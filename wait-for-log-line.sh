#!/bin/bash
set -e

CONTAINER_NAME="template51_backend"
TIMEOUT_SEC=120

while read line; do
  case "$line" in

    *"Started AdapterApplication in"* )
      echo "######### deploy success #########"
      exit 0;
      ;;

    *"Shutdown completed"* )
      echo "deploy shutdown"
      exit 1
      ;;

    *"Error starting ApplicationContext"* )
      echo "deploy failed"
      exit 1
      ;;

    * )
      echo $line
      ;;

  esac
done < <(timeout "$TIMEOUT_SEC"s docker logs -f $CONTAINER_NAME)

echo "error" "$TIMEOUT_SEC"s timeout reached
exit 1
