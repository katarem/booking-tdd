# Automated Postman testing (EN)

This folder contains automated tests for all HTTP endpoints in the API.

## Structure

- Postman collection: `tests/postman/booking-tdd.postman_collection.json`
- Local environment: `tests/postman/local.postman_environment.json`

## Covered endpoints

- `POST /api/bookings`
- `GET /api/bookings/{bookingId}`
- `GET /api/bookings`
- `PATCH /api/bookings/{bookingId}/confirm`
- `PATCH /api/bookings/{bookingId}/cancel`

## Run requirements

1. API running at `http://localhost:8080`
2. Dependencies up with Docker Compose (`docker compose up -d`)
3. Postman or Newman installed

## Run in Postman

1. Import `booking-tdd.postman_collection.json`.
2. Import `local.postman_environment.json`.
3. Select `Booking TDD Local` environment.
4. Run the full collection with Collection Runner.

## Run from CLI (Newman)

```bash
npx newman run tests/postman/booking-tdd.postman_collection.json \
  -e tests/postman/local.postman_environment.json
```

## Notes

- The collection creates a booking, reuses its `id`, and validates the basic lifecycle (get, confirm, cancel).
- Timestamps are generated in prerequest scripts to avoid invalid dates across repeated runs.
