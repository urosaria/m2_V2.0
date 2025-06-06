# -------- Dev Environment --------
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Install required tools
RUN apt-get update && apt-get install -y maven curl && apt-get clean

# Cache dependencies by copying only pom.xml first
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy entire source for development
COPY . .

# Use host's Maven cache if mounted (via docker-compose.override.yml)
# and run with spring-boot:run for hot reload support
CMD ["mvn", "spring-boot:run"]