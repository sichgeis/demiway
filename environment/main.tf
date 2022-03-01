terraform {
  backend "s3" {
    key = "environment.tfstate"
    bucket = "demiway-tf-state"
    region = "eu-central-1"
    profile = "bi"
  }
}

provider "aws" {
  region = "eu-central-1"
  profile = "bi"
}
