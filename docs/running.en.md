# Local run guide

## Prerequisites

- Java 25
- Docker + Docker Compose

## Start dependencies with Docker Compose

The project includes `compose.yaml` to start PostgreSQL.

```bash
docker compose up -d
```

To stop services:

```bash
docker compose down
```

## Run the API

```bash
./mvnw spring-boot:run
```

By default, the API runs at `http://localhost:8080`.

## Swagger UI

With the API running:

- Interactive UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON spec: `http://localhost:8080/v3/api-docs`

## Base endpoints

- `POST /api/bookings`
- `GET /api/bookings/{bookingId}`
- `GET /api/bookings`
- `PATCH /api/bookings/{bookingId}/confirm`
- `PATCH /api/bookings/{bookingId}/cancel`
