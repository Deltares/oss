# Environment

The configuration for the environment is:
```
Liferay DXP 7.1.10-sp5
MariaDB 10.2.25
ElasticSearch 6.8.12
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

Check the `example-gradle-local.properties` file to see the available properties.
```
env=local
dbName=liferay
dbUser=liferay
dbPassword=liferay
# provide a default password if this is a fresh new instance
default.admin.password=Deltares123!
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
Add the below entries to your hosts file.
```
127.0.0.1	keycloak
127.0.0.1	dsd.local.nl
127.0.0.1	oss.local.nl
127.0.0.1	oss-child.local.nl
```

### Database and document library backup restore
Both the Keycloak container and the Liferay container are initialized using a
restore of a previous database dump. These dump files can be found and overwritten 
in the following location:

 `dump-liferay.sql` in `docker/resources/` 
 `dump-keycloak.sql` in `docker/resources/`

Place document library backup in `docker/liferay/data`

!! Please do not update the database dump files to GITHUB !!

### Deployment
Refer to [deployment](deployment.md)