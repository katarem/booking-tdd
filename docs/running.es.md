# Ejecución local

## Prerrequisitos

- Java 25
- Docker + Docker Compose

## Levantar dependencias con Docker Compose

El proyecto incluye `compose.yaml` para iniciar PostgreSQL.

```bash
docker compose up -d
```

Para detener servicios:

```bash
docker compose down
```

## Ejecutar la API

```bash
./mvnw spring-boot:run
```

Por defecto la API arranca en `http://localhost:8080`.

## Swagger UI

Con la API levantada:

- UI interactiva: `http://localhost:8080/swagger-ui/index.html`
- Especificación OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Endpoints base

- `POST /api/bookings`
- `GET /api/bookings/{bookingId}`
- `GET /api/bookings`
- `PATCH /api/bookings/{bookingId}/confirm`
- `PATCH /api/bookings/{bookingId}/cancel`
