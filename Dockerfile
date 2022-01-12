FROM openjdk:11-jdk-slim
EXPOSE 8080
VOLUME /tmp
ARG JAR_FILE=target/travel-checker-server.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar", "-web -webAllowOthers -tcp -tcpAllowOthers -browser"]