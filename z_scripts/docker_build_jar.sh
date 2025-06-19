#!/bin/bash
set -e


### NOTES
#
## For exploring 
# Keeping the container alive and getting in it use tle flags "-ti"
#   for example "docker run --rm -ti ..."
#
###


docker run --rm \
  --name tmp_maven \
  -v ~/.m2:/root/.m2 \
  -v ./:/app \
  -w /app \
  maven:3.9.9-eclipse-temurin-21-alpine \
    bash ./z_scripts/build_01_build_and_install_my_modules.sh && \
    bash ./z_scripts/build_02_jar.sh
