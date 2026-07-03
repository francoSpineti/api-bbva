# BBVA Client Management API

API RESTful desarrollada en Spring Boot (Java 17) para la gestión de clientes y sus productos bancarios asociados. Cuenta con persistencia en MySQL y seguridad robusta implementada mediante JWT (JSON Web Tokens).

## Requisitos Previos

- **Java 17**
- **Docker** y **Docker Compose**
- **Maven**

---

## 1. Levantar la Base de Datos (MySQL)

La base de datos corre dentro de un contenedor Docker en el puerto **3306**.

Para iniciar la base de datos, ejecuta el siguiente comando en la raíz del proyecto:

```bash
docker-compose up -d
```
---

## 2. Compilar y Ejecutar Pruebas

Para asegurarte de que todo compile correctamente y pasar la suite de pruebas unitarias e integración (JUnit 5 + Mockito), ejecuta:

**En Windows (PowerShell):**
```powershell
$env:JAVA_HOME="D:\JDK\jdk-23.0.1" # Modifica la ruta si tu JDK 17+ se encuentra en otro lugar
.\mvnw.cmd clean test
```

**En Linux / macOS:**
```bash
./mvnw clean test
```

---

## 3. Iniciar la Aplicación

Para arrancar el servidor en `http://localhost:8080`, ejecuta:

**En Windows (PowerShell):**
```powershell
$env:JAVA_HOME="D:\JDK\jdk-23.0.1"
.\mvnw.cmd spring-boot:run
```

**En Linux / macOS:**
```bash
./mvnw spring-boot:run
```

---

## 4. Estructura de la Colección de API (Postman)

Se ha creado un archivo de colección listo para importar en Postman en la raíz del proyecto:
 `bbva-crud-api.postman_collection.json`

### Flujo de Uso de Seguridad (JWT)

Todos los endpoints de gestión de clientes (`/api/v1/clients/**`) están protegidos. Sigue estos pasos para probar la API:

1. **Registrar un Usuario**: Envía un `POST` a `/api/v1/auth/register` con las credenciales que desees crear.
2. **Iniciar Sesión**: Envía un `POST` a `/api/v1/auth/login` con esas credenciales. El endpoint te devolverá un campo `token`.
3. **Autorizar Peticiones**: Copia ese token. En tus peticiones a `/api/v1/clients/**`, dirígete a la pestaña **Authorization** en Postman, selecciona **Bearer Token**, y pega el token en el campo correspondiente.

---

## Endpoints Disponibles

### Autenticación (Público)
- `POST /api/v1/auth/register` - Crear un usuario nuevo.
- `POST /api/v1/auth/login` - Obtener el token JWT.

### Clientes (Requiere Bearer Token JWT)
- `POST /api/v1/clients` - Crear un cliente (debe tener al menos 1 producto bancario en el campo `productoBancario`).
- `GET /api/v1/clients/all?page=0&size=20` - Obtener listado paginado de todos los clientes.
- `GET /api/v1/clients?id=1` - Obtener un cliente por su ID.
- `PATCH /api/v1/clients/{id}?phone=...` - Modificar el teléfono de un cliente.
- `GET /api/v1/clients/by-product?code=CHEQ` - Buscar clientes asociados a un producto específico (e.g. `CHEQ`, `PZOF`, `TJCREDITO`, etc.).
