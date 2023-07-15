FROM openjdk:11 AS builder

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN chmod +x ./gradlew
RUN ./gradlew bootJAR


FROM openjdk:11

# 위 builder에서 만든 .jar파일을 컨테이너 내부로 복사
COPY --from=builder build/libs/*.jar app.jar
EXPOSE 8080

# 이미지 빌드시 실행되는 명령어. 로컬에서 jar파일을 구동하는 것과 동일한 명령어
ENTRYPOINT ["java", "-jar", "/app.jar"]