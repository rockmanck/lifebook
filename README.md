# lifebook

PostgreSQL and Apache2 servers are installed on VM.

### How to deploy
* SSH to server
* Pull new version to `/home/rockman_roll/lifebook` via `git pull`
* Build `./gradlew build`
* `sudo systemctl stop lifebook`
* Copy .war file to `/home/rockman_roll/app`
* `sudo systemctl start lifebook`
* check status by `journalctl -f`. If server fails to start - reset VM.
