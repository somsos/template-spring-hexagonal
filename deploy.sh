#!/bin/bash
set -e


echo "create network if necessary"
docker network inspect template-network >/dev/null 2>&1 || \
  docker network create -d bridge --subnet 173.100.1.0/24 --gateway 173.100.1.1 template-network || true





echo "deploy postgres database if it's not running"
docker ps -a --format="table {{.Names}}" | grep template_database || \
  docker run -d --rm --name template_database \
    -e POSTGRES_DB=jab_db_test \
    -e POSTGRES_USER=jab_db_user \
    -e POSTGRES_PASSWORD=jab_db_pass \
    --network template-network \
    --ip 173.100.1.101 \
    -p 8101:5432 \
    postgres:17.2-alpine3.21 -c log_statement=all








CONTAINER_NAME="template_backend"
echo "stopping template_backend if it's running"
docker stop $CONTAINER_NAME 2> /dev/null || true

sleep 5

echo "Setup Backend"

FILE_LOGS="./$CONTAINER_NAME.log"
JAR_NAME="adapter-0.0.1.jar"
PATH_JAR="./adapter/target/"
PROFILES="default, test-docker"

TIMEOUT_SEC=120

##-DEPLOY

docker run -d --name $CONTAINER_NAME \
  -v $PATH_JAR:/app \
  -w /app \
  -p 8201:8080 \
  --network template-network \
  --ip 173.100.1.102 \
  eclipse-temurin:21-alpine \
    bash -c 'java -jar adapter-0.0.1.jar -Dspring.profiles.active='$PROFILES''

##END-DEPLOY

function stop_container {
  docker stop $CONTAINER_NAME &> /dev/null && docker rm $CONTAINER_NAME &> /dev/null
}

while read line; do
  case "$line" in

    *"Started AdapterApplication in"* )
      echo "######### deploy success #########"
      exit 0;
      ;;

    *"Shutdown completed"* )
      echo "deploy shutdown"
      stop_container
      exit 1
      ;;

    *"Error starting ApplicationContext"* )
      echo "deploy failed"
      stop_container
      exit 1
      ;;

    * )
      echo $line
      ;;

  esac
done < <(timeout "$TIMEOUT_SEC"s docker logs -f $CONTAINER_NAME)

echo "error" "$TIMEOUT_SEC"s timeout reached
exit 1
