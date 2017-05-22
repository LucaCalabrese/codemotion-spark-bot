<center><img src="https://s3.eu-central-1.amazonaws.com/github-lucacalabrese-assets/codemotion-spark-bot/title.jpg"/></center>

# Spark Bot for CODEMOTION Rome 2017
Spark Bot for Codemotion Rome 2017

## Commands

### User commands

* **play**: Starts the game or goes to the next question if the game has already started.
* **next**: Same as <b>play</b>.
* **a / b / c / d**: Used to select an answer when prompted by the bot.
* **score**: Prints the current score for the user.
* **phone**: Allows to edit the user's contact phone number
* **now**: Shows the list of upcoming events.
* **help**: Shows the list of user commands in every step of the game.

### Admin commands

All admin commands start with **/** and can be issued only by users with Admin role.
* **/clean [user's Spark email]**: Deletes all user data from the database, allowing him/her to play again (useful for testing).
* **/clearcache**: Forces the application to reload the configuration settings from the database.
* **/setconf [key] [value]**: Edits a configuration value.

### Business commands
All business commands start with **/** and can be issued only by users with Marketing role.
* **/winners**: Extracts the 3 best scorers.
* **/stats**: Shows the total number of people registered to the contest.

## Modules description

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
