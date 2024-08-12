#!/bin/bash

# 디버깅 모드 활성화
set -x

# 현재 날짜와 시간을 변수에 저장
CURRENT_DATE=$(date +"%Y%m%d-%H%M%S")
LOG_FILE="build_push_log_$CURRENT_DATE.txt"
DEPLOYMENT_FILE="./templates/deployment.yaml"
HELM_RELEASE_NAME="backend-batch"
HELM_NAMESPACE="prod"

# 초기 상태 변수 설정
STATUS=0

# 로그 파일 생성 및 빌드 로그 기록 시작
{
    echo "=== Docker 이미지 빌드 및 푸시 시작: $CURRENT_DATE ==="
    echo "이미지 태그: 192.168.0.212:5555/spring-batch:$CURRENT_DATE"
    echo ""

    # Docker 이미지 빌드
    echo "Docker 이미지 빌드 중..."
    docker build -t 192.168.0.212:5555/spring-batch:$CURRENT_DATE -f ../Dockerfile .. 2>&1
    BUILD_STATUS=$?
    if [ $BUILD_STATUS -ne 0 ]; then
        echo "Docker 이미지 빌드 실패"
        STATUS=1
    else
        echo "Docker 이미지 빌드 성공"
    fi

    echo ""

    # Docker 이미지 푸시
    echo "Docker 이미지 푸시 중..."
    docker push 192.168.0.212:5555/spring-batch:$CURRENT_DATE 2>&1
    PUSH_STATUS=$?
    if [ $PUSH_STATUS -ne 0 ]; then
        echo "Docker 이미지 푸시 실패"
        STATUS=1
    else
        echo "Docker 이미지 푸시 성공"
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

    # deployment.yaml 파일에서 이미지 태그 업데이트
    sed -i.bak "s|image: 192.168.0.212:5555/spring-batch:.*|image: 192.168.0.212:5555/spring-batch:$CURRENT_DATE|g" $DEPLOYMENT_FILE
    UPDATE_STATUS=$?
    if [ $UPDATE_STATUS -ne 0 ]; then
        echo "Deployment 파일 업데이트 실패"
        STATUS=1
    else
        echo "Deployment 파일 업데이트 성공"
        rm "$DEPLOYMENT_FILE.bak" # 백업 파일 삭제
    fi

    echo ""

    echo "Helm 작업 시작..."
    if helm list -n $HELM_NAMESPACE | grep "$HELM_RELEASE_NAME" > /dev/null; then
        echo "Helm 업데이트 실행 중: $HELM_RELEASE_NAME"
        helm upgrade $HELM_RELEASE_NAME . -n $HELM_NAMESPACE | tee -a $LOG_FILE
        if [ $? -ne 0 ]; then
            echo "Helm 업데이트 실패"
            STATUS=1
        else
            echo "Helm 업데이트 성공"
        fi
    else
        echo "Helm 설치 실행 중: $HELM_RELEASE_NAME"
        helm install $HELM_RELEASE_NAME . -n $HELM_NAMESPACE | tee -a $LOG_FILE
        if [ $? -ne 0 ]; then
            echo "Helm 설치 실패"
            STATUS=1
        else
            echo "Helm 설치 성공"
        fi
    fi

} | tee $LOG_FILE

# 모든 작업이 성공적으로 완료된 경우 로그 파일 삭제 및 로컬 이미지 삭제
if [ $STATUS -eq 0 ]; then
    echo "모든 작업이 성공적으로 완료되었습니다. 로그 파일을 삭제합니다."
    rm $LOG_FILE
    docker rmi 192.168.0.212:5555/spring-batch:$CURRENT_DATE
else
    echo "작업 중 오류가 발생했습니다. 로그 파일을 확인하세요: $LOG_FILE"
fi

# 디버깅 모드 비활성화
set +x
