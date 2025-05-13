💱 Currency Converter API

A RESTful API for currency conversion, designed to handle real-time exchange rates and log every transaction performed by users. Built as part of a technical challenge, this project demonstrates clean architecture, data persistence, and integration with external APIs using modern Kotlin and MySQL.

🧩 Features

- Convert between at least four currencies: BRL, USD, EUR, and JPY

- Real-time exchange rates via exchangeratesapi.io (free tier, base currency: EUR)

- Requires a user ID to perform conversions

- Persists every successful transaction with:

- User ID

- Source and target currencies

- Original and converted values

- Conversion rate

- Timestamp (UTC)

Exposes endpoints to:

- Perform currency conversions

- Retrieve a user’s past transactions

- Proper HTTP status codes and error messages

- MySQL for relational data persistence

- Satisfactory test coverage

🛠 Tech Stack

- Kotlin

- Spring Boot

- MySQL

- REST API

- JUnit for unit/integration tests

📑 Folder Structure & Architecture

- The application follows a clean and modular architecture with a clear separation of concerns:

```
src/
├── controller       ──▶️ Handles HTTP requests and API responses
├── configuration    ──▶️ Handles HTTP requests and API responses
├── service          ──▶️ Contains the business logic
├── repository       ──▶️ Interfaces for database access using Spring Data JPA
├── entity / model   ──▶️ Defines the data structures used by the application
├── dto              ──▶️ Data Transfer Objects (used in API requests/responses)
└── exception        ──▶️ Centralized exception handling for consistent error responses
```

🚀 How to Run

Make sure you have MySQL running and a database created (e.g., currency_db).

First of all, 
1. Install Docker Desktop
https://docs.docker.com/compose/install/

2. Install JDK 17
```
java -version
openjdk version "17.0.15" 2025-04-15 LTS
OpenJDK Runtime Environment Zulu17.58+21-CA (build 17.0.15+6-LTS)
OpenJDK 64-Bit Server VM Zulu17.58+21-CA (build 17.0.15+6-LTS, mixed mode, sharing)
```
```
javac -version
javac 17.0.15
```

Then:

# Clone the project
```
git clone https://github.com/your-user/currency-converter-api.git
cd currency-converter-api
```
# Create Database container
```
docker-compose -f docker-compose.yml up -d
```
# Build with Gradle
```
./gradlew build
```
# Run with Gradle
```
java -jar build/libs/currency-converter-api-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

Once running, access the API at: http://localhost:8081/swagger-ui/index.html

📑 Folder Structure & Architecture
