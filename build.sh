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

pushd AccountManagement
./build.sh
popd

pushd Payment
./build.sh
popd 

# Build APIs
pushd CustomerApi
./build.sh
popd

# Build APIs
pushd MerchantApi
./build.sh
popd
