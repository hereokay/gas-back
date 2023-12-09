FROM openjdk:17-jdk-alpine
COPY build/libs/demo-0.0.5.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

