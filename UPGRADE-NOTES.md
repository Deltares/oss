<h1>Upgrade script for upgrade Liferay to Commerce environment</h1>

<h2>Step 1 - Docker preparation</h2>

Make sure to clean all the containers & volumes from the Docker desktop instance

<h2>Step 2 - Pre upgrade data preparation</h2>

Before we can upgrade the database we need to clean up data and make some preparations.

Follow these steps:
<ol type="1">
<li>Obtain a database dump of the production database from Firelay.</li>
<li>Copy and rename the dump file over file /docker/resources/dump-liferay.sql (do not commit to github)</li>
<li>In the file /docker/docker-compose.yml, comment out all services except for 'database'. This will assure that Liferay is not loaded.</li>
<li>Start Docker by running the following command in the 'Terminal' panel from the project root folder!<br>
<code>./gradlew startLiferay</code></li>
<li>Connect to the database using a DB viewer such as DBeaver.<br> 
<code>jdbc:mariadb://localhost:3307/liferay</code><br>
<code> user: liferay, password: liferay</code>
</li>
<li>Check that everything works fine with the original settings.</li>
</ol>

<h2>Step 3 - Upgrade the Liferay workspace and database</h2>

Upgrade the project gradle configurations and the Liferay Docker configurations to match the values for the 
Commerce upgrade (version ~ dxp.2024.q3.2)
<code>./gradlew startLiferay</code>
<p>
This command will start the 'oss-liferay-74' container which will initiate the database upgrade. Open the container logs
and make sure no errors occur.

<h2>Step 4 - Update settings in Liferay</h2>

First begin by logging into Liferay:

http://localhost:8081

<code>screen name=liferay, password='value entered in Step 2 - sub step 7</code>

<h3>TODO</h3>
TODO

<h3>FreeMarker settings</h3>
