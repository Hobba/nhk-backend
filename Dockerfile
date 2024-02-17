# Use a base image with JDK (Java Development Kit) installed.
# You can choose a tag based on the JDK version you need.
FROM openjdk:17-jdk-slim as builder

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle configuration files into the container
# Ensure the destination is marked as a directory by ending with a /
COPY build.gradle.kts gradlew /app/
COPY gradle /app/gradle/

# Copy the application's source code into the container
COPY src /app/src/

# Build the application using Gradle wrapper
RUN ./gradlew build

# Use a slim JDK image for running the application to reduce the final image size
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the built JAR from the builder stage to the runner stage
# The destination is correctly marked as a directory
COPY --from=builder /app/build/libs/*.jar /app/

# Set the environment variable required by Google Cloud
ARG PROJECT_ID
ENV GOOGLE_CLOUD_PROJECT=$PROJECT_ID

# Command to run the application
CMD ["java", "-jar", "/app/app.jar"]
