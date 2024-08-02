FROM gradle:jdk21 AS builder
WORKDIR /home/gradle/project

COPY --chown=gradle:gradle . .

RUN chmod +x gradlew

RUN ./gradlew clean build -x test --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app

RUN apt-get update && apt-get install -y netcat-openbsd

COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar
COPY --from=builder /home/gradle/project/src/main/resources /app/resources

COPY check-service.sh /app/check-service.sh

RUN chmod +x /app/check-service.sh

ENTRYPOINT ["sh", "-c", "/app/check-service.sh && cat /app/resources/application.properties && java -jar app.jar --spring.config.location=/app/resources/application.properties"]
