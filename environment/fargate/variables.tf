variable "region" {
  default = "eu-central-1"
}

variable "profile" {
  default = "bi"
}

variable "config_bucket" {
  default = "internal-services-config"
}

variable "key" {
  default = "fargate/terraform.tfstate"
}

variable "domains_state_key" {
  default = "domains/domains.tfstate"
}

variable "network_state_key" {
  default = "network/internal.tfstate"
}

