#!/bin/bash

# 현재 날짜와 시간을 변수에 저장
CURRENT_DATE=$(date +"%Y%m%d-%H%M%S")
LOG_FILE="build_push_log_$CURRENT_DATE.txt"
DEPLOYMENT_FILE="./templates/deployment.yaml"

# 로그 파일 생성 및 빌드 로그 기록 시작
{
    echo "=== Docker 이미지 빌드 및 푸시 시작: $CURRENT_DATE ==="
    echo "이미지 태그: 192.168.0.212:5555/spring-batch:$CURRENT_DATE"
    echo ""

    # Docker 이미지 빌드
    echo "Docker 이미지 빌드 중..."
    docker build -t 192.168.0.212:5555/spring-batch:$CURRENT_DATE -f ../Dockerfile .. 2>&1
    BUILD_STATUS=$?
    if [ $BUILD_STATUS -eq 0 ]; then
        echo "Docker 이미지 빌드 성공"
    else
        echo "Docker 이미지 빌드 실패"
        exit 1
    fi

    echo ""

    # Docker 이미지 푸시
    echo "Docker 이미지 푸시 중..."
    docker push 192.168.0.212:5555/spring-batch:$CURRENT_DATE 2>&1
    PUSH_STATUS=$?
    if [ $PUSH_STATUS -eq 0 ]; then
        echo "Docker 이미지 푸시 성공"
    else
        echo "Docker 이미지 푸시 실패"
        exit 1
    fi

    echo ""
    echo "=== Docker 이미지 빌드 및 푸시 완료: $CURRENT_DATE ==="

    # deployment.yaml 파일 업데이트
    echo "Deployment 파일 업데이트 중..."

    # DEPLOYMENT_FILE 파일이 존재하는지 확인
    if [ ! -f "$DEPLOYMENT_FILE" ]; then
        echo "Deployment 파일이 존재하지 않습니다: $DEPLOYMENT_FILE"
        exit 1
    fi

    # sed 명령어를 디버깅 모드로 실행하여 오류 메시지 확인
    sed -i.bak "s|image: 192.168.0.212:5555/spring-batch:.*|image: 192.168.0.212:5555/spring-batch:$CURRENT_DATE|g" $DEPLOYMENT_FILE
    UPDATE_STATUS=$?
    if [ $UPDATE_STATUS -eq 0 ]; then
        echo "Deployment 파일 업데이트 성공"
        # 백업 파일 삭제
        rm "$DEPLOYMENT_FILE.bak"
    else
        echo "Deployment 파일 업데이트 실패"
        exit 1
    fi

} | tee $LOG_FILE

# 모든 작업이 성공적으로 완료된 경우 로그 파일 삭제 및 로컬 이미지 삭제
if [ $BUILD_STATUS -eq 0 ] && [ $PUSH_STATUS -eq 0 ] && [ $UPDATE_STATUS -eq 0 ]; then
    echo "모든 작업이 성공적으로 완료되었습니다. 로그 파일을 삭제합니다."
    rm $LOG_FILE
    docker rmi 192.168.0.212:5555/spring-batch:$CURRENT_DATE
else
    echo "작업 중 오류가 발생했습니다. 로그 파일을 확인하세요: $LOG_FILE"
fi
