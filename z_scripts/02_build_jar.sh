#!/bin/bash
set -e

echo -e "\n\n\n\n######### Building adapter #########"
cd ./adapter
mvn clean dependency:resolve package -DskipTests=true -DoutputDirectory=../../3_results

