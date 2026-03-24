# Test coverage

## Current strategy

The project uses a mixed test strategy:

- Unit tests for domain, use cases, and adapters
- Web tests (`@WebMvcTest`) for API HTTP contract
- Data tests for the JPA adapter

## Functional coverage by layer

### Domain

- Date and state rules in `Booking`
- Tolerance rule (`TolerancePolicy`)
- Profanity rule (`ProfanityPolicy`)

Relevant files:

- `src/test/java/io/github/katarem/unit/domain/BookingTest.java`
- `src/test/java/io/github/katarem/unit/domain/ProfanityPolicyTest.java`
- `src/test/java/io/github/katarem/unit/domain/policy/TolerancePolicyTest.java`

### Application

- Create booking (validations and orchestration)
- Confirm booking
- Cancel booking
- Get booking by id
- List bookings

Relevant files:

- `src/test/java/io/github/katarem/unit/application/CreateBookingUseCaseTest.java`
- `src/test/java/io/github/katarem/unit/application/ConfirmBookingUseCaseTest.java`
- `src/test/java/io/github/katarem/unit/application/CancelBookingUseCaseTest.java`
- `src/test/java/io/github/katarem/unit/application/GetBookingUseCaseTest.java`
- `src/test/java/io/github/katarem/unit/application/ListBookingUseCaseTest.java`

### Infrastructure

- Output adapters (employee, room, booking)
- JPA persistence
- REST API and HTTP error mapping

Relevant files:

- `src/test/java/io/github/katarem/unit/infraestructure/adapter/output/EmployeeOutputAdapterTest.java`
- `src/test/java/io/github/katarem/unit/infraestructure/adapter/output/RoomOutputAdapterTest.java`
- `src/test/java/io/github/katarem/unit/infraestructure/adapter/output/BookingOutputAdapterTest.java`
- `src/test/java/io/github/katarem/data/infrastructure/BookingJpaOutputTest.java`
- `src/test/java/io/github/katarem/web/BookingApiTest.java`

## Endpoint coverage (WebMvc)

`BookingApiTest` covers all exposed endpoints:

- `POST /api/bookings`
- `GET /api/bookings/{bookingId}`
- `GET /api/bookings`
- `PATCH /api/bookings/{bookingId}/confirm`
- `PATCH /api/bookings/{bookingId}/cancel`

It includes success and error flows (`400`, `404`, `409`, `500`) across key scenarios.

## Percentage metric

There is currently no JaCoCo integration in `pom.xml`, so the build does not produce a numeric coverage percentage report.

## Local run status

In this environment, running `./mvnw test` fails because the active Java runtime does not support the configured release:

- Error: `release version 25 not supported`

To run tests locally, use a JDK compatible with `java.version=25` or change that property.
