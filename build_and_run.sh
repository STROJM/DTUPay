#!/bin/bash
set -e

./build.sh

# Update the set of services and
# build and execute the system tests
#pushd end-to-end-tests
#./deploy.sh
#sleep 5
#./test.sh
#popd

docker-compose up -d rabbitMq
sleep 10
docker-compose up -d token-management # add remaining services

# Cleanup the build images
docker image prune -f

