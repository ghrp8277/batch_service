#!/bin/sh

# Kafka가 실행 중인지 확인하는 함수
check_mysql() {
  nc -zv host.docker.internal 3306
  return $?
}

# Kafka가 실행될 때까지 대기
while ! check_mysql; do
  echo "Waiting for MySQL to be up..."
  sleep 5
done

echo "MySQL is up and running!"