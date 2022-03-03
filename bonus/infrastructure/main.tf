provider "aws" {
  profile = "bi"
  region  = "eu-central-1"
}

terraform {
  backend "s3" {
    region = "eu-central-1"
    profile = "bi"
    bucket = "demiway-tf-state"
    key = "bonus.tfstate"
  }
}

output "cluster_name" {
  value = data.aws_ecs_cluster.ecs_cluster.id
}
