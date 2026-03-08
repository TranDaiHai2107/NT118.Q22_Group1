@echo off
@setlocal

set "MAVEN_PROJECTBASEDIR=%~dp0"
set "WRAPPER_JAR=%~dp0.mvn\wrapper\maven-wrapper.jar"
set "WRAPPER_PROPERTIES=%~dp0.mvn\wrapper\maven-wrapper.properties"

if NOT "%JAVA_HOME%"=="" (
    set "JAVA_EXE=%JAVA_HOME%\bin\java.exe"
) else (
    set "JAVA_EXE=java.exe"
)

"%JAVA_EXE%" ^
  -Dmaven.multiModuleProjectDirectory="%MAVEN_PROJECTBASEDIR%" ^
  -classpath "%WRAPPER_JAR%" ^
  org.apache.maven.wrapper.MavenWrapperMain %*

@endlocal
