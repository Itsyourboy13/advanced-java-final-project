# Use the official Eclipse Temurin (OpenJDK) JRE image – very reliable
FROM eclipse-temurin:17-jre

# Set working directory
WORKDIR /app

# Copy the built JAR (make sure it's the correct name)
COPY target/D387_sample_code-0.0.2-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]