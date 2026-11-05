@echo off
echo ===============================================
echo    💳 FinanceFlow Pro Banking System
echo ===============================================
echo.

cd /d "%~dp0"

echo Checking Java installation...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 11 or higher
    pause
    exit /b 1
)

echo Building the project and downloading dependencies...
call mvn clean compile dependency:copy-dependencies -DoutputDirectory=target/dependency -q
if %errorlevel% neq 0 (
    echo ERROR: Failed to build the project or download dependencies
    pause
    exit /b 1
)

echo Starting SecureBank Banking System...
echo.
echo The application window should open shortly...
echo Press Ctrl+C to stop the application
echo.

REM Try running with JavaFX modules first
echo Attempting to run with JavaFX modules...
java --module-path "target/dependency" --add-modules javafx.controls,javafx.fxml -cp "target/classes;target/dependency/*" com.bank.BankingApplication

REM If that fails, try without modules
if %errorlevel% neq 0 (
    echo.
    echo JavaFX modules failed, trying alternative method...
    java -cp "target/classes;target/dependency/*" com.bank.BankingApplication
)

pause 