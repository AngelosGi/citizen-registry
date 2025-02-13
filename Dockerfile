#
# Dockerfile for the Citizen Registry REST Service
# Fine-grained layered build approach (like the book) with parent installation
#

#############################
# Stage 1: Build the Maven Project
#############################
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder
WORKDIR /app

# Copy the aggregator (parent) POM into /app.
COPY pom.xml .

# --- Install the parent POM artifact non-recursively so child modules can resolve it ---
RUN mvn -B install -N -DskipTests || true

# ----- Build the Domain Module First -----
# Create a directory for the domain module and copy its files.
RUN mkdir domain-registry
COPY domain/src /app/domain-registry/src
COPY domain/pom.xml /app/domain-registry/pom.xml

# Ensure the domain module’s POM has <relativePath>../pom.xml</relativePath>
WORKDIR /app/domain-registry
RUN mvn -B dependency:go-offline || true
RUN mvn -B clean install -DskipTests || true

# ----- Build the Service Module Second -----
WORKDIR /app
RUN mkdir service-registry
COPY service/src /app/service-registry/src
COPY service/pom.xml /app/service-registry/pom.xml

# Ensure the service module’s POM has <relativePath>../pom.xml</relativePath>
WORKDIR /app/service-registry
RUN mvn -B dependency:go-offline || true
RUN mvn -B clean package -DskipTests || true

#############################
# Stage 2: Create a Minimal Runtime Image
#############################
FROM eclipse-temurin:21-alpine

RUN addgroup -S citizen && adduser -S -G citizen -s /bin/sh citizen
RUN mkdir /app && chown -R citizen:citizen /app
WORKDIR /app


COPY --from=builder --chown=citizen:citizen /app/service-registry/target/service-*-exec.jar app.jar


USER citizen
EXPOSE 8080

# Optionally define environment variables for DB, etc, NO need as it takes the defaults from the application.properties

#ENV DB_HOST=localhost \
#    DB_PORT=5432 \
#    DB_USER=postgres \
#    DB_PASSWORD=pass123 \
#    DB_NAME=citizens

CMD ["java", "-jar", "app.jar"]
