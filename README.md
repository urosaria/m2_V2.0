# M2 Project Monorepo

This repository contains both the frontend (React/TypeScript) and backend (Spring Boot) for the M2 platform.

---

## Structure

- `frontend/` – React + TypeScript admin/user web app
- `backend/` – Spring Boot REST API server
- `docker-compose.yml` – For local multi-container orchestration
- `pom.xml` – Maven project descriptor for backend

---

## Quick Start

### 1. Clone and setup

```bash
git clone <repo-url>
cd m2
```

### 2. Environment variables

- Frontend: see `frontend/.env.example` or README
- Backend: see `backend/README.md`

### 3. Run with Docker Compose (recommended)

```bash
docker-compose up --build
```

- Frontend: [http://localhost:3000](http://localhost:3000)
- Backend: [http://localhost:8080](http://localhost:8080)

### 4. Manual start

- Backend: see `backend/README.md`
- Frontend: see `frontend/README.md`

---

## Scripts

- `docker-compose up` – Start all service

