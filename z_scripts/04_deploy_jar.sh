#!/bin/bash
set -e

docker run -d --rm --name template_backend \
  -v ./adapter/target:/app \
  -w /app \
  -p 8201:8080 \
  --network template-network \
  --ip 173.100.1.102 \
  eclipse-temurin:21-alpine \
    java -jar adapter-0.0.1.jar
