# AI Code Generator Backend

Production-oriented backend system built using Spring Boot 4 and Java 21 for AI-assisted code generation workflows.

---

## Overview

This project focuses on building a scalable backend platform that combines:

- Secure authentication & authorization
- AI-assisted code/content generation
- File storage & project management
- Subscription billing workflows
- Cloud-native deployment practices

The goal is to explore production-style backend engineering patterns and real-world integrations.

---

## Tech Stack

- Java 17
- Spring Boot 4
- Spring Security
- Spring Data JPA
- PostgreSQL
- Spring AI + OpenAI
- MapStruct
- Redis
- Docker
- Kubernetes
- MinIO
- Stripe


---

## Planned Features

- [ ] JWT Authentication & Authorization
- [ ] User & Workspace Management
- [ ] Project & Template Management
- [ ] AI Code Generation APIs
- [ ] File Upload & Storage with MinIO
- [ ] Subscription Billing with Stripe
- [ ] Redis Caching
- [ ] Kubernetes Deployment

---

## Architecture

Layered Architecture:

Controller → Service → Repository → Database

Additional integrations:
- OpenAI for AI-assisted workflows
- MinIO for object/file storage
- Stripe for billing/subscriptions
- Redis for caching

---

## Database Design

### ER Diagram

![ER Diagram](./docs/er-diagram.jpg)

---

## Project Status

Current Progress:
- ✅ Feature Planning
- ✅ API Planning
- ✅ Database Modeling
- ✅ Project APIs
- ✅ Project Member APIs

---

## API Modules



---

## Learning Goals

This project is focused on understanding:

- Production-ready backend architecture
- Secure API design
- AI-assisted development workflows
- Distributed/cloud-native deployment concepts
- Scalable database and caching strategies

---

## Future Improvements

- CI/CD Pipeline
- Monitoring & Logging
- Rate Limiting
- Event-Driven Architecture
- Microservices Exploration

---

## Running the Project

```bash
# Coming Soon
```

---

## Repository Structure

```bash
src/
 ├── controller
 ├── service
 ├── repository
 ├── dto
 ├── entity
 ├── security
 └── config
```

---

## License

This project is intended for learning and educational purposes.
