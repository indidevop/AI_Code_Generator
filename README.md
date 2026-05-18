# 🚀 AI based Code Generator Platform

Production-oriented backend system built using **Spring Boot 4** and **Java 17** for AI-assisted code generation (vibe coding platform).

---

## 📖 Overview

This Spring Boot application helps in generating code files/projects based on prompts provided by the user.

---

## 🛠️ Tech Stack

- ☕ Java 17
- 🌱 Spring Boot 4
- 🔐 Spring Security
- 🗄️ Spring Data JPA
- 🐘 PostgreSQL
- 🤖 Spring AI + OpenAI
- 🔄 MapStruct
- ⚡ Redis
- 🐳 Docker
- ☸️ Kubernetes
- 📦 MinIO
- 💳 Stripe

---

## ✨ Features

- 🔐 JWT Authentication & Authorization
- 👤 User & Workspace Management
- 📂 Project & Template Management
- 🤖 AI Code Generation APIs
- 📁 File Upload & Storage with MinIO
- 💳 Subscription Billing with Stripe
- ⚡ Redis Caching
- ☸️ Kubernetes Deployment

---

## 🏗️ Architecture

Layered Architecture:

```text
Controller → Service → Repository → Database
```

### Additional Integrations

- 🤖 OpenAI for AI-assisted workflows
- 📦 MinIO for object/file storage
- 💳 Stripe for billing/subscriptions
- ⚡ Redis for caching

---

## 🗄️ Database Design

### ER Diagram

![ER Diagram](./docs/er-diagram.jpg)

---

## 📁 Repository Structure

```bash
src/
 ├── main/
 │   ├── java/
 │   │   └── com.springboot.AI_Code_Generator/
 │   │       ├── controller/    # REST Controllers
 │   │       ├── dto/           # Request & Response DTOs
 │   │       ├── entity/        # JPA Entities
 │   │       ├── enums/         # Enum Definitions
 │   │       ├── mapper/        # MapStruct Mappers
 │   │       ├── repository/    # Spring Data JPA Repositories
 │   │       ├── service/       # Business Logic Layer
 │   │       └── AiCodeGeneratorApplication.java
 │   │
 │   └── resources/
 │       ├── static/
 │       ├── templates/
 │       └── application.yaml
 │
 └── test/
```

---

## 🔌 API Modules

### 📂 Project API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/project` | Retrieve all projects for a user |
| GET | `/api/project/{id}` | Retrieve project using ID |
| POST | `/api/project` | Create a new project |
| PATCH | `/api/project/{id}` | Update project details |
| DELETE | `/api/project/{id}` | Soft delete a project |

---

### 👥 Project Member API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/projects/{projectId}/members` | Retrieve all members associated with a project |
| POST | `/api/projects/{projectId}/members` | Invite a user to join the project |
| PATCH | `/api/projects/{projectId}/members/{memberId}` | Update a member's role in the project |
| DELETE | `/api/projects/{projectId}/members/{memberId}` | Remove a member from the project |

---

```bash
# More endpoints will be added as the development progresses.
```
---

## 🎯 Learning Goals

This project is focused on understanding:

- 🏗️ Production-ready backend architecture
- 🔐 Secure API design
- 🤖 AI-assisted development workflows
- ☁️ Distributed/cloud-native deployment concepts
- ⚡ Scalable database and caching strategies

---

## 🚀 Future Improvements

- 🔄 CI/CD Pipeline
- 📊 Monitoring & Logging
- 🚦 Rate Limiting
- 📡 Event-Driven Architecture
- 🧩 Microservices Exploration

---

## ▶️ Running the Project

```bash
# Coming Soon
```

---

## 📌 Project Status

Current Progress:

- ✅ Feature Planning
- ✅ API Planning
- ✅ Database Modeling
- ✅ Project APIs
- ✅ Project Member APIs

---

## 📜 License

This project is intended for learning and educational purposes.
