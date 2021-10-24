FROM openjdk:11
ADD target/travel-checker-server.jar travel-checker-server.jar
ENTRYPOINT ["java", "-jar", "/travel-checker-server.jar"]
EXPOSE 8080