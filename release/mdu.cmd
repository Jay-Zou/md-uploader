@echo off
@REM ==== START VALIDATION ====
if not "%JAVA_HOME%"=="" goto OkJHome
for %%i in (java.exe) do set "JAVACMD=%%~$PATH:i"
goto checkJCmd

:OkJHome
set "JAVACMD=%JAVA_HOME%\bin\java.exe"

:checkJCmd
if exist "%JAVACMD%" goto init

echo The JAVA_HOME environment variable is not defined correctly >&2
echo This environment variable is needed to run this program >&2
echo NB: JAVA_HOME should point to a JDK not a JRE >&2
goto error
@REM ==== END VALIDATION ====

:init

set "CURRENT_DIR=%~dp0"

set CMD_LINE_ARGS=%*
set JAR_FILE=%CURRENT_DIR%md-uploader-0.0.1-SNAPSHOT.jar

"%JAVACMD%" ^
  -jar %JAR_FILE% ^
  %CMD_LINE_ARGS%