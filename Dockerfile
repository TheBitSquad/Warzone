# Build the applications
FROM maven:3.9.4-eclipse-temurin-17 AS builder
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

# Run the application
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /home/app
COPY --from=builder /home/app/target/warzone-1.0-SNAPSHOT.jar ./warzone-game-cli.jar
CMD ["java", "-jar", "warzone-game-cli.jar"]
