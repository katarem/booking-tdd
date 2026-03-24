# Cobertura de testing

## Estrategia actual

El proyecto usa una estrategia mixta:

- Unit tests para dominio, casos de uso y adaptadores
- Web tests (`@WebMvcTest`) para contrato HTTP de la API
- Data tests para el adaptador JPA

## Cobertura funcional por capa

### Dominio

- Reglas de fechas y estados de `Booking`
- Reglas de tolerancia (`TolerancePolicy`)
- Control de lenguaje prohibido (`ProfanityPolicy`)

Archivos relevantes:

- `src/test/java/io/github/katarem/unit/domain/BookingTest.java`
- `src/test/java/io/github/katarem/unit/domain/ProfanityPolicyTest.java`
- `src/test/java/io/github/katarem/unit/domain/policy/TolerancePolicyTest.java`

### Aplicación

- Crear reserva (validaciones y orquestación)
- Confirmar reserva
- Cancelar reserva
- Obtener reserva por id
- Listar reservas

Archivos relevantes:

- `src/test/java/io/github/katarem/unit/application/CreateBookingUseCaseTest.java`
- `src/test/java/io/github/katarem/unit/application/ConfirmBookingUseCaseTest.java`
- `src/test/java/io/github/katarem/unit/application/CancelBookingUseCaseTest.java`
- `src/test/java/io/github/katarem/unit/application/GetBookingUseCaseTest.java`
- `src/test/java/io/github/katarem/unit/application/ListBookingUseCaseTest.java`

### Infraestructura

- Adaptadores de salida (employee, room, booking)
- Persistencia JPA
- API REST y mapeo de errores HTTP

Archivos relevantes:

- `src/test/java/io/github/katarem/unit/infraestructure/adapter/output/EmployeeOutputAdapterTest.java`
- `src/test/java/io/github/katarem/unit/infraestructure/adapter/output/RoomOutputAdapterTest.java`
- `src/test/java/io/github/katarem/unit/infraestructure/adapter/output/BookingOutputAdapterTest.java`
- `src/test/java/io/github/katarem/data/infrastructure/BookingJpaOutputTest.java`
- `src/test/java/io/github/katarem/web/BookingApiTest.java`

## Cobertura de endpoints (WebMvc)

`BookingApiTest` cubre los endpoints expuestos:

- `POST /api/bookings`
- `GET /api/bookings/{bookingId}`
- `GET /api/bookings`
- `PATCH /api/bookings/{bookingId}/confirm`
- `PATCH /api/bookings/{bookingId}/cancel`

Incluye escenarios de estado exitoso y de error (`400`, `404`, `409`, `500`) para varios flujos.

## Métrica de porcentaje

Actualmente no hay integración de JaCoCo en `pom.xml`, por lo que no se genera un porcentaje de cobertura en el build.

## Estado de ejecución local

En este entorno, la ejecución de `./mvnw test` falla por versión de Java no soportada en el runtime actual:

- Error: `release version 25 not supported`

Para ejecutar la suite localmente, se requiere un JDK compatible con `java.version=25` o ajustar esa propiedad.
