# Use an official Maven image to build the project
FROM maven:3.8.4-openjdk-11 AS build
WORKDIR /app
COPY pom.xml /app
COPY src /app/src
RUN mvn clean compile assembly:single

# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jre-slim
ENV TZ=Asia/Hong_Kong
WORKDIR /app
# COPY ${JAR_FILE} app.jar
COPY --from=build /app/target/java-exchange-rate-1.0-SNAPSHOT.jar /app/ExchangeRateFetcher.jar
CMD ["java", "-jar", "/app/ExchangeRateFetcher.jar"]