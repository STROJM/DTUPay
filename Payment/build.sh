#!/bin/bash
set -e
mvn clean package install
docker-compose build payment
