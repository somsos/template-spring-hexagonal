#!/bin/bash
set -e

./z_scripts/03_setup_postgres.sh

./z_scripts/04_deploy_jar.sh