set p=C:\Users\Administrator\Desktop\gzly\html\site\html\f\img
echo 您将对指定目录进行完全清除处理，处理后所有文件都只剩下空白文件
pause
echo 此操作将清除目录“%p%”下所有文件
pause
echo 真的想清楚了，没删除错误喔，确认将删除
pause
for /f "delims=" %%i   in ('dir  /b/a-d/s  %p%\*.*')  do (
	echo %%i
	copy /y clear.txt "%%i"
)
pause