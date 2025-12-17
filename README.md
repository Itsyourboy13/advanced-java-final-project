# Landon Hotel Scheduling App – Advanced Java Final Project

This repository contains my final project for **D387 – Advanced Java** at Western Governors University.

The application is a **full-stack Spring Boot (Java backend) + Angular (frontend)** hotel scheduling/reservation system, enhanced to demonstrate advanced Java concepts including:

- Multithreading for internationalization (i18n)
- Resource bundles for localization (English & French welcome messages)
- Currency formatting (USD, CAD, EUR)
- Custom time zone conversion (ET, MT, UTC)

The project also includes **Docker containerization** of the entire application.

## Features Demonstrated

### Backend (Spring Boot + Java)
- **Multithreaded welcome messages**: English and French messages displayed concurrently using separate threads and resource bundles
- **Time zone conversion**: Custom Java method to convert and display times in Eastern Time (ET), Mountain Time (MT), and UTC
- REST API serving reservation data

### Frontend (Angular)
- Displays welcome messages in both English and French
- Shows reservation prices formatted in USD, CAD, and EUR (on separate lines)
- Displays live presentation time in ET, MT, and UTC

### DevOps
- Dockerfile that builds a container image for the Spring Boot application
- Successfully tested running in a Docker container

## How to Run Locally

### Prerequisites
- Java 17
- Maven
- Docker (optional, for container run)

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/Itsyourboy13/landon-hotel-advanced-java.git
   cd landon-hotel-advanced-java
   
2. **Build and run with Maven**
   ```bash
   mvn spring-boot:run
- Application runs on http://localhost:8080
- Frontend is served from the backend (static resources)

3. **Open in browser**
- Go to http://localhost:8080
- You will see:
  - Welcome messages in English and French (displayed via multithreading)
  - Reservation Prices in USD, CAD, and EUR after you have selected check in and check out dates and have submitted those 
  - Live presentation time in ET, MT, and UTC

## Run with Docker
The existing Dockerfile is already in the project and works perfectly!

### Build and run:

   ```bash
   # Build the JAR first (if not already built)
   mvn clean package
   
   # Build the Docker image
   docker build -t landon-hotel .

   # Run container
   docker run -p 8080:8080 --name D387_landon_hotel landon-hotel
   ```
Then open http://localhost:8080

## Skills Demonstrated
- Multithreaded programming in Java
- Internationalization and localization with resource bundles
- Time zone handling with Java's Date/Time API
- Full-stack development (Spring Boot + Angular)
- Docker containerization and deployment preparation

## Academic Note
This project was completed as a graded assessment for WGU D387 – Advanced Java. The starter code was provided by the course. All modifications for multithreading, localization, currency display, time zone conversion, and Docker were implemented by me.

## Why This Project?
This project showcases advanced Java concepts in a real-world context, including concurrent programming and internationalization — key skills for enterprise Java development.