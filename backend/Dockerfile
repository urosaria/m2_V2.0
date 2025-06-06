# -------- Stage 1: Build --------
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copy Maven files first to leverage Docker cache
COPY pom.xml .
COPY src/ src/

# Optional: install Maven if not included in base image (for older images)
RUN apt-get update && apt-get install -y maven

# Build the Spring Boot app
RUN mvn clean package -DskipTests

# -------- Stage 2: Runtime --------
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy the packaged JAR from the build stage
COPY --from=build /app/target/m2-backend-0.0.1-SNAPSHOT.jar app.jar

# Run the app
CMD ["java", "-jar", "app.jar"]