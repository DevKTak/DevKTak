FROM adoptopenjdk/openjdk11

MAINTAINER Kyungtak Park <qkrrudkr77@naver.com>

# 컨테이너에 연결 할 포트번호
EXPOSE 9292

# Argument로 JAR_FILE_PATH 라는 변수명에 target/*.jar 경로 담기
ARG JAR_FILE_PATH=target/*.jar

# app.jar 이름으로 복사
COPY ${JAR_FILE_PATH} app.jar

# java를 실행하는 parameter
ENTRYPOINT ["java", "-jar", "app.jar"]
