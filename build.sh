#!/bin/bash
set -e

# Build and install the libraries
# abstracting away from using the
# RabbitMq message queue
pushd MessageUtilities
./build.sh
popd 

# Build the services
pushd TokenManagement
./build.sh
popd 
