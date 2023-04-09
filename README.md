# lifebook
[![CI](https://github.com/rockmanck/lifebook/actions/workflows/main.yml/badge.svg)](https://github.com/rockmanck/lifebook/actions/workflows/main.yml)
![Coverage](.github/badges/jacoco.svg)

### Prerequisites
PostgreSQL and Apache2 servers are installed on VM.

### How to deploy
* SSH to server
* Pull new version to `/home/<user>/lifebook` via `git pull`
* Build `./gradlew build`
* `sudo systemctl stop lifebook`
* Copy .war file to `/home/<user>/app`
* `sudo systemctl start lifebook`
* check status by `journalctl -f`. If server fails to start - reset VM.

### How to setup new VM (Ubuntu 20.04)
#### Setup and configure Apache2 server
```shell
sudo apt update
sudo apt install apache2
sudo vim 000-default.conf
```
* Update ServerAdmin property to your email
* Add proxy config:
```text
ProxyPreserveHost On
ProxyPass / http://localhost:8081/
ProxyPassReverse / http://localhost:8081/
```

* Enable proxy:
```shell
sudo a2enmod proxy
sudo a2enmod proxy_http
sudo systemctl restart apache2
```

#### Install Java 17
```shell
apt install openjdk-17-jdk
```

#### Create lifebook system service
```shell
sudo vim /etc/systemd/system/lifebook.service
```

With content:
```properties
[Unit]
Description=Lifebook Webapp Service
[Service]
User={{user}}
# The configuration file application.properties should be here:
#change this to your workspace
WorkingDirectory=/home/{{user}}/app
#path to executable. 
#executable is a bash script which calls jar file
ExecStart=/home/{{user}}/app/lifebook
SuccessExitStatus=143
TimeoutStopSec=100
Restart=always
#Restart=on-failure
RestartSec=10
[Install]
WantedBy=multi-user.target
```

Make it automatically start on system boot:
```shell
sudo systemctl enable lifebook
```

#### Create application folder and start script
```shell
cd ~
mkdir app
cd app
vim lifebook
chmod 777 lifebook
```

Script:
```shell
#!/bin/sh

java -Dspring.datasource.username={{db_user}} -Dspring.datasource.password={{db_password}} -Dspring.datasource.url="jdbc:postgresql://{{db_ip}}:5432/lifebook" -Dserver.port=8081 -jar application.war -Xmx400M
```

Make sure application.war located in ~/app folder.

#### Setup SSL
TODO