### Start Liferay
To start Liferay run the following command in your project root folder.
```
./gradlew startLiferay
```
This command will create the container for Liferay, MariaDB and ElasticSearch.
```
docker ps -a
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS                    PORTS                                                                 NAMES
e50942ae8581        oss_liferay         "/bin/sh -c '/etc/li…"   26 minutes ago      Up 26 minutes (healthy)   0.0.0.0:8000->8000/tcp, 8009/tcp, 0.0.0.0:8080->8080/tcp, 11311/tcp   oss-liferay
f0a8faf5b4de        mariadb:10.2.25     "docker-entrypoint.s…"   26 minutes ago      Up 26 minutes             0.0.0.0:3306->3306/tcp                                                oss-mariadb
c90070c167fe        oss_elasticsearch   "/usr/local/bin/dock…"   26 minutes ago      Up 26 minutes             0.0.0.0:9200->9200/tcp, 9300/tcp                                      oss-elastic

docker volume ls
DRIVER              VOLUME NAME
local               oss_dbdata
local               oss_esdata
```

### Checking the logs
The logs are available though docker.
```
docker logs -f oss-liferay
docker logs -f oss-mariadb
docker logs -f oss-elastic
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
If you need to restore your environment from a backup first clean the docker volumes.
```
docker volume rm oss_dbdata
docker volume rm oss_esdata
```

### Dump MariaDB database
If you need to create a dump of the database run the following command in your project root folder.
```
./gradlew dumpDB
```
This will create (override if exist) the file `docker/mariadb/docker-entrypoint-initdb.d/liferay.sql`. 