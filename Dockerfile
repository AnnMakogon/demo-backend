FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/check.jar check.jar
ENTRYPOINT ["java", "-jar", "check.jar"]