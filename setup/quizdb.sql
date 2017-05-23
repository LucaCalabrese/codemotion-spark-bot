--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.4
-- Dumped by pg_dump version 9.6.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: bot_config; Type: TABLE
--

CREATE TABLE bot_config (
    key character varying(256) NOT NULL,
    value character varying(2000)
);


--
-- Name: questions; Type: TABLE
--

CREATE TABLE questions (
    id integer NOT NULL,
    text character varying(2000),
    ans1 character varying(256),
    ans2 character varying(256),
    ans3 character varying(256),
    ans4 character varying(256),
    correct_ans integer
);


--
-- Name: questions_id_seq; Type: SEQUENCE
--

CREATE SEQUENCE questions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: questions_id_seq; Type: SEQUENCE OWNED BY
--

ALTER SEQUENCE questions_id_seq OWNED BY questions.id;


--
-- Name: spark_config; Type: TABLE
--

CREATE TABLE spark_config (
    key character varying(256) NOT NULL,
    value character varying(2000)
);


--
-- Name: user_answers; Type: TABLE
--

CREATE TABLE user_answers (
    user_id character varying NOT NULL,
    question_id integer NOT NULL,
    score integer DEFAULT 0,
    answered boolean DEFAULT false,
    ans_given integer,
    stage_id integer,
    correct boolean,
    seq integer
);


--
-- Name: user_roles; Type: TABLE
--

CREATE TABLE user_roles (
    id integer NOT NULL,
    user_id character varying(256),
    admin boolean,
    marketing boolean
);


--
-- Name: user_roles_id_seq; Type: SEQUENCE
--

CREATE SEQUENCE user_roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: user_roles_id_seq; Type: SEQUENCE OWNED BY
--

ALTER SEQUENCE user_roles_id_seq OWNED BY user_roles.id;


--
-- Name: users; Type: TABLE
--

CREATE TABLE users (
    id character varying(256) NOT NULL,
    username character varying(256),
    email character varying(256),
    last_question_id integer,
    status character varying(256),
    stage_id integer DEFAULT 1,
    questions_answered integer DEFAULT 0,
    total_score integer DEFAULT 0,
    phone character varying(128)
);


--
-- Name: questions id; Type: DEFAULT
--

ALTER TABLE ONLY questions ALTER COLUMN id SET DEFAULT nextval('questions_id_seq'::regclass);


--
-- Name: user_roles id; Type: DEFAULT
--

ALTER TABLE ONLY user_roles ALTER COLUMN id SET DEFAULT nextval('user_roles_id_seq'::regclass);


--
-- Data for Name: bot_config; Type: TABLE DATA
--

INSERT INTO bot_config (key, value) VALUES ('SPARK_PORT', '8080');
INSERT INTO bot_config (key, value) VALUES ('SPARK_CONTEXT_ROOT', '/client');
INSERT INTO bot_config (key, value) VALUES ('SPARK_HOST', 'ec2-35-160-245-208.us-west-2.compute.amazonaws.com');
INSERT INTO bot_config (key, value) VALUES ('NUMBER_OF_STAGES', '1');
INSERT INTO bot_config (key, value) VALUES ('CTA_QUESTION_NR', '5');
INSERT INTO bot_config (key, value) VALUES ('QUESTIONS_PER_STAGE', '10');
INSERT INTO bot_config (key, value) VALUES ('CTA_MIDDLE_VALIDITY_DATE', '1490396399000');
INSERT INTO bot_config (key, value) VALUES ('CTA_START_MSG', '**Join Devnet today https://developer.cisco.com/ ! The open innovation platform for developers who would like to innovate with Cisco API & technologies!**');
INSERT INTO bot_config (key, value) VALUES ('CTA_END_MSG', '**Join Devnet today https://developer.cisco.com/ ! The open innovation platform for developers who would like to innovate with Cisco API & technologies!**');
INSERT INTO bot_config (key, value) VALUES ('DISCLAIMER', '**We respect your privacy. The personal information provided will not be sold to third parties or used for commercial purposes. Collected data will only be used to reach the winners of the competition''s prizes**');
INSERT INTO bot_config (key, value) VALUES ('EVENTS_URL', 'https://codemotion-rome-2017.herokuapp.com/next');
INSERT INTO bot_config (key, value) VALUES ('GAME_ACTIVE', 'true');
INSERT INTO bot_config (key, value) VALUES ('QUESTION_TIMEOUT', '30000');
INSERT INTO bot_config (key, value) VALUES ('MAP_URL', 'https://s3-us-west-2.amazonaws.com/cisco-partnerclub-assets/in+colaborazione.png');
INSERT INTO bot_config (key, value) VALUES ('MAP_CAPTION', 'Grazie per aver partecipato e giocato con le soluzioni Intel Cisco!');
INSERT INTO bot_config (key, value) VALUES ('GAME_COMPLETED', 'You have already completed the game!');
INSERT INTO bot_config (key, value) VALUES ('CONTEST_FINISHED_MSG', 'The contest has finished! Thanks to all participants!');
INSERT INTO bot_config (key, value) VALUES ('HELP_MSG', 'Here are the things you can ask me: <br><ul><li>**play**: start the game!</li><li>**a**, **b**, **c** or **d**: answer - please don''t write the full text of the answer or I will get confused!</li><li>**next**: go to the next question</li><li>**score**: view your current score</li><li>**phone**: change your contact phone number</li><li>**help**: view this help again!</li></ul>');
INSERT INTO bot_config (key, value) VALUES ('WELCOME_MSG', 'Welcome to the **Coolest Geek Contest**!<br>Just type **play** when you are ready!<br>If I am in a space with other people (or bots like me) you need to mention me using "@" or I won''t read your messages...<br>For this reason, if you add me in a one-to-one conversation you can play much faster!');
INSERT INTO bot_config (key, value) VALUES ('CTA_MIDDLE_MSG', '');


