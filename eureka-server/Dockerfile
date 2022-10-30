FROM openjdk:17
EXPOSE 8761
VOLUME /tmp
ADD build/libs/eureka-server-0.0.1.jar eureka-server-0.0.1.jar
ENTRYPOINT ["java","-jar","eureka-server-0.0.1.jar"]