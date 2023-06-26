# Monopoly Deal Cards
Members:
Xiang Li: 21372326
Hanshu Rao: 21372327
Hao Zhang: 21372328
Ziqin Ma: 21372329

## Introduction
This is a simple game that allows players to enjoy a fun gaming experience. It offers various features and controls to enhance the gameplay. Read this document to learn more about the game and how to play it.

## Getting Started
To start the game, follow these steps:

1. Make sure you have JDK 17 installed on your system.
2. Ensure that the Maven dependencies are correctly loaded.
3. Navigate to the src/main/java/mdc directory and locate the ApplicationStart class.
4. Run the ApplicationStart class to start the game.

## Game Controls
The game can be controlled using the following inputs:

1. Mouse: 
 - Use the mouse to click on the game buttons.
2. Keyboard:
 - Left and Right Arrow Keys: Navigate and select cards, properties, and opponents.
 - Up and Down Arrow Keys: Select buttons and navigate through the options. Press Enter to confirm the selection (you can also use the mouse).
 - Esc and Enter Keys: Pause or resume the game. Pressing Esc while paused returns to the main menu (Prevent some possible bugs). Press Enter to continue playing.
## Game Over
The game ends when a player has three complete properties.
When the game is over, it automatically returns to the main menu. The winner's name will be displayed in the console.

## Tips:
If you want to change the number of players (initially 5), you can go to the src/main/java/mdc/states/game/MDCGame.java found a TODO. There you can change the number of players: 2 to 5. Sorry for not being able to do this in the UI.

## Enjoy playing the game and have fun!
