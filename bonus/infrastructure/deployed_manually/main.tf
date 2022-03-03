provider "aws" {
  profile = "bi"
  region = "eu-central-1"
}

terraform {
  backend "s3" {
    region = "eu-central-1"
    profile = "bi"
    bucket = "demiway-tf-state"
    key = "bonus-deployed-manually.tfstate"
  }
}
