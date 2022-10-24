FROM openjdk:16-jdk-alpine
RUN adduser -D demo
USER demo
VOLUME /tmp
ADD target/items-api-*.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]