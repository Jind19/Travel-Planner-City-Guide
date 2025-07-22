# ------------ Stage 1: Build the app ------------
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy Maven build files separately to leverage Docker cache

COPY pom.xml .
COPY src ./src

# Build the application (skips tests for speed, remove -DskipTests if needed)
RUN mvn clean package -DskipTests

# ------------ Stage 2: Run the app ------------
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy only the JAR file from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose the app port
EXPOSE 6065

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]