set tomcat=c:\lifebook-tomcat

call gradlew clean war
PAUSE

::stop tomcat
net stop "Apache Tomcat #1 - lifebook.ua"
PAUSE

echo clear 'previous' dir
RMDIR %tomcat%\previous /s /q
mkdir %tomcat%\previous
PAUSE

echo move working copy to 'previous' dir
MOVE %tomcat%\lifebook %tomcat%\previous
PAUSE

echo extract new copy
mkdir %tomcat%\lifebook
"c:\Program Files\7-Zip\7z.exe" x build\libs\*.war -o%tomcat%\lifebook
PAUSE

echo clean tmp tomcat folders
RMDIR %tomcat%\work /s /q
RMDIR %tomcat%\temp /s /q
RMDIR %tomcat%\logs /s /q
PAUSE

:: start tomcat
net start "Apache Tomcat #1 - lifebook.ua"
PAUSE
