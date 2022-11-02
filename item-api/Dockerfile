FROM openjdk:17-alpine
EXPOSE 8082
VOLUME /tmp
ADD build/libs/item-api-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]