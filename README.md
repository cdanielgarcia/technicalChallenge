# 🧮 Technical Challenge API

This project is a backend system built with **Spring Boot 3**, **Java 21**, **PostgreSQL**, **Docker**, and **Docker Compose**.  
It provides a **REST API** to perform percentage-based calculations and **logs every call into a database**.

---

## 🛠 How to Run the Project

1. Package the application:
```bash
   ./mvnw clean package
```
2. Start the containers:
```bash
   docker-compose up --build
```
   This will:
    - Start a **PostgreSQL 14** database (`technicalChallenge-db`).
    - Build and run the **Spring Boot application** (`technicalChallenge-api`).

3. Access the API locally:

   http://localhost:8080/

---
## 📦 Running the Application from Docker Hub

You can also pull and run the pre-built Docker image:

Pull the image:
```bash
docker pull danielgarcia10/technicalchallenge-api:latest
```
Run the container:
```bash
docker run -p 8080:8080 danielgarcia10/technicalchallenge-api:latest
```
Important: Make sure your PostgreSQL database is running and accessible before starting the application.

If you don't have a database, use the provided docker-compose.yml to bring up PostgreSQL.

## 📬 Available Endpoints

### 1. POST `/api/calculate`

Calculates the sum of two numbers and applies a cached or freshly fetched percentage.

- **Method:** POST
- **URL:** http://localhost:8080/api/calculate
- **Headers:**
    - Content-Type: application/json
- **Body Example:**

  {
  "num1": 100,
  "num2": 200
  }

- **Success Response (200 OK):**

  {
  "result": 330.0,
  "percentageUsed": 0.1
  }

- **Possible Errors:**
    - 400 Bad Request: if the request is malformed.
    - 503 Service Unavailable: if no external percentage could be fetched and no cached value exists.

---

### 2. GET `/api/history`

Retrieves the paginated call history of previous calculations.

- **Method:** GET
- **URL:** http://localhost:8080/api/history?page=0&size=10 
- **Query Parameters:**
  - `page`: (default = 0) the page number to retrieve. **Starts from 0**.
  - `size`: (default = 10) how many records you want per page.

📌 **Note:**  
If you have more than 10 calculations, you can increase the `page` number (e.g., `page=1`, `page=2`, etc.) to fetch additional results.
- **Success Response (200 OK):**

  {
  "content": [
  {
  "id": 1,
  "endpoint": "/calculate",
  "parameters": "CalculationRequest(num1=100.0, num2=200.0)",
  "response": "CalculationResponse(result=330.0, percentageUsed=0.1)",
  "success": true,
  "timestamp": "ActualTimeZone"
  }
  ],
  "totalPages": 1,
  "totalElements": 1,
  "last": true
  }

---

## 🧠 Technical Details

### 🔹 Percentage Caching

- The percentage is fetched once every **30 minutes**.
- If the external fetch fails:
    - The last cached value is used.
    - If no cached value exists, an ExternalServiceException is thrown (503 error).

### 🔹 Logging

- Every call to `/calculate` is **logged asynchronously** into the database (`call_log` table).
- Logged fields:
    - endpoint
    - parameters
    - response
    - success
    - timestamp

### 🔹 Error Handling

Managed globally with `@ControllerAdvice`:

| Type of Error            | HTTP Status             |
| ------------------------- | ----------------------- |
| Validation errors         | 400 Bad Request          |
| Type mismatch errors      | 400 Bad Request          |
| General server errors     | 500 Internal Server Error |
| External service failure  | 503 Service Unavailable  |

---

## 📦 Main Classes Overview

| Class                       | Purpose |
| ---------------------------- | ------- |
| `CalculationController`      | Handles HTTP requests (`/api/calculate`, `/api/history`) |
| `CalculationService`         | Performs the business calculations |
| `PercentageService`          | Manages caching and fetching of percentage |
| `StaticPercentageProvider`   | Simulates fetching percentage from external source |
| `CallLogService`             | Logs API calls asynchronously |
| `GlobalExceptionHandler`     | Handles all application exceptions globally |
| `CallLogRepository`          | JPA Repository for call logs |
| `CalculationRequest` / `CalculationResponse` | DTOs for request and response |

---

## 🧪 Testing

- Unit tests cover:
    - Cached percentage usage.
    - Fallback to cached value on external service failure.
    - Correct calculation logic.

- **Technologies used:**
    - JUnit 5
    - Mockito

---

## 🐳 Docker Setup

- **Dockerfile:** Builds a Java 21 environment with the packaged `.jar`.
- **docker-compose.yml:** Launches both PostgreSQL and the Spring Boot application.

- **Ports Exposed:**
    - API → 8080
    - Database → 5432

---

## ✅ Final Notes

- Built with **Java 21** and tested with **Postman**.
- Best practices applied:
    - Caching
    - Exception Handling
    - Logging
    - Asynchronous Processing
- Fully dockerized and easy to deploy locally for testing or development.

---
