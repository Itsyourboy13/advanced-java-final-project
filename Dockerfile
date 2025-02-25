# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Add the application's JAR file to the container
ADD target/D387_sample_code-0.0.2-SNAPSHOT.jar /app/D387_sample_code.jar

# Set the working directory inside the container
WORKDIR /app

# Expose the port that the application will run on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "D387_sample_code.jar"]
