resource "aws_iam_role" "managed_ecs_task_execution" {
  name               = "EcsFargateTaskExecution"
  assume_role_policy = data.aws_iam_policy_document.managed_ecs_task_execution.json
}

data "aws_iam_policy_document" "managed_ecs_task_execution" {
  statement {
    actions = ["sts:AssumeRole"]

    principals {
      type        = "Service"
      identifiers = ["ecs-tasks.amazonaws.com"]
    }
  }
}

resource "aws_iam_role_policy_attachment" "managed_ecs_task_execution" {
  role       = aws_iam_role.managed_ecs_task_execution.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

output "fargate_managed_task_execution_role_arn" {
  value = aws_iam_role.managed_ecs_task_execution.arn
}

