# Arquitectura

## Estilo arquitectónico

El proyecto sigue un enfoque de arquitectura hexagonal (Ports & Adapters), separando responsabilidades en dominio, aplicación e infraestructura.

## Capas

### 1) Dominio

Responsable de reglas de negocio puras y estado del modelo.

- Modelos: `Booking`, `Room`, `BookingList`, `BookingStatus`
- Políticas de negocio: `ProfanityPolicy`, `TolerancePolicy`
- Criterios de consulta: `BookingCriteria`, `PageCriteria`, `OrderCriteria`

Rutas principales:

- `src/main/java/io/github/katarem/domain/model`
- `src/main/java/io/github/katarem/domain/policy`
- `src/main/java/io/github/katarem/domain/criteria`

### 2) Aplicación

Orquesta los casos de uso y define contratos de entrada/salida.

- Input ports (casos de uso): create, get, list, confirm, cancel
- Output ports: persistencia de bookings y consultas de empleados/salas
- Implementaciones de casos de uso: `CreateBookingUseCaseImpl`, `GetBookingUseCaseImpl`, etc.

Rutas principales:

- `src/main/java/io/github/katarem/application/port/input`
- `src/main/java/io/github/katarem/application/port/output`
- `src/main/java/io/github/katarem/application/usecase`

### 3) Infraestructura

Implementa adaptadores, mapeadores, persistencia y exposición HTTP.

- Adaptador REST de entrada: `BookingApi`
- Adaptadores de salida:
  - Persistencia JPA: `BookingPersistenceAdapter`, `BookingJpaOutput`
  - Clientes externos simulados: `EmployeeOutputAdapter`, `RoomOutputAdapter`
- Mapeadores: `BookingRestMapper`, `BookingPersistenceMapper`, `RoomMapper`
- Manejo de errores HTTP: `BookingExceptionHandler`
- Configuración: `BeanConfig`, `PolicyConfig`

Rutas principales:

- `src/main/java/io/github/katarem/infraestructure/adapter`
- `src/main/java/io/github/katarem/infraestructure/mapper`
- `src/main/java/io/github/katarem/infraestructure/config`
- `src/main/java/io/github/katarem/infraestructure/exception`

## Flujo por endpoint

1. `BookingApi` recibe request HTTP y valida DTO.
2. Mapper transforma request a modelo de dominio.
3. Caso de uso aplica reglas de negocio.
4. Output ports delegan en adaptadores de infraestructura.
5. Mapper convierte modelo de dominio a response.
6. `BookingExceptionHandler` traduce excepciones de negocio a códigos HTTP.

## Endpoints expuestos

- `POST /api/bookings`
- `GET /api/bookings/{bookingId}`
- `GET /api/bookings`
- `PATCH /api/bookings/{bookingId}/confirm`
- `PATCH /api/bookings/{bookingId}/cancel`
