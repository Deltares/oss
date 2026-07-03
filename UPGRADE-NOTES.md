<h1>Upgrade script for upgrade Keycloak from 24.0.2 to 26.6.3</h1>

<h2>Step 1 - Podman preparation</h2>

Make sure to clean all the containers & volumes from the Podman instance

<h2>Step 2 - Pre upgrade data preparation</h2>

Before we can upgrade the database we need to clean up data and make some preparations.

Follow these steps:
<ol type="1">
<li>Obtain a database dump of the production database from Firelay.</li>
<li>Copy and rename the dump file over file /docker/resources/dump-keycloak.sql (do not commit to github)</li>
<li>In the file /docker/docker-compose.yml, comment out all services except for 'database'. This will assure that Keycloak is not loaded.</li>
<li>Start Podman by running the following command in the 'Terminal' panel from the 'docker' folder!<br>
<code>podman compose -f docker-compose.yml up -d</code>
</li>
<li>Check the container logs in Podman to see if MariaDB started correctly and the DB import finished without errors.<br> 
<li>Connect to the database using a DB viewer such as DBeaver.<br> 
<code>jdbc:mariadb://localhost:3308/keycloak</code><br>
<code> user: keycloak, password: password</code>
</li>
<li>Stop Podman by running the following command in the 'Terminal' panel from the 'docker' folder!<br>
<code>podman compose -f docker-compose.yml down</code>
</li>
</ol>

<h2>Step 3 - Run the Keycloak Upgrade</h2>

The Keycloak upgrade needs to run incrementally. First we start Keycloak with the currently active version: <strong>24.0.2</strong> 

<ol type="1">
<li>Update 'docker-compose.yml' to activate the Keycloak configuration and set the image version to 24.0.2</li>
<li>Start the configuration using the startup command: <br/>
<code>podman compose -f docker-compose.yml up -d</code>
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

<h3>What to do when one of the Keycloak increments fails?</h2>
<ol type="1">
<li>
Stop the containers as described above.
</li>
<li>
Delete the 'volume' named 'docker_dbkeycloak74' in Podman.
</li>
<li>
Update the 'docker-compose.yml' file by commenting out the Keycloak section.
</li>
<li>
Copy the last DB dump file to folder and rename to <code>./docker/resouces/dump-keycloak.sql</code>
</li>
<li>
Restart the docker configuration again as described above. And check that DB is successfully rebuilt.
</li>
<li>Go back to the begining of Step 3, but decrease the step size for Keycloak and/or resolve any problems.</li>
</ol>

<h3>Known DB upgrade errors</h3>
<ol type="1">
<li>Upgrading to Keycloak > 26.1.* resulted in a DB upgrade error related to creating table KEYCLOAK_GROUP<br/>
This is a known issue and resolution can be fownd in following link: <a href="https://github.com/keycloak/keycloak/issues/48782">Foreign key constraint is incorrectly formed/a> <br/>
<p>
Although the above issue does not seem to be the cause of our problem, it did tigger me to find the following issue:<br/>
Table ORG and some others had character set <code>utf8mb4</code> active while all other tables had <code>utf8mb3</code>
</p>
<p>
Replace all <code>utf8mb4</code> by <code>utf8mb3</code> in the <strong>dump-keycloak.sql</strong> before restoring the DB resolves this problem.
</p>

</li>

</ol>

<h2>Step 4 - Deploy the mydeltares-keycloak JARS</h2>
Once you have verified that Keycloak is running under 26.6.3, it is time to test the Deltares JAR files:
<p>
Verify this by copying JAR files;

- mydeltares-keycloak-spi-5.0.0.jar &
- mydeltares-keycloak-theme-5.0.0.jar

into the folder <code>keycloak/deployments</code>. Now restart Keycloak and check the logs for errors.
</p>

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