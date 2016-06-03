
set p=%~dp0
pushd %p%
#set p=%cd%

set BAT_HOME=%p%


if "%JAVA_HOME%" == "" goto noJavaHome

:JavaHome
set PATH=%JAVA_HOME%\bin;%PATH%
set classpath=.;%JAVA_HOME%\lib
goto okJava

:noJavaHome
if "%JRE_HOME%" == "" goto noJreHome
set PATH=%JRE_HOME%\bin;%PATH%
set classpath=.;%JRE_HOME%\lib
goto okJava

:noJreHome
set JAVA_HOME=C:\Java\jdk1.7.0_79
goto JavaHome

:okJava
