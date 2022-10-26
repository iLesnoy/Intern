FROM openjdk:16-jdk-alpine
VOLUME /tmp
ADD build/libs/users-api-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]