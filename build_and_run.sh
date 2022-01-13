#!/bin/bash
set -e

#Build images
./build.sh

#Run images
docker-compose up -d rabbitMq
sleep 10
docker-compose up -d token-management account-management payment reporting customer-api merchant-api

#Run e2e tests
sleep 5
pushd EndToEndTests
 ./run_tests.sh
popd

# Cleanup the build images
docker image prune -f

