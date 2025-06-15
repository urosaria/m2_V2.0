# --- Build Stage ---
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app

# Download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source and build
COPY . .
RUN mvn clean package -DskipTests

# --- Runtime Stage ---
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy built jar from build stage
COPY --from=build /app/target/*.jar app.jar

# 👇 Tell Railway what port to use
EXPOSE 8080

# 👇 Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]