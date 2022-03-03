module "bff_task_role" {
  source = "../modules/task-role"

  task_name = "bonus-bff"

  policy_json = data.aws_iam_policy_document.bff_permissions.json
}

data "aws_iam_policy_document" "bff_permissions" {
  statement {
    actions = [
      "kms:Decrypt",
    ]

    resources = [
      "arn:aws:kms:eu-central-1:548449406202:alias/aws/ssm"
    ]
  }

  statement {
    actions = [
      "ssm:GetParameter",
    ]

    resources = [
      "arn:aws:ssm:eu-central-1:548449406202:parameter/impactor.*"
    ]
  }
}
