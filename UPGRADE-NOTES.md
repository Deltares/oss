<h1>Upgrade script for upgrade Liferay DXP from 7.4 to 2026.qxxxx</h1>

<h2>Step 1 - Podman preparation</h2>

Make sure to clean all the containers & volumes from the Podman instance

<h2>Step 2 - Pre upgrade data preparation</h2>

Before we can upgrade the database we need to clean up data and make some preparations.

Follow these steps:
<ol type="1">
<li>Obtain a database dump of the production database from Firelay.</li>
<li>Copy and rename the dump file over file /docker/resources/dump-liferay.sql (do not commit to github)</li>
<li>In the file /docker/docker-compose.yml, comment out all services except for 'database'. This will assure that Liferay is not loaded.</li>
<li>Start Podman by running the following command in the 'Terminal' panel from the 'docker' folder!<br>
<code>podman compose -f docker-compose.yml up -d</code>
</li>
<li>Check the container logs in Podman to see if MariaDB started correctly and the DB import finished without errors.<br> 
<li>Connect to the database using a DB viewer such as DBeaver.<br> 
<code>jdbc:mariadb://localhost:3307/liferay</code><br>
<code> user: keycloak, password: password</code>
</li>
<li>Stop Podman by running the following command in the 'Terminal' panel from the 'docker' folder!<br>
<code>podman compose -f docker-compose.yml down</code>
</li>
</ol>

<h3>Prepare DB dump file before loading</h3>

Here is a list of issues that need to be fixed in the Production Liferay DB Dump before the database dump can be loaded.

<ol>
<li>
Replace all values of <strong>utf8mb3</strong> with the value <strong>utf8mb4</strong>.<br />
Replace all values of <strong>utf8mb3_general_ci</strong> with the value <strong>utf8mb4_unicode_ci</strong>.<br />
</li>
<li>
Error 'Unable to get user personal site group...': <br />
Remove all User Personal Site records from TABLE Group_. These are all records with friendlyURL = '/personal_site' 
</li>
<li>Switch OFF SAML login for all Virtual Hosts. Search in TABLE Configuration_ for SAML records.<code>configurationId like '%SAML%'</code></li>
<li>Set password for users Liferay and rooij_e in TABLE User_.</li>
<li>Remove all Forum content for specific site: <br />
Lookup GroupId of the specific site. In all TABLES starting with 'MB...', lookup and delete all records with groupId == "site group id".
</li>
</ol>

<h3>Cleanup unused content</h3>

Before starting the upgrade proces, it is a good time to clean up all outdated content in the Liferay instance.

Follow these steps:
<ol type="1">
<li>In docker-compose.yml enable the Liferay configurations again.</li>
<li>Start Liferay podman container</li>
<li>Deploy all modules to the container</li>
<li>Login to Liferay and clean all unused; virtual instances, sites, message boards, content</li>
<li>Once you are satisfied with the results, create a DB Dump from the Liferay database</li>
<li>Shutdown containers</li>
</ol>


<h2>Step 3 - Run the Liferay Upgrade</h2>

The Liferay upgrade needs to run incrementally. If your are feeling lucky you can start by trying to upgrade
strait to the target version of Liferay

> When upgrading to Liferay 2026 you will need to upgrade the MySql connector JAR. 
>
> This can be downloaded from <a href="https://dev.mysql.com/downloads/file/?id=552109">MySql Connector JAR</a> and Store this JAR
> in directory /configs/common/tomcat/.../mysql.jar
> 
> Also in the portal-ext.properties file update the DB connection variable: 
> 
> jdbc.default.url = jdbc:mysql://database/@MYSQL_DATABASE@?characterEncoding=UTF-8&dontTrackOpenResources=true&holdResultsOpenOverStatementClose=true&serverTimezone=GMT&useFastDateParsing=false&useUnicode=true&permitMysqlScheme&sslMode=trust
> 
> Additionally update the MariaDB version to 11.8.8
> 
> In configuration file docker-compose.yml update the MariaDB image to mariadb:11.8.8

> Upgrade the elasticsearch engine to version 8.19.18
> 
> In configuration file /docker/elasticsearch/Dockerfile, update the image version to 8.19.18

<ol type="1">
<li>Update 'liferay/Dockerfile' to activate the Liferay configuration and set the image version to 2026.q?.? </li>
<li>Start the configuration using the startup command: <br/>
<code>./gradlew startLiferay</code>
</li>
<li>Check the container logs for Keycloak to see if the container started without errors and if the DB upgrade ran and completed without error. <br/>
Make sure the the DB upgrade is completed.</li>
<li>After each successfull DB upgrade, make a dump of the Keycloak database!</li>
<li>Stop the container with the following command: <br/>
<code>podman compose -f docker-compose.yml down</code>
</li>
<li>Now update 'docker-compose.yml' again by increasing the Keycloak image version to 25.0.6</li>
<li>Repeate the above steps until you reach the target Keycloak image version 26.6.3. <br/> 
Suggested increments for updating Keycloak; 24.0.2, 25.0.5 & 26.0.8, 26.1.5, 26.2.5, 26.3.5, 26.4.7, 26.5.7 and 26.6.3.</li>
</ol>

<h3>Errors/Warns that occur during the Liferay DB upgrade process</h2>
<ol type="1">
<li>
WARN utf8mb3 character set and utf8mb3_general_ci collation, but database has utf8mb4 character set and utf8mb4_general_ci collation. Recommended character set is utf8mb4 and recommended collation is utf8mb4_unicode_ci
<br />
Dump the upgraded DB from Liferay and convert the character set values. Then restore the dump.
</li>
</ol>


<h2>Step 5 - Testing</h2>

There are many things that can be tested but the most important tests are described below. In all cases it is necessary
to setup SAML configuration in Liferay.

<ul>
<li>Login with the liferay admin account.</li>
<li>Register a new 'none-Deltares' account.</li>
<li>Reset your password.</li>
<li>Update all profile information from the Liferay <strong>MyProfile</strong> pages.</li>
<li>Test 2FA by e-mail from Keycloak</li>
</ul>