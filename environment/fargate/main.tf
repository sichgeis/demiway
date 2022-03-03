provider "aws" {
  profile = var.profile
  region  = var.region
}

terraform {
  backend "s3" {
    region  = "eu-central-1"
    profile = "internal-services/Admin"
    bucket  = "internal-services-config"
    key     = "ecs/ecs.tfstate"
  }
}

output "fargate_executor_arn" {
  value = aws_iam_role.fargate_executor.arn
}
output "fargate_cluster_arn" {
  value = aws_ecs_cluster.fargate.arn
}