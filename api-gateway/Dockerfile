FROM openjdk:17
EXPOSE 8765
VOLUME /tmp
ADD build/libs/api-gateway-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
