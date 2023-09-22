# Use an official OpenJDK runtime as a parent image
FROM maven:3.8-openjdk-11 AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project file
COPY pom.xml .

# Download the project dependencies
RUN mvn dependency:go-offline -B

# Copy the source code into the container
COPY src ./src

# Build the application
RUN mvn package

# Use an official OpenJDK runtime image as the final image
FROM openjdk:11-jre-slim

# Set the working directory in the final image
WORKDIR /app

# Copy the JAR file built in the previous stage into the final image
COPY --from=builder /app/target/warzone-1.0-SNAPSHOT.jar ./warzone-game-cli.jar

# Define the command to run your application
CMD ["java", "-jar", "warzone-game-cli.jar"]