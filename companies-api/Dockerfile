FROM openjdk:17-alpine
EXPOSE 8081
VOLUME /tmp
ADD build/libs/companies-api-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
