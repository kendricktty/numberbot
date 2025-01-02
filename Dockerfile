# Use the official OpenJDK 17 image as the base image
FROM openjdk:17-jdk-slim

# Copy your Spring Boot application JAR file into the Docker image
COPY target/remote-numberbot-0.0.1-SNAPSHOT.jar /app.jar

# Set the working directory inside the Docker image
WORKDIR /

# Specify the command to run your Spring Boot application
CMD ["java", "-jar", "app.jar"]