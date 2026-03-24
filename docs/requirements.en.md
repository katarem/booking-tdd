# Requirements

## Goal

Build a room booking REST API using TDD, with business rules for validation, conflict detection, and booking lifecycle transitions.

## Functional requirements

### Booking entity

Main fields:

- `id`
- `employeeId`
- `roomId`
- `title`
- `description`
- `startDateTime`
- `endDateTime`
- `status` (`PENDING`, `CONFIRMED`, `CANCELLED`)
- `attendeesCount`
- `createdAt`

### Endpoints

- `POST /api/bookings` (create)
- `PATCH /api/bookings/{id}/confirm` (confirm)
- `PATCH /api/bookings/{id}/cancel` (cancel)
- `GET /api/bookings/{id}` (get by id)
- `GET /api/bookings` (list with filters and pagination)

Supported list filters:

- `employeeId`
- `roomId`
- `status`
- `date`
- `page`
- `size`
- `sort`

## Business validations

### Basic validations

- `title` is required and must be `3..100`
- `description` is required and max `500`
- `attendeesCount >= 1`
- `employeeId >= 1`
- `roomId >= 1`

### Date/time validations

- `startDateTime < endDateTime`
- Minimum duration: 30 minutes
- Maximum duration: 8 hours
- Allowed time window: 08:00 to 20:00
- Configurable start tolerance (`tolerance.booking-start-date`)

### Cross validations

- Employee must exist
- Room must exist
- Room must be active
- `attendeesCount` cannot exceed room capacity
- No overlap for the same room

### State rules

- Initial status on creation: `PENDING`
- Confirm only from `PENDING`
- Cancellation is not allowed less than 15 minutes before start time

## Non-functional requirements

- Layered Ports & Adapters architecture
- Persistence with Spring Data JPA
- REST exposure with Spring MVC
- Request validation with Jakarta Validation
- API documentation with Swagger UI (OpenAPI)
- Dependency startup with Docker Compose

## Technical requirements

- Java `25` (as defined in `pom.xml`)
- Maven Wrapper (`./mvnw`)
- Docker and Docker Compose
