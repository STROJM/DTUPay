#!/bin/bash
set -e

#Build images
./build.sh

# Run and test
./run_and_test.sh

# Cleanup the build images
docker image prune -f

