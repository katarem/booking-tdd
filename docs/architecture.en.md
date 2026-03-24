# Architecture

## Architectural style

The project follows a hexagonal architecture (Ports & Adapters), separating responsibilities into domain, application, and infrastructure.

## Layers

### 1) Domain

Owns pure business rules and model state.

- Models: `Booking`, `Room`, `BookingList`, `BookingStatus`
- Business policies: `ProfanityPolicy`, `TolerancePolicy`
- Query criteria: `BookingCriteria`, `PageCriteria`, `OrderCriteria`

Main paths:

- `src/main/java/io/github/katarem/domain/model`
- `src/main/java/io/github/katarem/domain/policy`
- `src/main/java/io/github/katarem/domain/criteria`

### 2) Application

Orchestrates use cases and defines input/output contracts.

- Input ports (use cases): create, get, list, confirm, cancel
- Output ports: booking persistence plus employee/room queries
- Use case implementations: `CreateBookingUseCaseImpl`, `GetBookingUseCaseImpl`, etc.

Main paths:

- `src/main/java/io/github/katarem/application/port/input`
- `src/main/java/io/github/katarem/application/port/output`
- `src/main/java/io/github/katarem/application/usecase`

### 3) Infrastructure

Implements adapters, mappers, persistence, and HTTP exposure.

- Input REST adapter: `BookingApi`
- Output adapters:
  - JPA persistence: `BookingPersistenceAdapter`, `BookingJpaOutput`
  - Simulated external clients: `EmployeeOutputAdapter`, `RoomOutputAdapter`
- Mappers: `BookingRestMapper`, `BookingPersistenceMapper`, `RoomMapper`
- HTTP error handling: `BookingExceptionHandler`
- Configuration: `BeanConfig`, `PolicyConfig`

Main paths:

- `src/main/java/io/github/katarem/infraestructure/adapter`
- `src/main/java/io/github/katarem/infraestructure/mapper`
- `src/main/java/io/github/katarem/infraestructure/config`
- `src/main/java/io/github/katarem/infraestructure/exception`

## Endpoint flow

1. `BookingApi` receives and validates the HTTP request DTO.
2. A mapper converts request DTO to domain model.
3. The use case applies business rules.
4. Output ports delegate to infrastructure adapters.
5. A mapper converts domain model to response DTO.
6. `BookingExceptionHandler` maps business exceptions to HTTP status codes.

## Exposed endpoints

- `POST /api/bookings`
- `GET /api/bookings/{bookingId}`
- `GET /api/bookings`
- `PATCH /api/bookings/{bookingId}/confirm`
- `PATCH /api/bookings/{bookingId}/cancel`
