#!/bin/bash
set -e

echo -e "\n######### Building common #########"

docker run --rm \
  --name tmp_maven_building_common \
  -v ~/.m2:/root/.m2 \
  -v ./common:/app \
  -w /app \
  maven:3.9.9-eclipse-temurin-21-alpine \
    mvn clean install -DskipTests=true





echo -e "\n\n\n\n######### Building user #########"

docker run --rm \
  --name tmp_maven_building_user \
  -v ~/.m2:/root/.m2 \
  -v ./user:/app \
  -w /app \
  maven:3.9.9-eclipse-temurin-21-alpine \
    mvn clean install -DskipTests=true
    





echo -e "\n\n\n\n######### Building product #########"

docker run --rm \
  --name tmp_maven_building_product \
  -v ~/.m2:/root/.m2 \
  -v ./product:/app \
  -w /app \
  maven:3.9.9-eclipse-temurin-21-alpine \
    mvn clean install -DskipTests=true
    





echo -e "\n\n\n\n######### Building adapter #########"

docker run --rm \
  --name tmp_maven_building_adapter \
  -v ~/.m2:/root/.m2 \
  -v ./adapter:/app \
  -w /app \
  maven:3.9.9-eclipse-temurin-21-alpine \
    mvn clean dependency:resolve package -DskipTests=true





echo -e "\n\n\n\n######### Building docker image #########"

# The version is sync with the adapter/pom.xml
docker build -t template_backend:0.0.1 .
