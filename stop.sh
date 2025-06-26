#!/bin/bash
set -e

echo "#########- stopping postgres if it's running -#########"
docker stop template_database 2> /dev/null || true

echo "#########- stopping template_backend if it's running -#########"
docker stop template_backend 2> /dev/null || true
