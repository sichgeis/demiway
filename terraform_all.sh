#!/bin/bash

set -e

action=$1; shift;

pwd=$(pwd)

if [ "${action}" = "apply" ]; then

    # environment
    cd $pwd/environment
    ./terraform.sh apply -auto-approve

    # bonus app
    cd $pwd/bonus/infrastructure/deployed_manually
    ./terraform.sh apply -auto-approve

    cd $pwd/bonus/infrastructure
    ./terraform.sh apply -auto-approve

elif [ "${action}" = "destroy" ]; then

    # bonus app
    cd $pwd/bonus/infrastructure
    ./terraform.sh destroy -auto-approve

    cd $pwd/bonus/infrastructure/deployed_manually
    ./terraform.sh destroy -auto-approve

    # environment
    cd $pwd/environment
    ./terraform.sh destroy -auto-approve

else
    echo "please specify either apply or destroy action to command"
fi