--
-- Data for Name: questions; Type: TABLE DATA
--

INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (1, 'What is the running time of a binary search on a sorted array of n elements?', '(1)', '(lg n) ', '(n)', '(n^2)', 2);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (2, 'Which of the following best describe polymorphism?', 'An Object Oriented Programming concept that allows for any object to be transformed into any other object.', 'An Object Oriented Programming concept that allows objects to hide data from each other.', 'A programming language feature that allows an object to hide its data from other objects.', 'A programming language feature that allows the values of different data types to be handled using a uniform interface.', 4);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (3, 'Which operation takes more than O(1) time with the Hash Table data structure?', 'Search', 'Sort', 'Insert', 'Delete', 2);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (4, 'What is the worst case running time for finding an element in a Splay Tree?', 'O(1)', 'O(lg n)', 'O(n) ', 'O(n lg n)', 3);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (5, 'Which sorting algorithm has an upper bound of O(n lg n)?', 'Bubble Sort', 'Insertion Sort', 'Merge Sort ', 'Quicksort', 3);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (6, 'How is a true or false statement put into code that the programmer expects to always be true called?', 'exception', 'harness', 'assertion ', 'expression', 3);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (7, 'How is a section of code that responds to a particular interaction of the user with a gui control called?', 'dispatch function', 'event handler ', 'control structure', 'exception handler', 2);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (8, 'Which is the purpose of XSLT?', 'speed up database queries', 'encrypt passwords', 'transform XML into HTML ', 'cache html pages', 3);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (9, 'What is a computer program composed by?', 'a completed flowchart', 'algorithms', 'algorithms written in computer’s language', 'discrete logical steps', 2);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (10, 'Which are the errors that can be pointed out by the compiler?', 'Syntax errors', 'Internal errors', 'Semantic errors', 'Logical errors', 1);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (11, 'MVC is the name of a famous pattern for implementing user interfaces. Which is its acronym?', 'My Versatile Controller', 'Modern Virtual Compilator', 'Magic Visible Customization', 'Model View Controller ', 4);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (12, 'Which programming language can be directly understood by the CPU?', 'Java', 'Javascript', 'C#', 'Assembly ', 4);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (13, 'What is the name of the Java mascot?', 'Duke ', 'Mr. Java', 'Snoo', 'Tux', 1);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (14, 'Who is the creator of the JavaScript scripting language?', 'Brendan Eich ', 'Guido van Rossum', 'John Ousterhout', 'Larry Wall', 1);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (15, 'Which famous computer scientist killed himself by eating a posion apple?', 'Alan Turing ', 'Bjarne Stroustrup', 'Donald Knuth', 'Edsger Dijkstra', 1);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (16, 'What programming language did Bjarne Stroustrup create?', 'Bash Shell Scripting', 'C++ ', 'Python', 'Perl', 2);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (17, 'What was the first song ever sung by a computer?', 'Daisy Bell ', 'Happy Birthday', 'Mary Had a Little Lamb', 'Twinkle Twinkle Little Star', 1);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (18, 'In what year was the specification for the COBOL language created?', '1952', '1959 ', '1964', '1970', 2);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (19, 'What is grep?', 'Generic REPresentation', 'A system administration utility in Unix-like operating systems.', 'A command line text editor in Unix-like operating systems.', 'A command line text search utility in Unix-like operating systems. ', 4);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (20, 'What is the origin of the Python scripting language''s name?', 'The company that developed it wanted a name that explanationressed its dynamic nature and power as a scripting language.', 'The creator thought it was a cool name.', 'It was named after Monty Python''s Flying Circus. ', 'It was named after Python, the earth-dragon of Delphi. Which was a serpent from ancient Greece.', 3);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (21, 'Who founded the "Free Software Foundation"?', 'Jon Gosselin', 'Linus Torvalds', 'Paul Allen', 'Richard Stallman ', 4);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (22, 'What was Java called before it was Java?', 'Maple', 'Oak ', 'Snoo', 'There was no working name for Java before it was named Java.', 2);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (23, 'Which is the language John Kemeny and Thomas Kurtz invented t Dartmouth College in 1964?', 'ALGOL', 'BASIC ', 'FORTRAN', 'PASCAL', 2);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (24, 'Which of these is not a functional programming language?', 'F#', 'Erlang', 'LISP', 'Fortran ', 4);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (25, 'Dylan, Erlang, Haskell and ML are examples of?', 'web languages', 'declarative languages', 'functional languages ', 'visual languages', 3);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (26, 'What is RISC?', 'a defensive programming style', 'a web framework', 'a threading library', 'a CPU design strategy ', 4);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (27, 'What does all computers execute?', 'BASIC programs', 'COBOL programs', 'machine language programs ', 'PL/1 programs', 3);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (28, 'Which programming language was used for writing the popular operating system UNIX?', 'C++', 'SNOBOL', 'PASCAL', 'C', 4);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (29, 'Who is called the ''mother'' of COBOL?', 'Grace Murray Hopper ', 'Ada Augusta Byron', 'Masatoshi Shima', 'Kathleen Jensen', 1);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (30, 'What are compilers and interpreters themselves?', 'high level languages', 'programs', 'codes', 'mnemonics', 2);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (31, 'How is a Computer programming language based on problems solved called?', 'advanced level programming language', 'ordinary programming language', 'high level programming language ', 'low level programming language', 3);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (32, 'How is building of new number set and placing into correct order in data sorting is considered as?', 'insertion ', 'selection', 'exchange', 'change', 1);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (33, 'Except what process the high level language program before ready to be executed must go through?', 'Linking', 'Loading', 'Controlling', 'Translation ', 4);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (34, 'How many bits is ASCII codes composed of?', '8 ', '16', '10', '24', 1);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (35, 'DNS is acronym for?', 'Data Number Sequence', 'Digital Network Service ', 'Domain Name System', 'Disk Numbering System', 3);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (36, 'Which company originally invented Java?', 'Oracle', 'Microsoft', 'Novell', 'Sun ', 4);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (37, 'Which is the unit of speed used for super computer?', 'KELOPS', 'GELOPS ', 'MELOPS', 'None of these', 2);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (38, 'Whose trademark is the operation system UNIX?', 'Motorola', 'Microsoft', 'BELL Laboratories ', 'AshtonTate', 3);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (39, 'How was the first mechanical computer designed by Charles Babbage called?', 'Abacus', 'Analytical Engine ', 'Calculator', 'Processor', 2);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (40, 'For which purpose is the parity bit added?', 'Coding', 'Error detection ', 'Controlling', 'Indexing', 2);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (41, 'What is the full form of IP?', 'Interface program', 'Interface protocol', 'Internet program', 'Internet protocol ', 4);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (42, 'What is the number of bit patterns provided by a 7 bit code?', '64', '128 ', '256', '512', 2);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (43, 'How is the word length of a computer measured in?', 'bits', 'bytes ', 'millimetres', 'metres', 2);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (44, 'What is the full form of TCP?', 'Transmission control program', 'Totalling control program', 'Transmission control protocol ', 'Total control program', 3);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (45, 'Who is considered to be The Father of Artificial Intelligence?', 'Alan Turing', 'John McCarthy ', 'George Boole', 'Allen', 2);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (46, 'Which one of these is not an example of real security and privacy risks?', 'hackers', 'spam ', 'viruses', 'identity theft', 2);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (47, 'Which of the following is NOT one of the four major data processing functions of a computer?', 'gathering data', 'processing data into information', 'analyzing the data or information', 'storing the data or information', 3);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (48, 'How is data called when it has been organized or presented in a meaningful fashion?', 'A process', 'Software', 'Storage', 'Information ', 4);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (49, 'What does it mean when computers allow to gather data from users?', 'present', 'input ', 'output', 'store', 2);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (50, 'What city does Batman protect?', 'Springfield', 'New York', 'Smallville', 'Gotham City ', 4);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (51, 'Who is Mario’s brother?', 'Luigi ', 'Yoshi', 'Antonio', 'Wario', 1);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (52, 'Live long and', 'prosper ', 'conquer', 'may the force be with you', 'travel', 1);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (53, 'What’s the name of the main protagonist of the “Legend of Zelda” series, who is always controlled by the player?', 'Zelda', 'Link ', 'Ganon', 'Mido', 2);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (54, 'What is Iron Man’s real name?', 'Clark Kent', 'Tony Stark ', 'Bruce Wayne', 'Peter Parker', 2);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (55, 'What is the popular wookie’s name from Star Wars?', 'Chewbacca', 'Jawa', 'Ackbar', 'Wicket W. Warrick', 1);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (56, 'What is the answer to life, the universe and everything?', 'Nobody knows', 'The universe is made of little particles called “atoms”', '42 ', 'All your base are belong to us', 3);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (57, 'What planet does the Death Star destroy in Star Wars Episode IV: A New Hope?', 'Alderaan ', 'Bespin', 'Cymoon 1', 'Geonosis', 1);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (58, 'Which animal is Sonic, the famous SEGA mascot?', 'dog', 'cat', 'hedgehog ', 'coyote', 3);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (59, '3 + 3 - 3 * 3 + 3. What is the result of this operation? ', '42', '12', '0', 'NaN', 3);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (60, 'Which of the following are the first 8 Fibonacci numbers sequence?', '1, 1, 2, 3, 5, 8, 13, 21, 34, 55 ', '1, 2, 3, 4, 5, 6, 7, 8, 9, 10', '1, 2, 4, 8, 16, 32, 64, 128, 256, 512', '2, 3, 5, 7, 11, 13, 17, 19, 23, 29', 1);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (61, 'Our universe actually does exists in Marvel Comic Books. How is it referred as?', 'Earth-1218 ', 'Earth-2017', 'Earth-199999', 'Earth', 1);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (62, 'All of the following characters appear in any of the Marvel universes except one. Which one?', 'Clark Kent', 'Eminem', 'Peter Parker', 'Donald Duck ', 4);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (63, 'The Nintendo Entertainment System (NES) is the first true home game console released by Nintendo. When was it released in America?', '1989', '1985 ', '2000', '1970', 2);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (64, 'Which year was the first iPhone released in?', '2000', '2010', '2007 ', '1995', 3);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (65, 'What are the names of the ghosts who chase Pac Man and Ms. Pac Man?', 'Inky, Blinky, Pinky and Clyde ', 'Inky, Twinky, Pinky and Clyde', 'Micky, Blinky, Pinky and Clyde', 'Inky, Blinky, Mocky and Clyde', 1);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (66, 'Who was the captain of the Enterprise in the pilot episode of Star Trek?', 'Captain Pike ', 'Captain Spock', 'Captain Kirk', 'Captain Archer', 1);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (67, 'What was the first ever game released on a commercial gaming console that was in 3D?', '3D Monster Maze ', 'Doom', 'Wolfenstein 3D', 'Super Mario 3D', 1);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (68, 'In what year was the arcade game Pong created?', '1972 ', '1964', '1976', '1969', 1);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (69, 'The Term “Geek” Originally Referred To?', 'Circus Performers ', 'Astronauts', 'Computer Engineers ', 'RPG Enthusiast ', 1);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (70, 'The Best Selling PC Game Of All Time Is?', 'Minecraft ', 'The Sims', 'World of Warcraft', 'Myst', 1);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (71, 'Website Security CAPTCHA Forms Are Descended From the Work of?', 'Charles Babbage', 'Alan Turing ', 'Grace Hopper', 'Larry Page', 2);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (72, 'The First Commercial Coin-Operated Video Game Was?', 'Pac-Man', 'Computer Space ', 'Pong', 'Spacewar!', 2);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (73, 'Who is considered to be the world''s first programmer?', 'Ada byron Lovelace ', 'Alan Turing', 'George Boole ', 'Konrad Zuge', 1);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (74, 'Who co-created the UNIX operating system in 1969 with Dennis Ritchie?', 'Bjarne Stroustrup', 'Steve Wozniak', 'Niklaus Wirth', 'Ken Thompson ', 4);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (75, 'Who designed the original Java language and wrote "Emacs" for Unix systems?', 'James Gosling ', 'Ramus Lerdorf', 'Larry Ellison', 'Larry Wall', 1);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (76, 'Who developed PHP', 'Phil Zimmermann', 'Linus Torvalds', 'Ramus Lerdorf ', 'Larry Wall', 3);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (77, 'Who co-founded Microsoft Corporation with Bill Gates in 1975?', 'Andy Grove', 'Paul Allen ', 'Larry Wall', 'Larry Ellison', 2);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (78, 'Who founded the WWW Consortium (W3C)?', 'Bill Gates', 'Marc Andreessen', 'Steve Case', 'Tim Berners-Lee ', 4);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (79, 'Who invented the computer mouse', 'Jerry Yang', 'Doug Englebart ', 'Tim Berners-Lee', 'Ada Byron Lovelace', 2);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (80, 'Who is the inventor of the C++ programming language?', 'Bjarne Stroustrup ', 'Phil Zimmermann', 'Charles Goldfarb', 'Ray Tomlinson', 1);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (81, 'Which cartoon family lives in Bedrock?', 'The Simpsons', 'The Pickles', 'The Flintstones ', 'The Jetsons', 3);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (82, 'What is SpongeBob''s pet snail''s name?', 'Bob', 'Rob', 'Sam', 'Gary', 4);
INSERT INTO questions (id, text, ans1, ans2, ans3, ans4, correct_ans) VALUES (83, 'What does the Scooby gang call their van?', 'Spooky Van', 'Supernatural Mobile', 'Mystery Inc.', 'Mystery Machine', 4);

