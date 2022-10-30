FROM openjdk:17-alpine
EXPOSE 8084
VOLUME /tmp
ADD build/libs/users-api-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]