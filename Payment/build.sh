#!/bin/bash
#@author Søren Andersen s182881
set -e
mvn clean package install
docker-compose build payment
