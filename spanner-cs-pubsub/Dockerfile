# Stage 1: Build the application
FROM maven:3.8.7-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy the Maven POM file and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the application source code and build the package
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM gcr.io/dataflow-templates-base/java17-template-launcher-base
WORKDIR /app

# Metadata required by Dataflow Flex Templates
ENV FLEX_TEMPLATE_JAVA_MAIN_CLASS="com.psre.dataflow.SpannerChangeStreamsToPubSub"

# Copy the application JAR file from the builder stage
COPY --from=builder /app/target/spanner-changestreams-to-pubsub-1.0-SNAPSHOT.jar /template/spanner-changestreams-to-pubsub.jar

# Set up the entry point for Dataflow Flex Template
ENTRYPOINT ["/template/launch_template"]
