🧪 TDD Practice — Room Booking API

🎯 Objetivo

Construir una API REST en Spring Boot usando TDD que gestione reservas de salas.

⸻

📦 Dominio

Una reserva (Booking) tiene:
•	id
•	employeeId
•	roomId
•	title
•	description
•	startDateTime
•	endDateTime
•	status (PENDING, CONFIRMED, CANCELLED)
•	attendeesCount
•	createdAt

⸻

🚀 Endpoints

1. Crear reserva

POST /api/bookings

Request

{
"employeeId": 12,
"roomId": 3,
"title": "Sprint planning",
"description": "Planificación del sprint",
"startDateTime": "2026-03-20T10:00:00",
"endDateTime": "2026-03-20T11:00:00",
"attendeesCount": 6
}

Respuesta
•	201 Created

⸻

2. Confirmar reserva

PATCH /api/bookings/{id}/confirm

⸻

3. Cancelar reserva

PATCH /api/bookings/{id}/cancel

⸻

4. Obtener reserva

GET /api/bookings/{id}

⸻

5. Listar reservas

GET /api/bookings

Query params:
•	employeeId
•	roomId
•	status
•	date
•	page
•	size

⸻

✅ Validaciones

Básicas
•	title obligatorio
•	title: 3–100 chars
•	description ≤ 500 chars
•	attendeesCount > 0

Fechas
•	start < end
•	mínimo 30 min
•	máximo 8h
•	no en el pasado
•	horario permitido: 08:00–20:00

Contenido

Palabras prohibidas:

List.of("mierda", "gilipollas", "idiota")

Cruzadas
•	attendeesCount ≤ capacidad sala
•	no solapes

⸻

🔁 Reglas de negocio

Creación
•	empleado debe existir
•	sala debe existir
•	sala debe estar activa
•	estado inicial: PENDING

Confirmación
•	solo desde PENDING
•	validar conflicto otra vez

Cancelación
•	no cancelar si <15 min

⸻

🔌 Integraciones externas

EmployeeClient
•	existsById

RoomClient
•	getRoomById
•	id
•	capacity
•	active

⸻

💥 Errores

400
•	validaciones

404
•	booking
•	employee
•	room

409
•	conflictos
•	estados inválidos

⸻

🧠 Arquitectura sugerida
•	Controller → HTTP
•	Service (use case) → orquestación
•	Validator → reglas
•	Mapper → transformación
•	Client → externos
•	Repository → persistencia

⸻

🧪 Testing (TDD)

Unit tests

Validator
•	title vacío
•	title largo
•	profanity
•	fechas inválidas

Service
•	create OK
•	employee no existe
•	sala no existe
•	conflicto

Confirm
•	OK
•	estado inválido

Cancel
•	OK
•	<15 min

⸻

Web tests
•	POST → 201 / 400 / 404 / 409
•	PATCH confirm
•	PATCH cancel
•	GET by id

⸻

🔄 TDD Iterations
1.	Crear reserva básica
2.	Validaciones simples
3.	Validaciones de tiempo
4.	Profanity
5.	Integraciones externas
6.	Capacidad
7.	Solapes
8.	Confirmar
9.	Cancelar
10.	Listado

⸻

📌 Excepciones sugeridas
•	BookingNotFoundException
•	EmployeeNotFoundException
•	RoomNotFoundException
•	BookingConflictException
•	InvalidBookingTimeException
•	ProfanityDetectedException
•	CapacityExceededException

⸻

💡 Regla clave
•	Optional → consultas
•	Exception → negocio
•	Exception técnica → infra

⸻

🎯 Objetivo final
•	Controllers finos
•	Services claros
•	Validators aislados
•	Tests robustos
•	Bajo acoplamiento