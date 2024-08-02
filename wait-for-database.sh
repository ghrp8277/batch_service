#!/bin/bash

DATABASE_HOST=localhost
DATABASE_PORT=3306

# 데이터베이스가 준비될 때까지 대기
until nc -z -v -w30 $DATABASE_HOST $DATABASE_PORT; do
 echo "Waiting for database connection..."
 # 대기 시간 추가 (예: 5초)
 sleep 5
done