# Testing automatizado con Postman (ES)

Esta carpeta centraliza las pruebas automatizadas de todos los endpoints HTTP de la API.

## Estructura

- Colección Postman: `tests/postman/booking-tdd.postman_collection.json`
- Environment local: `tests/postman/local.postman_environment.json`

## Endpoints cubiertos

- `POST /api/bookings`
- `GET /api/bookings/{bookingId}`
- `GET /api/bookings`
- `PATCH /api/bookings/{bookingId}/confirm`
- `PATCH /api/bookings/{bookingId}/cancel`

## Requisitos para ejecutar

1. API levantada en `http://localhost:8080`
2. Dependencias activas con Docker Compose (`docker compose up -d`)
3. Postman o Newman instalado

## Ejecución en Postman

1. Importar la colección `booking-tdd.postman_collection.json`.
2. Importar el environment `local.postman_environment.json`.
3. Seleccionar el environment `Booking TDD Local`.
4. Ejecutar la colección completa con Collection Runner.

## Ejecución por CLI (Newman)

```bash
npx newman run tests/postman/booking-tdd.postman_collection.json \
  -e tests/postman/local.postman_environment.json
```

## Notas

- La colección crea una reserva, reutiliza su `id` y prueba el ciclo básico de vida (get, confirm, cancel).
- Los timestamps se generan en prerequest para evitar fechas inválidas en ejecuciones repetidas.
