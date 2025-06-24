#!/bin/bash
set -e

docker network inspect template-network >/dev/null 2>&1 || \
  docker network create -d bridge --subnet 173.100.1.0/24 --gateway 173.100.1.1 template-network || true

docker run -d --rm --name template-database \
  -e POSTGRES_DB=jab_db_test \
  -e POSTGRES_USER=jab_db_user \
  -e POSTGRES_PASSWORD=jab_db_pass \
  --network template-network \
  --ip 173.100.1.101 \
  -p 8101:5432 \
  postgres:17.2-alpine3.21 -c log_statement=all
