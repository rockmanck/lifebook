set JAVA_HOME=%GRAALVM_HOME%
call "C:\Program Files (x86)\Microsoft Visual Studio\2017\BuildTools\VC\Auxiliary\Build\vcvars64.bat"
cd ..
gradlew build "-Dquarkus.package.type=native"