data "aws_vpc" "vpc" {
  filter {
    name   = "tag:Name"
    values = ["demiway"]
  }
}

data "aws_subnet_ids" "private" {
  vpc_id = data.aws_vpc.vpc.id
  tags = {
    Tier = "Private"
  }
}

data "aws_ecs_cluster" "ecs_cluster" {
  cluster_name = var.cluster_name
}

data "aws_iam_role" "task" {
  name = "EcsTask-${var.app_name}-${var.component_name}"
}

data "aws_iam_role" "ecs_task_execution" {
  name = var.task_execution_role_name
}
