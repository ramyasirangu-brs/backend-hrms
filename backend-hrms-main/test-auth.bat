@echo off
echo === HRMS JWT Authentication Test ===
echo.

REM Test 1: Check if server is running
echo 1. Testing server connectivity...
curl -s "http://localhost:8181/api/auth/test" >nul 2>&1
if %errorlevel% neq 0 (
    echo Server not running. Please start the application first.
    pause
    exit /b 1
)
echo ✓ Server is running
echo.

REM Test 2: Register Admin
echo 2. Registering admin...
curl -X POST "http://localhost:8181/api/auth/register/admin" ^
  -H "Content-Type: application/json" ^
  -d "{\"username\": \"admin\", \"password\": \"admin123\", \"email\": \"admin@company.com\", \"fullName\": \"System Administrator\"}"
echo.
echo.

REM Test 3: Login as Admin
echo 3. Logging in as admin...
curl -X POST "http://localhost:8181/api/auth/login" ^
  -H "Content-Type: application/json" ^
  -d "{\"username\": \"admin\", \"password\": \"admin123\"}"
echo.
echo.

REM Test 4: Create Employee (you'll need to copy the token from step 3)
echo 4. Creating employee...
echo Please copy the token from the login response above and use it in the next command:
echo curl -X POST "http://localhost:8181/api/admin/employees" ^
echo   -H "Content-Type: application/json" ^
echo   -H "Authorization: Bearer YOUR_TOKEN_HERE" ^
echo   -d "{\"username\": \"employee1\", \"password\": \"emp123\"}"
echo.
echo.

REM Test 5: Login as Employee
echo 5. Logging in as employee...
curl -X POST "http://localhost:8181/api/auth/login" ^
  -H "Content-Type: application/json" ^
  -d "{\"username\": \"employee1\", \"password\": \"emp123\"}"
echo.
echo.

echo === Test Complete ===
echo If all tests passed, your JWT authentication system is working correctly!
pause 