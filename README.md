# COMP4004 Piraten Kapern (Assignment 1)
Zahid Dawod
101041370

The following code contains a 3 player Piraten Kapern game written in Java using Intellij.

The 3 players connect to a host server and each player gets to play their turn. There rounds go on until someone reaches 6000 points, once someone reaches 6000 points the final round initiates. In the final round the other players have a chance to play one last turn. At the end of the game whoever has the highest points is considered the winner. In each round and every turn the server keeps track of the players scores and what happens each turn and displays it via console. 

Versions
--------
Java 1.8.0_144

Intellij Community Edition 2020.2.1

How To Play
-----------
Clone the repository into a desired location.
Open your favourite IDE (Project was developed in Intellij but should work with eclipse as well)
Open the project.

Before you run any code first edit your configurations so that Game.java can be run in parallel as shown below:
![](https://i.gyazo.com/bf3b279505eb7319f58729f4ce1a4e2d.png)
Then click apply and ok.

How to run the code:
1) Run the Server.java first. (1 server)
2) Run the Game.java 3 times (3 clients)
3) Once the server is running you can join by selecting '1' as your option and hitting enter (blank is automatically set to 'localhost')
  
Note: For JUnit tests make sure the code is built with the JUnit dependencies by refreshing maven.