--
-- Name: questions_id_seq; Type: SEQUENCE SET
--

SELECT pg_catalog.setval('questions_id_seq', 3, true);


--
-- Data for Name: spark_config; Type: TABLE DATA
--

INSERT INTO spark_config (key, value) VALUES ('BOT_PORT', '8080');
INSERT INTO spark_config (key, value) VALUES ('BOT_CONTEXT_ROOT', '/chatbot-codemotion');
INSERT INTO spark_config (key, value) VALUES ('BOT_HOST', 'ec2-35-160-245-208.us-west-2.compute.amazonaws.com');
INSERT INTO spark_config (key, value) VALUES ('SPARK_BOT_TOKEN', '');


--
-- Name: user_roles_id_seq; Type: SEQUENCE SET
--

SELECT pg_catalog.setval('user_roles_id_seq', 1, true);


--
-- Name: bot_config bot_config_pkey; Type: CONSTRAINT
--

ALTER TABLE ONLY bot_config
    ADD CONSTRAINT bot_config_pkey PRIMARY KEY (key);


--
-- Name: user_answers pk_user_answers; Type: CONSTRAINT
--

ALTER TABLE ONLY user_answers
    ADD CONSTRAINT pk_user_answers PRIMARY KEY (user_id, question_id);


--
-- Name: questions questions_pkey; Type: CONSTRAINT
--

ALTER TABLE ONLY questions
    ADD CONSTRAINT questions_pkey PRIMARY KEY (id);


--
-- Name: spark_config spark_config_pkey; Type: CONSTRAINT
--

ALTER TABLE ONLY spark_config
    ADD CONSTRAINT spark_config_pkey PRIMARY KEY (key);


--
-- Name: users users_pkey; Type: CONSTRAINT
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: question_id_index; Type: INDEX
--

CREATE UNIQUE INDEX question_id_index ON questions USING btree (id);


--
-- Name: user_answers_index; Type: INDEX
--

CREATE INDEX user_answers_index ON user_answers USING btree (user_id);


--
-- Name: user_answers user_answers_question_id_fkey; Type: FK CONSTRAINT
--

ALTER TABLE ONLY user_answers
    ADD CONSTRAINT user_answers_question_id_fkey FOREIGN KEY (question_id) REFERENCES questions(id);


--
-- Name: user_answers user_answers_user_id_fkey; Type: FK CONSTRAINT
--

ALTER TABLE ONLY user_answers
    ADD CONSTRAINT user_answers_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id);


--
-- PostgreSQL database dump complete
--

