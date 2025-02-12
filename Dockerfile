# Stage 1: Build the Maven project
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copy entire source tree including parent POM
COPY . .

# Build parent POM first to resolve ${revision}
RUN mvn -B -N -f pom.xml dependency:go-offline

# Build domain module with parent context
RUN mvn -B -f pom.xml -pl domain -am clean install -Drevision=1.0.0-SNAPSHOT

# Build service module with explicit version
RUN mvn -B -f pom.xml -pl service -am clean package -Drevision=1.0.0-SNAPSHOT -DskipTests

# Stage 2: Runtime image
FROM eclipse-temurin:21-alpine

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
WORKDIR /app

COPY --from=build --chown=appuser:appgroup /app/service/target/*.jar app.jar

USER appuser
EXPOSE 8080

# Optionally define environment variables for DB, etc, NO need as it takes the defaults from the application.properties
#ENV DB_HOST=localhost \
#    DB_PORT=5432 \
#    DB_USER=postgres \
#    DB_PASSWORD=pass123 \
#    DB_NAME=citizens

ENTRYPOINT ["java", "-jar", "app.jar"]


