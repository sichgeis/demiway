resource "aws_ecs_cluster" "fargate" {
  name = "fargate"
}

resource "aws_iam_role" "fargate_executor" {
  name = "Fargate-task-executor"

  assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": "sts:AssumeRole",
      "Principal": {
        "Service": "ecs-tasks.amazonaws.com"
      },
      "Effect": "Allow"
    }
  ]
}
EOF
}

resource "aws_iam_role_policy" "fargate_executor" {
  name   = "Fargate-task-executor"
  role   = aws_iam_role.fargate_executor.id
  policy = data.aws_iam_policy_document.fargate_executor.json
}

data "aws_iam_policy_document" "fargate_executor" {
  statement {
    actions = [
      "ecr:GetAuthorizationToken",
      "cloudwatch:PutMetricData",
      "logs:CreateLogGroup",
      "logs:CreateLogStream",
      "logs:PutLogEvents",
    ]
    resources = [
      "*"
    ]
  }
  statement {
  
    actions = [
      "ecr:GetDownloadUrlForLayer",
      "ecr:BatchGetImage",
      "ecr:BatchCheckLayerAvailability",
      "ecr:DescribeRepositories",
      "ecr:ListImages",
    ]

    resources = [
      "*"
    ]
  }
}
