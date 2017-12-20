@echo off
if exist %1\ (
	setlocal ENABLEDELAYEDEXPANSION
	for /r %~dp0/common %%v in (*.jar) do (
		xcopy "%%v" "%1" /y
	)
	for /r %~dp0/mybatis %%v in (*.jar) do (
		xcopy "%%v" "%1" /y
	)
	endlocal
)
pause