FROM gradle:8.5-jdk17-alpine AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy your source code and Gradle files into the container
COPY build.gradle settings.gradle ./
COPY src ./src

# Run the Gradle build inside the container (skipping tests for speed)
RUN gradle build -x test

FROM eclipse-temurin:17-jdk-alpine

# Copy the JAR file into the container
COPY build/libs/*.jar app-v2.jar

# Expose port 80
EXPOSE 8080

# Define the entry point to run your application
ENTRYPOINT ["java", "-jar", "app-v2.jar"]