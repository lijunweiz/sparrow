@echo off

@if not "%ECHO%" == ""  echo %ECHO%
@if "%OS%" == "Windows_NT" setlocal

if "%OS%" == "Windows_NT" (
  set "DIRNAME=%~dp0%"
) else (
  set DIRNAME=.\
)

set "JAVA_HOME=%DIRNAME%\jre"
set "JAVA=%JAVA_HOME%\bin\java"
set "JAVA_OPTS=--illegal-access=deny --add-opens java.base/java.lang=ALL-UNNAMED -Xms128m -Xmx256m"
set "RUN_JAR=%DIRNAME%\sparrow.jar"
start /b %JAVA% %JAVA_OPTS% -jar %RUN_JAR%