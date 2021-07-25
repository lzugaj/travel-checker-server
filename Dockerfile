FROM openjdk:11-jdk-alpine
VOLUME /tmp
ADD target/travel-checker-0.0.1-SNAPSHOT.jar travel-checker-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/travel-checker-0.0.1-SNAPSHOT.jar"]