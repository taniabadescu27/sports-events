# Sports Events Tracker

A Spring Boot microservice that tracks live sports events by accepting status updates, for each event live calls an external APIs, transforms the response into a
message and publishing to Kafka.

## 🚀 Features

- `POST /events/status` endpoint that accepts a JSON payload.
- Scheduled polling every 10 seconds to fetch updates for live events.
- Publishes live event data to Kafka with retry logic and logging.
- Kafka retry handling with `RetryTemplate`.
- Unit and integration tests included.

---

## 🛠️ Technologies

- Java 17+
- Spring Boot
- Spring Kafka
- Maven
- JUnit 5
- Mockito
- Embedded Kafka (for integration testing)

---

## 📦 Setup & Run

### 1. Clone the repository

```bash
git clone https://github.com/taniabadescu27/sports-events.git
cd sports-events
```

### 2. Start Kafka (locally or via Docker)

If using Docker:

```bash
docker-compose up -d
```

### 3. Run the application

```bash
mvn spring-boot:run
```
The service will start at:

```
http://localhost:8080
```

## 📮 API Usage

### POST /events/status

Updates the statuses of one or more events.

#### 🔧 Request Example

**Method**: `POST`  
**URL**: `http://localhost:8080/events/status`  
**Headers**:
- `Content-Type: application/json`

**Body (raw JSON)**:
```json
[
  {
    "eventId": "56444",
    "status": "LIVE"
  },
  {
    "eventId": "98711",
    "status": "NOT_LIVE"
  }
]
```
### ✅ Tested using Postman:
The above request was successfully tested using Postman by sending it to the endpoint while the application was running locally. A 200 OK response confirms the update was processed correctly.


## ✅ Running Tests

### Run all tests:

```bash
mvn test
```
This will run all unit and integration tests in the junit.tests package.


## 🧠 Design Decisions

- **Used a `ConcurrentHashMap`** to store event status safely across threads, ensuring thread-safety in concurrent operations.
  
- **Polling of live events** is scheduled every 10 seconds using `@Scheduled` to ensure the system periodically fetches updates.

- **Kafka publishing** is wrapped in a `RetryTemplate` to handle transient failures and ensure reliable delivery of messages.

- **Logging**: All failures are logged properly, and the system is designed to be resilient, retrying failed operations and providing detailed logging for debugging and monitoring.

- **@Valid** and custom validation used to enforce eventId and status input correctness.


## 🤖 AI-Assisted Parts

- Some classes and methods (e.g., `EventPublishingService`, test templates) were initially generated using ChatGPT.

- All AI-generated code was:
  - Reviewed for correctness and compliance with project structure.
  - Modified to ensure testability and integration with existing components.
  - Verified using unit/integration tests.

- Documentation (this README) was also assisted with AI suggestions and edited manually.

### 📁 Folder Structure

```
src/
 └── main/
     └── java/com/example/tracker/
         ├── client/
         ├── config/
         ├── controller/
         ├── exceptions/
         ├── model/
         └── service/

 └── test/
     └── java/com/example/tracker/
```

