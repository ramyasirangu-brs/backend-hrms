#!/bin/bash

# Test script for JWT Authentication
BASE_URL="http://localhost:8181"

echo "=== HRMS JWT Authentication Test ==="
echo

# Test 1: Check if server is running
echo "1. Testing server connectivity..."
curl -s "$BASE_URL/api/auth/test" || { echo "Server not running. Please start the application first."; exit 1; }
echo "✓ Server is running"
echo

# Test 2: Register Admin
echo "2. Registering admin..."
ADMIN_RESPONSE=$(curl -s -X POST "$BASE_URL/api/auth/register/admin" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123",
    "email": "admin@company.com",
    "fullName": "System Administrator"
  }')

echo "Admin registration response: $ADMIN_RESPONSE"
ADMIN_TOKEN=$(echo $ADMIN_RESPONSE | grep -o '"token":"[^"]*"' | cut -d'"' -f4)
echo "Admin token: $ADMIN_TOKEN"
echo

# Test 3: Login as Admin
echo "3. Logging in as admin..."
LOGIN_RESPONSE=$(curl -s -X POST "$BASE_URL/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }')

echo "Login response: $LOGIN_RESPONSE"
LOGIN_TOKEN=$(echo $LOGIN_RESPONSE | grep -o '"token":"[^"]*"' | cut -d'"' -f4)
echo "Login token: $LOGIN_TOKEN"
echo

# Test 4: Create Employee
echo "4. Creating employee..."
EMPLOYEE_RESPONSE=$(curl -s -X POST "$BASE_URL/api/admin/employees" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -d '{
    "username": "employee1",
    "password": "emp123"
  }')

echo "Employee creation response: $EMPLOYEE_RESPONSE"
echo

# Test 5: Login as Employee
echo "5. Logging in as employee..."
EMP_LOGIN_RESPONSE=$(curl -s -X POST "$BASE_URL/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "employee1",
    "password": "emp123"
  }')

echo "Employee login response: $EMP_LOGIN_RESPONSE"
EMP_TOKEN=$(echo $EMP_LOGIN_RESPONSE | grep -o '"token":"[^"]*"' | cut -d'"' -f4)
echo "Employee token: $EMP_TOKEN"
echo

# Test 6: Test Admin Endpoint
echo "6. Testing admin endpoint..."
ADMIN_TEST=$(curl -s -X GET "$BASE_URL/api/admin/test" \
  -H "Authorization: Bearer $ADMIN_TOKEN")
echo "Admin test response: $ADMIN_TEST"
echo

# Test 7: Test Employee Endpoint
echo "7. Testing employee endpoint..."
EMP_TEST=$(curl -s -X GET "$BASE_URL/api/employee/test" \
  -H "Authorization: Bearer $EMP_TOKEN")
echo "Employee test response: $EMP_TEST"
echo

# Test 8: Test Unauthorized Access
echo "8. Testing unauthorized access..."
UNAUTHORIZED=$(curl -s -X GET "$BASE_URL/api/admin/test" \
  -H "Authorization: Bearer $EMP_TOKEN")
echo "Unauthorized access response: $UNAUTHORIZED"
echo

echo "=== Test Complete ==="
echo "If all tests passed, your JWT authentication system is working correctly!" 