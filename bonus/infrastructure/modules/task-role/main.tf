variable "policy_json" {}
variable "task_name" {}


resource "aws_iam_policy" "policy" {
  name = "EcsTask-${var.task_name}"
  policy = var.policy_json
}

resource "aws_iam_role" "role" {
  name = "EcsTask-${var.task_name}"
  assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "",
      "Effect": "Allow",
      "Principal": {
        "Service": "ecs-tasks.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    }
  ]
}
EOF
}

resource "aws_iam_role_policy_attachment" "attachment" {
  role = aws_iam_role.role.id
  policy_arn = aws_iam_policy.policy.arn
}

output "role_arn"{
  value = aws_iam_role.role.arn
}

output "role_id"{
  value = aws_iam_role.role.id
}
