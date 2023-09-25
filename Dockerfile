# Use the official OpenJDK 17 image as the base image
FROM adoptopenjdk/openjdk17:alpine-slim

ARG REMOTE_NUMBERBOT_USER
ARG REMOTE_NUMBERBOT_TOKEN

# Set environment variables
ENV REMOTE_NUMBERBOT_USER=$REMOTE_NUMBERBOT_USER
ENV REMOTE_NUMBERBOT_TOKEN=$REMOTE_NUMBERBOT_TOKEN

# Copy your Spring Boot application JAR file into the Docker image
COPY remote-numberbot-0.0.1-SNAPSHOT.jar /app.jar

# Set the working directory inside the Docker image
WORKDIR /

# Expose the port that your Spring Boot application listens on
EXPOSE 8080

# Specify the command to run your Spring Boot application
CMD ["java", "-jar", "app.jar"]