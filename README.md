<center><img src="https://s3.eu-central-1.amazonaws.com/github-lucacalabrese-assets/codemotion-spark-bot/title.jpg"/></center>

Spark Bot for CODEMOTION Rome 2017
====================================

**QuizBot** is a chatbot, built for Cisco Spark, that implements a "quiz" game. The bot asks the user a set of questions drawn randomly from a database. The user must select the correct answer among 4 possible answers (marked with A, B, C or D) within a time limit. Each correct answer is given an amount of points that is proportional to the remaining time. Wrong answers, as well as answers not given within the time limit, are given 0 points.
<br>This bot has been used for a contest at the international tech conference Codemotion Rome 2017 (March 24-25th 2017).
<br>The best scores were shown on a monitor. [Here](https://github.com/LucaCalabrese/codemotion-spark-bot-scores) you can find the code used for the ranking page.

Installation
------------
### Application Server
* Download and install <a href="http://wildfly.org/downloads/">Wildfly 10.1.0.Final</a>

### Database connection
* Download and install <a href="https://www.postgresql.org/download/">PostgreSQL 9.6</a>.
* Create a new database (through PGAdmin or CLI).
* Run script quizdb.sql (in *setup* folder) to create all the tables.
* Download <a href="https://jdbc.postgresql.org/download.html">PostgreSQL JDBC driver</a> (v 9.4.1212).
* Copy postgresql-9.4.1212.jar into folder *WILDFLY_HOME/modules/system/layers/base/org/postgresql/main*, where *WILDFLY_HOME* is the path of the Wildfly root directory.
* In the above folder insert *module.xml*.
* Make a backup copy of file *WILDFLY_HOME/standalone/configuration/standalone.xml*
* Copy the provided *standalone.xml* into folder *WILDFLY_HOME/standalone/configuration*
* Open the new *standalone.xml* and reach the following lines:
```xml
<subsystem xmlns="urn:jboss:domain:datasources:4.0">
	<datasources>
		<datasource jndi-name="java:jboss/datasources/ExampleDS" pool-name="ExampleDS" enabled="true" use-java-context="true">
			<connection-url>jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE</connection-url>
			<driver>h2</driver>
			<security>
				<user-name>sa</user-name>
				<password>sa</password>
			</security>
		</datasource>
		<datasource jta="true" jndi-name="java:jboss/datasources/quizdb" pool-name="quizdb" enabled="true" use-java-context="true">
			<connection-url>connection-url</connection-url>
			<driver>postgresql</driver>
			<security>
				<user-name>user-name</user-name>
				<password>password</password>
			</security>
		</datasource>
		<drivers>
			<driver name="h2" module="com.h2database.h2">
				<xa-datasource-class>org.h2.jdbcx.JdbcDataSource</xa-datasource-class>
			</driver>
			<driver name="postgresql" module="org.postgresql">
				<xa-datasource-class>org.postgresql.xa.PGXADataSource</xa-datasource-class>
			</driver>
		</drivers>
	</datasources>
</subsystem>
```
* Substitute:
  * *connection-url* with the url of your database (e.g. *jdbc:postgresql://example.com/quizdb*)
  * *user-name* and *password* with the credentials used when accessing your database
* Connect to the database and change the [configurations](#configuration-variables) according to your environment.

### Deployment
After building sources, choose one of the following:
* Deploy both .war files (*chatbot-clients-ciscospark.war* and *chatbot-codemotion.war*) through the Administration Console of Wildfly.
* Copy both .war files in folder WILDFLY_HOME/standalone/deployments.

Commands
--------
This is the list of commands that can be given to **QuizBot** through the Cisco Spark interface.

### User commands

* **play**: Starts the game or goes to the next question if the game has already started.
* **next**: Same as <b>play</b>.
* **a / b / c / d**: Used to select an answer when prompted by the bot.
* **score**: Prints the current score for the user.
* **phone**: Allows to edit the user's contact phone number
* **now**: Shows the list of upcoming events.
* **help**: Shows the list of user commands in every step of the game.

### Admin commands (ChatOps)
All admin commands start with **/** and can be issued only by users with Admin role.
* **/clean [user's Spark email]**: Deletes all user data from the database, allowing him/her to play again (useful for testing).
* **/clearcache**: Forces the application to reload the configuration settings from the database.
* **/setconf [key] [value]**: Edits a configuration value.

### Business commands
All business commands start with **/** and can be issued only by users with Marketing role.
* **/winners**: Shows the top 3 scorers. Useful for winners extraction at the end of the contest.
* **/stats**: Shows the total number of people registered to the contest.

User roles
----------

* **Admin**: Users allowed to use admin commands.
* **Marketing**: Users allowed to use business commands.
* **Normal user**: Every user that is not included in the roles above.

Users can be assigned both Admin and Marketing role. Maintainers of the bot should have both these roles.
Roles can be assigned by filling table *USER_ROLES*:

<table>
<h>
<td>id</td>
<td>user_id</td>
<td>admin</td>
<td>marketing</td>
</h>
<tr>
<td>Sequence number for the row</td>
<td>Id of the user (references <i>USERS</i> table)</td>
<td>True/False</td>
<td>True/False</td>
</tr>
</table>

Configuration variables
-----------------------

These configurations can be edited by updating the *BOT_CONFIG* table or by issuing command **/setconf** to the bot. 
* **CONTEST_FINISHED_MSG**: Text message shown at the end of the contest.
* **CTA_END_MSG**: Text message shown at the end of the game.
* **CTA_MIDDLE_MSG**: Text message shown after question **CTA_QUESTION_NR**.
* **CTA_MIDDLE_VALIDITY_DATE**: Date after which **CTA_MIDDLE_MSG** stops being shown (milliseconds since Jan 01 1970).
* **CTA_QUESTION_NR**: Question after which the **CTA_MIDDLE_MSG** is shown.
* **CTA_START_MSG**: Text message shown at the beginning of the game.
* **DISCLAIMER**: Disclaimer text being shown when the user is prompted to insert his/her phone number.
* **EVENTS_URL**: Url to get the list of upcoming events at the conference. 
* **GAME_ACTIVE**: When the game is not active, users cannot play (true / false).
* **GAME_COMPLETED**: Message to show when the user has already completed the game (e.g.: "You have already completed the game!").
* **HELP_MSG**: Help message, shown with **/help** command.
* **MAP_CAPTION**: Caption for the image shown at the end of the game.
* **MAP_URL**: URL for the image shown at the end of the game.
* **NUMBER_OF_STAGES**: Unused, set to 1.
* **QUESTIONS_PER_STAGE**: Number of questions in a stage, i.e. the total number of questions.
* **QUESTION_TIMEOUT**: Timeout for the questions (in ms).
* **SPARK_CONTEXT_ROOT**: Context root of chatbot-clients-ciscospark application (/client).
* **SPARK_HOST**: Host of chatbot-clients-ciscospark application (e.g. the AWS EC2 host).
* **SPARK_PORT**: Port of chatbot-clients-ciscospark application (e.g. 8080).
* **WELCOME_MSG**: Welcome message shown when the bot is added to a room or in a 1-1 conversation.

These configurations can be edited by updating the *SPARK_CONFIG* table.
* **BOT_CONTEXT_ROOT**: Context root of chatbot-clients-ciscospark application (/chatbot-codemotion).
* **BOT_HOST**: Host of chatbot-codemotion application (e.g. the AWS EC2 host).
* **BOT_PORT**: Port of chatbot-codemotion application (e.g. 8080).
* **SPARK_BOT_TOKEN**: Spark token of the bot.

Modules description
-------------------

### chatbot-clients-common
Contains common interfaces for different clients.

### chatbot-clients-ciscospark
Web app that is responsible for the communication with Spark through the <a href="https://github.com/ciscospark/spark-java-sdk">Spark Java SDK</a>.

### chatbot-clients-ciscospark-logic
Business logic for <a href="https://github.com/LucaCalabrese/codemotion-spark-bot#chatbot-clients-ciscospark">chatbot-clients-ciscospark</a>.

### chatbot-codemotion
Web app that exposes REST APIs for the interaction with the bot.

### chatbot-codemotion-logic
Contains the business logic of the bot (game management) and the interaction with the database.
