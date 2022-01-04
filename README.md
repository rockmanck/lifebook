# lifebook

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

### Project structure
* Domain - core module that contains high-level policies
* Application - module contains integrations and app wire up. 
Simplified version of hexagonal architecture - high-level packages could be converted to separate modules.
