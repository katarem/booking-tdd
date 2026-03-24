# Requerimientos

## Objetivo

Construir una API REST para gestionar reservas de salas, aplicando TDD y reglas de negocio de validación, conflicto y ciclo de vida de la reserva.

## Requerimientos funcionales

### Entidad Booking

Campos principales:

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

- `POST /api/bookings` (crear)
- `PATCH /api/bookings/{id}/confirm` (confirmar)
- `PATCH /api/bookings/{id}/cancel` (cancelar)
- `GET /api/bookings/{id}` (obtener por id)
- `GET /api/bookings` (listar con filtros y paginación)

Filtros soportados en listado:

- `employeeId`
- `roomId`
- `status`
- `date`
- `page`
- `size`
- `sort`

## Validaciones de negocio

### Validaciones básicas

- `title` obligatorio y con tamaño `3..100`
- `description` obligatorio y máximo `500`
- `attendeesCount >= 1`
- `employeeId >= 1`
- `roomId >= 1`

### Fechas

- `startDateTime < endDateTime`
- Duración mínima: 30 minutos
- Duración máxima: 8 horas
- Franja permitida: 08:00 a 20:00
- Tolerancia de inicio configurable (`tolerance.booking-start-date`)

### Reglas cruzadas

- El empleado debe existir
- La sala debe existir
- La sala debe estar activa
- `attendeesCount` no puede exceder capacidad de sala
- No se permiten solapes para la misma sala

### Estados

- Estado inicial al crear: `PENDING`
- Confirmar solo si está en `PENDING`
- Cancelar no permitido si faltan menos de 15 minutos para el inicio

## Requerimientos no funcionales

- Arquitectura por capas con Ports & Adapters
- Persistencia con Spring Data JPA
- Exposición REST con Spring MVC
- Validación de requests con Jakarta Validation
- Documentación de API con Swagger UI (OpenAPI)
- Soporte de arranque de dependencias con Docker Compose

## Requerimientos técnicos

- Java `25` (según `pom.xml`)
- Maven Wrapper (`./mvnw`)
- Docker y Docker Compose
