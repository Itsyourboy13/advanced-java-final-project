# Runtime image: Eclipse Temurin 17 JRE
FROM eclipse-temurin:17-jre

WORKDIR /app

# Build the JAR first with: ./mvnw clean package
COPY target/hotel-reservation-0.0.2-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
