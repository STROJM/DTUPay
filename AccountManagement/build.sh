# @author Søren Andersen s182881
#!/bin/bash
set -e
mvn clean package
docker-compose build account-management
