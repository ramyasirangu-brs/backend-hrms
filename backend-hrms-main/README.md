# HRMS JWT Authentication System

This Spring Boot application implements JWT authentication for an HRMS system with the following features:

## Features

- **JWT Authentication**: Secure token-based authentication
- **Role-based Access**: Admin and Employee roles
- **Single Admin Policy**: Only one admin can exist in the system
- **Employee Management**: Admin can create and manage employees
- **Secure Endpoints**: Role-based endpoint protection

## API Endpoints

### Public Endpoints (No Authentication Required)
- `POST /api/auth/register/admin` - Register the first admin
- `POST /api/auth/login` - Login for both admin and employees
- `GET /api/auth/test` - Test authentication API
- `GET /api/test/public` - Public test endpoint

### Admin Endpoints (Require ADMIN Role)
- `POST /api/admin/employees` - Create new employee
- `GET /api/admin/employees` - Get all employees
- `DELETE /api/admin/employees/{id}` - Delete employee
- `GET /api/admin/test` - Admin test endpoint

### Employee Endpoints (Require EMPLOYEE Role)
- `GET /api/employee/profile` - Employee profile
- `GET /api/employee/dashboard` - Employee dashboard
- `GET /api/employee/test` - Employee test endpoint

### Protected Endpoints (Require Authentication)
- `GET /api/test/authenticated` - Test authenticated endpoint

## Testing the Authentication

### 1. Register Admin (First Time Only)
```bash
curl -X POST http://localhost:8181/api/auth/register/admin \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123",
    "email": "admin@company.com",
    "fullName": "System Administrator"
  }'
```

### 2. Login as Admin
```bash
curl -X POST http://localhost:8181/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

### 3. Create Employee (Using Admin Token)
```bash
curl -X POST http://localhost:8181/api/admin/employees \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_ADMIN_TOKEN" \
  -d '{
    "username": "employee1",
    "password": "emp123"
  }'
```

### 4. Login as Employee
```bash
curl -X POST http://localhost:8181/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "employee1",
    "password": "emp123"
  }'
```

### 5. Test Protected Endpoints
```bash
# Test admin endpoint
curl -X GET http://localhost:8181/api/admin/test \
  -H "Authorization: Bearer YOUR_ADMIN_TOKEN"

# Test employee endpoint
curl -X GET http://localhost:8181/api/employee/test \
  -H "Authorization: Bearer YOUR_EMPLOYEE_TOKEN"
```

## Security Features

- **Password Encryption**: All passwords are encrypted using BCrypt
- **JWT Tokens**: Stateless authentication with configurable expiration
- **Role-based Authorization**: Different endpoints for different roles
- **Single Admin Policy**: Prevents multiple admin accounts
- **CORS Support**: Cross-origin requests enabled

## Configuration

The JWT configuration is in `application.properties`:
- `jwt.secret`: Secret key for JWT signing
- `jwt.expiration`: Token expiration time in milliseconds (24 hours by default)

## Database

The application uses MySQL database. Make sure to:
1. Create a database named `hrms`
2. Update database credentials in `application.properties` if needed
3. The application will automatically create the required tables

## Running the Application

1. Ensure MySQL is running
2. Update database credentials in `application.properties`
3. Run the application: `mvn spring-boot:run`
4. The application will start on port 8181 