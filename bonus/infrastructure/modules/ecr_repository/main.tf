variable "repository_name" {}

variable "ecr_account" {}

variable "allow_pull_other_accounts" {
  default = true
}

variable "allowed_role_arn" {
  type = list(string)
  default = [
    "arn:aws:iam::548449406202:root" # prod; necessary if prod account is a different account than the repo account
  ]
}

variable "days_before_images_expire" {
  default = 20
}

resource "aws_ecr_repository" "repository" {
  name = var.repository_name

  image_scanning_configuration {
    scan_on_push = true
  }
}

data "aws_iam_policy_document" "ecr_permissions" {
  statement {
    sid = "AllowPullOtherAccount"
    actions = [
      "ecr:GetDownloadUrlForLayer",
      "ecr:BatchGetImage",
      "ecr:BatchCheckLayerAvailability"
    ]
    principals {
      type = "AWS"
      identifiers = var.allowed_role_arn
    }
  }
}

resource "aws_ecr_repository_policy" "ecr_policy" {
  count = var.allow_pull_other_accounts ? 1 : 0
  repository = aws_ecr_repository.repository.name
  policy = data.aws_iam_policy_document.ecr_permissions.json
}

resource "aws_ecr_lifecycle_policy" "lifecycle_policy_of_containers" {
  repository = aws_ecr_repository.repository.name

  policy = <<EOF
{
    "rules": [
        {
            "rulePriority": 1,
            "description": "Delete images after ${var.days_before_images_expire} days",
            "selection": {
                "tagStatus": "any",
                "countType": "sinceImagePushed",
                "countUnit": "days",
                "countNumber": ${var.days_before_images_expire}
            },
            "action": {
                "type": "expire"
            }
        }
    ]
}
EOF
}
