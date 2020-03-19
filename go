#!/bin/bash

work_dir=`dirname $0`
cd "$work_dir"
echo " Working Directory: $work_dir "

case "$1" in
  "localComposeUp")
    docker-compose -f config/local/docker-compose.yml down
    docker-compose -f config/local/docker-compose.yml up -d
    ;;
  "localComposeDown")
    docker-compose -f config/local/docker-compose.yml down
    ;;
  "clean")
    ./gradlew clean
    ;;
  "up-config")
    ./gradlew :config-server:bootRun
    ;;
  "up-order")
    ./gradlew :order-service:bootRun
    ;;
  "up-inventory")
    ./gradlew :inventory-service:bootRun
    ;;
  "up-payment")
    ./gradlew :payment-service:bootRun
    ;;
  "up-txlcn")
    ./gradlew :txlcn-tm:bootRun
    ;;
  *)
    ./gradlew clean build
    exit 0
    ;;
esac
