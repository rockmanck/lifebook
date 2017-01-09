#!/bin/bash

bash ./gradlew clean
bash ./gradlew war

bash /data/lifebook/tomcat/bin/shutdown.sh

read -p "Press anykey..."

rm -rf /data/lifebook/tomcat/logs/*
rm -rf /data/lifebook/prev/*
cp -rf /data/lifebook/tomcat/webapps/ROOT/* /data/lifebook/prev
rm -rf /data/lifebook/tomcat/webapps/ROOT/*

unzip ./build/libs/lifebook.war -d /data/lifebook/tomcat/webapps/ROOT
chmod 777 /data/lifebook/tomcat/webapps/ROOT/

read -p "Press anykey..."

bash /data/lifebook/tomcat/bin/startup.sh
