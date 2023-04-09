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

#### Setup SSL ([example config](https://www.arubacloud.com/tutorial/how-to-enable-https-protocol-with-apache-2-on-ubuntu-20-04.aspx))
* Generate certificate
    * run `sudo openssl req -new -newkey rsa:2048 -nodes -out <filename.csr> -keyout <private.key> -subj "/C=ua/ST=<state>/L=<city>/O=<org name>/CN=<domain>/emailAddress=<email>"` in target folder
    * copy .csr content, paste into SSL provider and generate .crt file
    * upload .crt file to VM
* Create params file
```shell
sudo vim /etc/apache2/conf-available/ssl-params.conf
```

With content:
```properties
SSLCipherSuite EECDH+AESGCM:EDH+AESGCM:AES256+EECDH:AES256+EDH
    
SSLProtocol All -SSLv2 -SSLv3 -TLSv1 -TLSv1.1

SSLHonorCipherOrder On


Header always set X-Frame-Options DENY

Header always set X-Content-Type-Options nosniff

# Requires Apache >= 2.4

SSLCompression off

SSLUseStapling on

SSLStaplingCache "shmcb:logs/stapling-cache(150000)"


# Requires Apache >= 2.4.11

SSLSessionTickets Off
```

* Update virtual host
```shell
sudo vim /etc/apache2/sites-available/000-default.conf
```

Insert at the end of file:
```properties
<IfModule mod_ssl.c>
        <VirtualHost _default_:443>
                ServerAdmin <your email>
                DocumentRoot /var/www/html
                ErrorLog ${APACHE_LOG_DIR}/error.log
                CustomLog ${APACHE_LOG_DIR}/access.log combined 

                SSLEngine on
                SSLCertificateFile      <path to crt/pem file>
                SSLCertificateKeyFile   <path to private key>

                <FilesMatch "\.(cgi|shtml|phtml|php)$">
                        SSLOptions +StdEnvVars
                </FilesMatch>
                <Directory /usr/lib/cgi-bin>
                        SSLOptions +StdEnvVars
                </Directory>
        </VirtualHost>
</IfModule>
```

Add redirect to https (at the end of *80 virtual host):
```properties
<VirtualHost *:80>
...
    Redirect permanent / https://<your host>/
...
</VirtualHost>
```

* Enable modules and restart Apache server
```shell
sudo a2enmod ssl
sudo a2enmod headers
sudo a2enconf ssl-params
sudo systemctl restart apache2
```