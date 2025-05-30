# 💸 Currency Converter API

This project is a currency conversion API built with **Node.js**, **TypeScript**, and **NestJS**, using **Prisma ORM** and **PostgreSQL**. It enables conversion between currencies, stores each transaction, and provides a RESTful interface to query conversion history.

---

## 🧠 Project Overview

The goal of this project is to implement a backend API that allows converting between currencies using real-time exchange rates from [currencyapi.com](https://currencyapi.com/), with transaction persistence and proper API design.

### ✅ Core Features

- Convert between the following currencies:
  - BRL (Brazilian Real)
  - USD (US Dollar)
  - EUR (Euro)
  - JPY (Japanese Yen)
- Store each conversion transaction with:
  - User ID
  - Origin and destination currencies
  - Original value
  - Converted value
  - Exchange rate
  - UTC timestamp
- Retrieve transactions by user:
  - `GET /transactions?userId=123`

---

## 🏗️ Architecture Overview

The project uses **layered architecture** inspired by **Clean Architecture**, promoting separation of concerns and scalability:

├── src
│   ├── modules
│   │   └── transactions
│   │       ├── controllers
│   │       ├── services
│   │       ├── repositories
│   │       └── dto
│   ├── shared
│   │   ├── database
│   │   ├── logger
│   │   └── exceptions
│   ├── config
│   └── main.ts


### ⚙️ Tech Stack

| Layer        | Technology            | Reason                                                  |
|--------------|------------------------|----------------------------------------------------------|
| Framework    | NestJS                 | Modular, TypeScript-friendly, scalable, DI built-in      |
| ORM          | Prisma                 | Type-safe, intuitive schema and database migrations      |
| Database     | PostgreSQL             | Reliable relational DB                                   |
| HTTP Client  | Axios                  | Simple and widely adopted                                |
| Logger       | Pino or Winston        | Structured logging                                       |
| Testing      | Jest                   | Officially supported by NestJS                           |
| API Docs     | Swagger via @nestjs/swagger | Easy-to-use interface and docs                    |
| Deployment   | Railway / Render       | Fast, free hosting platforms                             |
| CI/CD        | GitHub Actions         | Automates tests, lint, and builds                        |

---
## 📦 Installation & Running Locally

### Prerequisites

- Node.js >= 18
- PostgreSQL or SQLite installed (or use Docker)
- `currencyapi.com` API key (free plan available)

### 1. Clone the repo

```bash
git clone https://github.com/your-username/currency-converter-api.git
cd currency-converter-api
```

### 2. Install dependencies

```bash
npm install
```

### 3. Setup environment variables

Copy the example `.env` file and edit your credentials:

```bash
cp .env.example .env
```

Edit `.env`:

```env
DATABASE_URL="postgresql://USER:PASSWORD@localhost:5432/currency_db?schema=public"
CURRENCY_API_KEY="your_currencyapi_key"
```

### 4. Generate Prisma Client

```bash
npx prisma generate
```

### 5. Run database migration

```bash
npx prisma migrate dev --name init
```

### 6. Start the app

```bash
npm run start:dev
```
Access the API at: http://localhost:3000

### Available Scripts
```bash
# Run development server
npm run start:dev

# Format code with Prettier
npm run format

# Lint the codebase
npm run lint

# Run unit tests
npm run test

# Run Prisma Client generation
npm run prisma:generate

# Run Prisma migration
npm run prisma:migrate
```