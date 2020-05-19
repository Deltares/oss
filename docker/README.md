# Environment

The configuration for the environment is:
```
Liferay DXP 7.1.10 GA1
MariaDB 10.2.25
ElasticSearch 6.5.0
```

## Configurations
The path for Liferay's configurations is: 
```
configs/docker
```

## How to set up your local development environment

### Prerequisites
* Cloned repository
* 8GB RAM
* Docker
* Docker-compose
* Java 8
* Gradle

### Gradle
Create `gradle-local.properties` file in project root folder. These properties will be replaced in the
`*.properties` file when executing the deployment tasks.
```
env=local
dbName=liferay
dbUser=liferay
dbPassword=liferay
# provide a default password if this is a fresh new instance
default.admin.password=SECURE_PASSWORD
```
Note: if this file does not exist the build tasks will use the default values
(refer to `build.gradle` for details).
```
dbName = getEnvProperty('dbName', 'liferay')
dbUser = getEnvProperty('dbUser', 'liferay')
dbPassword = getEnvProperty('dbPassword', 'liferay')
```

### Docker

Create `.env` file in `[project_root]/docker`. These properties will be used to create the MariaDB container.
```
MYSQL_ROOT_PASSWORD=liferay
MYSQL_DATABASE=liferay
MYSQL_USER=liferay
MYSQL_PASSWORD=liferay
```
Note: If the values provided in this file are not the same as those in `gradle-local.properties` Liferay will not be
able to connect to the database.

### Host
Add `oss.portal` to your hosts file.
```
127.0.0.1    oss.portal
```

### Database and document library backup restore
Place database backup file with name `liferay.sql` in `docker/mariadb/docker-entrypoint-initdb.d/`. \
Place document library backup in `docker/liferay/data`

Note: Database and document library backups are not versioned in git due to their weight. The suggested way to get a
starting backup is from the test environment. 

### Deployment
Refer to [deployment](deployment.md)