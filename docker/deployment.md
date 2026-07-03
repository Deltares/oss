### Prerequisites – Podman Setup (Windows)
Before running the stack, ensure Podman is installed and running:

1. Install [Podman Desktop](https://podman-desktop.io/) or the Podman CLI via `winget install RedHat.Podman`
2. Ensure Podman v4+ is installed — `podman compose` is built in (no extra install needed)
3. Start the Podman machine (if not using Podman Desktop):
   ```
   podman machine init
   podman machine start
   ```
4. Verify Podman is working:
   ```
   podman info
   ```
   
4. B: Problems building the Liferay image due to invalid credentials.
   Resolved this by adding the following command to the build.gradle:
   ```
   podman build -t liferay -f .\liferay\Dockerfile .
   ```
   After this I could run './gradlew startLiferay successfully'
   
5. When running `./gradlew startLiferay` you will be prompted for your **Liferay Customer Portal**
   credentials ([help.liferay.com](https://help.liferay.com)). These are used once to authenticate
   Podman against Docker Hub so it can pull the private `liferay/dxp` image. Podman caches the
   token locally so you won't be asked again until the token expires.

### Start Liferay
To start Liferay run the following command in your project root folder.
```
./gradlew startLiferay
```
This command will create the container for Liferay, MariaDB and ElasticSearch.
```
podman ps -a

CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS                    PORTS                                                                 NAMES
doe50942ae8581      oss_liferay         "/bin/sh -c '/etc/li…"   26 minutes ago      Up 26 minutes (healthy)   0.0.0.0:8000->8000/tcp, 8009/tcp, 0.0.0.0:8080->8080/tcp, 11311/tcp   oss-liferay-74
f0a8faf5b4de        mariadb:10.6.27     "docker-entrypoint.s…"   26 minutes ago      Up 26 minutes             0.0.0.0:3307->3306/tcp                                                oss-mariadb-74
6f1f7f16d9e2        mariadb:10.6.27     "docker-entrypoint.s…"   19 minutes ago      Up 19 minutes             0.0.0.0:3308->3306/tcp                                                keycloak-mariadb-74
c90070c167fe        oss_elasticsearch   "/usr/local/bin/dock…"   26 minutes ago      Up 26 minutes             0.0.0.0:9200->9200/tcp, 9300/tcp                                      oss-elastic-74

podman volume ls
DRIVER              VOLUME NAME
local               oss_dbkeycloak74
local               oss_dbliferay74
local               oss_esdata74
```

### Checking the logs
The logs are available through Podman.
```
podman logs -f oss-liferay-74
podman logs -f oss-mariadb-74
podman logs -f oss-elastic-74
```

### Stop Liferay
To stop Liferay run the following command in your project root folder.
```
./gradlew stopLiferay
```

### Deploy modules
To deploy the Theme and Liferay modules run the following command in your project root folder.
```
./gradlew clean build deploy 
```

### Cleaning database and document library
If you need to restore your environment from a backup first clean the Podman volumes.
```
podman volume rm oss_dbliferay74
podman volume rm oss_esdata74
```

### Dump MariaDB database
If you need to create a dump of the database run the following command in your project root folder.
```
./gradlew dumpDB
```
This will create (override if exist) the file `docker/resources/liferay.sql`.

### Reindexing the Liferay search indices
When initializing the Liferay container it may be necessary to reindex the es search index. This will
be necessary to view Web Content in the `http://liferay:8081` site.

Login as user `test@liferay.com` and open `Control Panel -> Configuration -> Search`. 
Run `Reindex all search indexes`