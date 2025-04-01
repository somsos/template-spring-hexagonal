#!/bin/bash
set -e


echo "\n\n\n\n######### Building common #########"
cd common
mvn clean install -DskipTests=true

echo -e "\n\n\n\n######### Building user #########"
cd ../user
mvn clean install -DskipTests=true

echo -e "\n\n\n\n######### Building product #########"
cd ../product
mvn clean install -DskipTests=true


echo -e "\n\n\n\n######### Building adapter #########"
cd ../adapter
mvn clean dependency:resolve package -DskipTests=true -DoutputDirectory=../../3_results

