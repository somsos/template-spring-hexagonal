#!/bin/bash
set -e

echo "#########- run.sh script started -#########"



echo "#########- create network if necessary -#########"
docker network inspect template-network >/dev/null 2>&1 || \
  docker network create -d bridge --subnet 173.100.1.0/24 --gateway 173.100.1.1 template-network || true





echo "#########- stopping postgres if it's running -#########"
docker stop template_database 2> /dev/null || true

sleep 5

echo "#########- setup postgres -#########"
docker run -d --rm --name template_database \
  -e POSTGRES_DB=jab_db_test \
  -e POSTGRES_USER=jab_db_user \
  -e POSTGRES_PASSWORD=jab_db_pass \
  --network template-network \
  --ip 173.100.1.101 \
  -p 8101:5432 \
  postgres:17.2-alpine3.21 -c log_statement=all







echo "#########- stopping template_backend if it's running -#########"
docker stop template_backend 2> /dev/null || true

sleep 5

echo "#########- Setup Backend -#########"
docker run -d --rm --name template_backend \
  -v ./adapter/target:/app \
  -w /app \
  -p 8201:8080 \
  --network template-network \
  --ip 173.100.1.102 \
  eclipse-temurin:21-alpine \
    java -jar adapter-0.0.1.jar -Dspring.profiles.active=default, test-docker


echo "#########- run.sh script finished -#########"