#!/bin/sh

# Kafka가 실행 중인지 확인하는 함수
check_kafka() {
  nc -zv host.docker.internal 9093
  return $?
}

# Kafka가 실행될 때까지 대기
while ! check_kafka; do
  echo "Waiting for Kafka to be up..."
  sleep 5
done

echo "Kafka is up and running!"
