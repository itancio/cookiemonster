# Cookie Monster
This is a game development project by Camila Picanco Mesquita and Irvin Tancioco for UC Berkeley Data Structure

## Project Description
This is a cookie monster game designed to teach children about healthy and unhealthy food consumption. Cookie monster collects food to gain calories and extend its life. The goal is to consume all the food or find the golden cookie without running out of lives or calories.

## Game Class Structure
* The `CookieMonsterGame` class is responsible for the game mechanics. The food items are placed randomly on floor tiles with their types determined by chance. The player controls an avatar through input commands, with movement being constrained by walls and calorie consumption. Eating different foods can either increase the avatar's calories and life or decrease life when retracing steps on red tiles. The game is won by obtaining a golden cookie but can end if the avatar depletes its calories or life or eats all the food.

<img src="assets/cookiemonster-start.png" width="80%"/>

* The `MapGenerator` class creates game maps using a constructor that takes width, height, and a seed for random generation. It clears the map and then populates it with rooms and connecting hallways. Rooms are drawn at random positions with random sizes, and L-shaped hallways connect them. There are methods for drawing both horizontal and vertical parts of the hallways and various helper methods for tasks such as generating random numbers and validating room placement.The map generation is designed to avoid room overlap and ensure everything fits within the map boundaries, and the seeded randomness supports consistent map creation for debugging and consistent gameplay. The current algorithm is basic, with room for more complexity and additional features.

<img src="assets/cookiemonster-grid.png" width="80%"/>
  
* The `Display` class utilizes the StdDraw library to render the game's user interface, including screens for starting the game, entering a seed, displaying game stats like lives and score, and showing end game messages. It follows the Single Responsibility Principle, with methods dedicated solely to display functions, and uses static methods indicating a single display manager is used. The class relies on consistent UI elements defined in external classes and could benefit from code refactoring to reduce duplication and error handling for more robustness, especially regarding external image dependencies and screen resolution variations.
* The `Engine` class acts as the command center for a tile-based game, managing setup, progression, and user interactions, such as a character collecting items on a map. It utilizes a variety of fields and constants to maintain the game state, alongside methods for initializing the game, handling user inputs via keyboard or string, pausing the game, and restarting after the game ends. The gameplay loop includes menu navigation, game world rendering, and game state updates. The class, which heavily relies on static imports for settings and utilities, also features functionality for saving and replaying game progress. It could be improved with method refactoring, enhanced error handling, and a more flexible command handling system to streamline gameplay management and expandability.
