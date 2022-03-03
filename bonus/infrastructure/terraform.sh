#!/bin/bash

rm -f .terraform/terraform.tfstate

terraform init
terraform $*
