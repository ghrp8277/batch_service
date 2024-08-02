#!/bin/sh

# Kafka가 실행 중인지 확인하는 함수
check_mysql() {
  nc -zv host.docker.internal 3306
  return $?
}

check_kafka() {
  nc -zv host.docker.internal 9093
  return $?
}

while ! check_kafka; do
  echo "Waiting for Kafka to be up..."
  sleep 5
done

while ! check_mysql; do
  echo "Waiting for MySQL to be up..."
  sleep 5
done

echo "MySQL and Kafka is up and running!"