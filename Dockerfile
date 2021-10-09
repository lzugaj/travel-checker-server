FROM openjdk:11
EXPOSE 9010
ADD target/travel-checker-server.jar travel-checker-server.jar
ENTRYPOINT ["java", "-jar", "/travel-checker-server.jar"]