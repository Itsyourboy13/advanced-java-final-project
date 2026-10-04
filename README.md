# Landon Hotel Reservation App

A full-stack hotel room reservation web app with a **Spring Boot** REST backend and an **Angular** frontend. Guests pick check-in and check-out dates, see which rooms are free for those dates, and reserve a room. The app also shows a welcome message loaded on several threads in English and French, and an upcoming online presentation time in three time zones.

I built this during my software engineering degree to practise concurrency, localization, time zone handling and containerization in a Spring Boot + Angular app.

## Features

**Room search and reservations (REST API under `/room/reservation/v1`)**
- `GET /room/reservation/v1?checkin=YYYY-MM-DD&checkout=YYYY-MM-DD` returns the rooms that have no overlapping reservation for those dates, as a paged JSON response.
- `GET /room/reservation/v1/{roomId}` returns one room.
- `POST /room/reservation/v1` creates a reservation (`roomId`, `checkin`, `checkout`).
- `PUT /room/reservation/v1` and `DELETE /room/reservation/v1/{reservationId}` are also mapped as placeholders: the PUT handler returns an empty response and DELETE returns 204 without removing anything.
- Spring `Converter` classes map between JPA entities and request/response models.
- An H2 database is seeded at startup with three rooms (405, 406 and 407) by `H2Bootstrap`.

**Concurrent, localized welcome message (`GET /api/welcome`)**
- `WelcomeController` uses an `ExecutorService` thread pool to load the welcome message from the `Welcome_en_US` and `Welcome_fr_CA` properties files on four tasks at once.
- Results are collected in a `ConcurrentLinkedQueue` and returned as one message, which the Angular navbar displays.

**Presentation time in several time zones (`GET /api/presentation`)**
- Works out a presentation time (7:30 PM UTC, ten days from now) and converts it to Mountain Time (`America/Denver`) and Eastern Time (`America/New_York`) with a small `TimeZoneConverter` utility built on `java.time` (`ZonedDateTime`, `withZoneSameInstant`).
- The frontend shows the result above the search form.

**Price display in three currency formats**
- Each available room's price is shown three times, labelled `$`, `CA$` and `€`. The number is the same each time; there is no exchange-rate conversion.

**Docker**
- A `Dockerfile` packages the built Spring Boot JAR on an Eclipse Temurin 17 JRE image.

## Tech stack

| Layer | Technology |
|---|---|
| Language (backend) | Java 17 |
| Backend framework | Spring Boot 2.7.2 (Spring Web, Spring Data JPA, Bean Validation) |
| Database | H2 (file-based, H2 console enabled at `/h2-console`) |
| Other backend libs | Lombok, JAXB API 2.3.0, MySQL Connector/J 8.0.29 (declared in `pom.xml`, not used by the default config) |
| Build | Maven (wrapper included), `exec-maven-plugin` 1.6.0 runs `ng build` during the Maven build |
| Frontend | Angular 14 (`@angular/core` ^14.1.0, Angular CLI ~14.1.3), TypeScript ~4.7.2, RxJS ~7.5.0 |
| Frontend tests | Karma + Jasmine (Angular CLI defaults) |
| Container | Docker, `eclipse-temurin:17-jre` |

## Getting started

### Prerequisites
- JDK 17
- Node.js and npm (only needed if you rebuild the Angular frontend)
- Docker (optional)

### Run with the prebuilt frontend (quickest)
A production build of the Angular app is already in `src/main/resources/static`, so the backend can serve it without Node:

```bash
git clone https://github.com/Itsyourboy13/advanced-java-final-project.git
cd advanced-java-final-project
./mvnw -Dexec.skip=true spring-boot:run
```

Then open http://localhost:8080. The H2 database file is created in your home directory (`~/spring-boot-h2-hotel-reservation.mv.db`).

### Full build (rebuilds the Angular frontend)
The Maven build runs `ng build` from `src/main/UI` and writes the output to `src/main/resources/static`, so install the frontend dependencies first and make sure `ng` is on your `PATH`:

```bash
cd src/main/UI
npm install
export PATH="$PWD/node_modules/.bin:$PATH"   # or: npm install -g @angular/cli@14
cd ../../..
./mvnw clean package
java -jar target/hotel-reservation-0.0.2-SNAPSHOT.jar
```

### Frontend dev server (optional)
```bash
cd src/main/UI
npm install
npm start          # http://localhost:4200, calls the backend at http://localhost:8080
```

### Tests
```bash
./mvnw -Dexec.skip=true test
```
This runs a Spring Boot context-load test.

### Docker
```bash
./mvnw -Dexec.skip=true clean package
docker build -t hotel-reservation .
docker run -p 8080:8080 hotel-reservation
```

## Project structure

```
.
├── Dockerfile
├── pom.xml
├── mvnw, mvnw.cmd, .mvn/            Maven wrapper
└── src
    ├── main
    │   ├── java/card/andrew/hotelreservation
    │   │   ├── HotelReservationApplication.java   Spring Boot entry point
    │   │   ├── H2Bootstrap.java                   seeds rooms at startup
    │   │   ├── ServletInitializer.java
    │   │   ├── config/        CORS, converter registration, MVC/JPA config
    │   │   ├── convertor/     entity/model converters, room and reservation services, TimeZoneConverter
    │   │   ├── entity/        RoomEntity, ReservationEntity (JPA)
    │   │   ├── model/         request/response models and HATEOAS-style links
    │   │   ├── repository/    Spring Data repositories
    │   │   └── rest/          ReservationResource, WelcomeController
    │   ├── resources
    │   │   ├── application.properties
    │   │   ├── Welcome_en_US.properties, Welcome_fr_CA.properties
    │   │   └── static/        built Angular app served by Spring Boot
    │   └── UI/                Angular 14 source (src/app/app.component.*)
    └── test/java/card/andrew/hotelreservation
        └── HotelReservationApplicationTests.java
```
