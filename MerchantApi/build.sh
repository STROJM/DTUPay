#!/bin/bash
#@author Søren Andersen s182881
set -e
mvn clean package
docker-compose build merchant-api
