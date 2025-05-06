💱 Currency Converter API
A RESTful API for currency conversion, designed to handle real-time exchange rates and log every transaction performed by users. Built as part of a technical challenge, this project demonstrates clean architecture, data persistence, and integration with external APIs using modern Kotlin and MySQL.

🧩 Features
Convert between at least four currencies: BRL, USD, EUR, and JPY
Real-time exchange rates via exchangeratesapi.io (free tier, base currency: EUR)
Requires a user ID to perform conversions

Persists every successful transaction with:
User ID
Source and target currencies
Original and converted values
Conversion rate
Timestamp (UTC)
Exposes endpoints to:
Perform currency conversions
Retrieve a user’s past transactions
Proper HTTP status codes and error messages
MySQL for relational data persistence
Satisfactory test coverage

🛠 Tech Stack
Kotlin
Spring Boot
MySQL
REST API
JUnit for unit/integration tests

📑 Folder Structure & Architecture
The application follows a clean and modular architecture with separation of responsibilities:

controller: Handles HTTP requests and API responses
service: Contains the business logic
repository: Interfaces for database access using Spring Data JPA
entity/model: Data definitions
dto: Data Transfer Objects used in the API
exception: Centralized error handling

🚀 How to Run
Make sure you have MySQL running and a database created (e.g., currency_db).

Update your application.yml or application.properties with your DB credentials.

Then:

bash
Copiar
Editar
# Clone the project
git clone https://github.com/your-user/currency-converter-api.git
cd currency-converter-api

# Run with Maven or Gradle (depending on your setup)
./mvnw spring-boot:run
# or
./gradlew bootRun


Once running, access the API at: http://localhost:8080