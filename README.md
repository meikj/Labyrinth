Labyrinth
=========

Labyrinth is a University of Strathclyde CS207 semester two paired programming project by Gareth Gill and John Meikle. This project is being developed in strict accordance with the description and specification as outlined by Clemens Kupke. This can be viewed [here](http://classes.myplace.strath.ac.uk/mod/resource/view.php?id=508775).

It is a puzzle game in which there are two game modes: Treasure Chase and Letter Chase. At the moment only Treasure Chase is being developed, with Letter Chase being planned for development during Easter. The game takes place on a fixed size randomly generated maze in which there is a fixed number of immovable tiles located at each odd coordinate (e.g. (1,1), (1,3), ..., (3,1), (3,3)). 

In regards to Treasure Chase, there is one random tile (excluding (1,1)) that contains a piece of treasure. If the player collects this treasure, they have won the game. Score is determined by number of rounds (lower the better) taken. 

In regards to Letter Chase, the board contains a number of letters that the player can collect. Once the user thinks they can form a word they can attempt to do so. If a valid English word is formed, they have won the game. Score is determined by the number of rounds divided by l^2 where l is the length of the formed word - the smaller the score, the better.

The player has a spare tile in which they can rotate by 90, 180 or 270 before placing it on the board. The player has a choice of two moves: the tile move and the token move. The former allows the aforementioned spare tile to be pushed into either a row or column (the row or column **must not** contain any fixed/immovable tiles) which results in a tile from the opposite end falling off, thus becoming the new spare tile. The latter involves the token either moving up, down, left or right assuming that the move is valid (i.e. there are no walls obstructing the way). 

After the player performs their moves, a computer player randomly makes a tile move. The computer player acts as a player so retains score, spare tile, etc. It is a **very** simple opponent.

As it stands, the project is still under heavy development, but is progressing very well.

## Progress
* [Move Tile Use Case](https://github.com/meikj/Labyrinth/wiki/Move-Tile-Use-Case)
* [Move Token Use Case](https://github.com/meikj/Labyrinth/wiki/Move-Token-Use-Case)
* [Treasure Chase Use Case](https://github.com/meikj/Labyrinth/wiki/Treasure-Chase-Use-Case)
* [The Final Product](https://github.com/meikj/Labyrinth/wiki/The-Final-Product)

## Authors
* Gareth Gill - <gareth.gill@strath.ac.uk>
* John Meikle - <john.meikle@strath.ac.uk>

## UML Diagram
![Labyrinth UML Diagram](http://i.imgur.com/U8GY3eV.png)
