provider "aws" {
  profile = "bi"
  region = "eu-central-1"
}

terraform {
  backend "s3" {
    region = "eu-central-1"
    profile = "bi"
    bucket = "demiway-tf-state"
    key = "impactor-docker-repos.tfstate"
  }
}

module "bff" {
  source = "../modules/ecr_repository"
  repository_name = "bonus/bff"
  ecr_account = "548449406202"
}
