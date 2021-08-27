package byow.Core;

import byow.InputDemo.*;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;
import java.io.File;

import static byow.Core.Setting.*;
import static byow.Core.Display.*;
import static byow.Core.Utils.*;


public class Engine {
    TERenderer ter = new TERenderer();
    private TETile[][] world;
    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    public static final File SAVED_GAME = join(CWD, "saved_game.txt");

    /** Game State */
    Boolean endGame;
    Boolean delayGame;
    Boolean playNewGame;
    long seed;
    char lastInputChar;
    String commands;
    String endMsg;
    int count;



    /** Constructor that initialized default world. */
    public Engine() {
        defaultGame();
    }
    /** Initialize all default variables of the game. */
    private void defaultGame() {
        //ter.initialize(WIDTH, HEIGHT);
        seed = -1;
        commands = "";
        endGame = false;
        delayGame = false;
        playNewGame = true;
        count = 0;
    }

    /** Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu. */
    public void interactWithKeyboard() {
        while (playNewGame) {
            InputSource inputSource = new KeyboardInputSource();
            // Stores the seed numbers
            String numbers = "";
            char c;

            choseFromMenu(inputSource);

            // Create game
            CookieMonsterGame game = new CookieMonsterGame(WIDTH, HEIGHT, seed);

            if (lastInputChar == LOAD) {
                game.moveAvatar(commands);
            } else if (lastInputChar == REPLAY) {
                String temp = commands;
                count += temp.length();
                inputSource = new StringInputDevice(temp);
                commands = "";
                delayGame = true;
            }

            while (!endGame || count > 0) {
                ter.renderFrame(game.getWorld());
                world = game.getWorld();

                displayLegend();
                displayCalories(game.getCalories());
                displayLife(game.getAvatarLife());

                int x = (int) StdDraw.mouseX();
                int y = (int) StdDraw.mouseY();

                if (!endGame && count == 0) {
                    inputSource = new KeyboardInputSource(world);
                    delayGame = false;
                }

                if (inputSource.possibleNextInput()) {
                    c = inputSource.getNextKey();

                    // Only true for relay
                    if (delayGame) {
                        timeDelay(100);
                        count -= 1;
                    }

                    if (c == ':' && inputSource.possibleNextInput()) {
                        c = inputSource.getNextKey();
                        if (c == 'q') {
                            writeContents(SAVED_GAME, seed + "s" + commands);
                            System.exit(0);
                        }
                    }
                    endGame = game.moveAvatar(Character.toString(c));
                    commands += c;
                }
            }
            endMsg = game.getEndMsg();
            displayEndGame(endMsg);
            timeDelay(1000);
            playNewGame = playAgain();
        }
        System.exit(0);
    }
    /** Gets user's choice from the main menu and
     * calls right method depending on user choice.
     * the game will end if the user chooses Q or
     * L (iff SAVED_GAME does not exist). */
    private void choseFromMenu(InputSource inputSource) {
        displayStartingScreen();
        char optionChosen = inputSource.getNextKey();

        while (!VALID_OPTIONS.contains(optionChosen)) {
            displayStartingScreen();
            optionChosen = inputSource.getNextKey();
        }

        if (optionChosen == NEW_GAME) {
            getSeedFromKeyboard(inputSource);
        } else if (optionChosen == LOAD) {
            if (!SAVED_GAME.exists()) {
                System.exit(0);
            } else {
                String savedGame = readContentsAsString(SAVED_GAME);
                getSeedAndCommandsFromString(savedGame);
                lastInputChar = LOAD;
            }
        } else if (optionChosen == REPLAY) {
            if (!SAVED_GAME.exists()) {
                System.exit(0);
            } else {
                lastInputChar = REPLAY;
                String savedGame = readContentsAsString(SAVED_GAME);
                getSeedAndCommandsFromString(savedGame);
            }
        } else if (optionChosen == QUIT) {
            System.exit(0);
        }
    }
    /** Gets the seed from the keyboard. */
    private void getSeedFromKeyboard(InputSource inputSource) {
        displaySeed();
        String numbers = "";
        // Reads in the user's seed input
        char c = inputSource.getNextKey();

        while (c != END_OF_SEED) {
            if (Character.isDigit(c)) {
                numbers += c;
                displaySeedInput(numbers);
            }
            c = inputSource.getNextKey();
        }
        try {
            seed = Long.parseLong(numbers);
        } catch (NumberFormatException e) {
            seed = 9223372036854775807L;
        }

    }
    /** Gets the seed and commands from a string. */
    private void getSeedAndCommandsFromString(String input) {
        String numbers = "";
        //@Source: InputDemo
        InputSource inputSource = new StringInputDevice(input);
        while (inputSource.possibleNextInput()) {
            char c = inputSource.getNextKey();
            if (Character.isDigit(c)) {
                numbers += c;

            // Second condition prevents seed to be overwritten if s is called again
            } else if (c == END_OF_SEED && seed == -1) {
                seed = Long.parseLong(numbers);
            } else if (VALID_COMMANDS.contains(c)) {
                commands += c;
            } else if (c == ':' && inputSource.possibleNextInput()) {
                c = inputSource.getNextKey();
                if (c == 'q') {
                    writeContents(SAVED_GAME, seed + "s" + commands);
                    endGame = true;
                    break;
                } else if (VALID_COMMANDS.contains(c)) {
                    commands += c;
                }
            }
        }
    }
    /** Slows down frame by some time DELAY */
    private void timeDelay(int delay) {
        StdDraw.disableDoubleBuffering();
        StdDraw.pause(delay);
        StdDraw.enableDoubleBuffering();
    }
    /** Displays a message to the user to see if they want
     * to play the game again. If yes, sets all variables
     * to default game and returns true. Otherwise, returns
     * false.*/
    private Boolean playAgain() {
        timeDelay(1000);
        displayEndScreen();
        InputSource input = new KeyboardInputSource();
        while (true) {
            char c = input.getNextKey();
            if (c == 'y') {
                defaultGame();
                return true;
            } else if (c == 'n') {
                return false;
            }
        }
    }
    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, both of these calls:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // Sets the input string to be all lower case letters
        input = input.toLowerCase();
        // Get first character in the input string
        char first = input.charAt(0);
        if (first == NEW_GAME) {
            // input string without first letter
            input = input.substring(1);
        } else if (first == LOAD) {
            String savedGame = readContentsAsString(SAVED_GAME);
            // Add saved game + input string without first letter
            input = savedGame + input.substring(1);
        }

        getSeedAndCommandsFromString(input);

        CookieMonsterGame game = new CookieMonsterGame(WIDTH, HEIGHT, seed);

        game.moveAvatar(commands);

        TETile[][] finalWorldFrame = game.getWorld();
        return finalWorldFrame;
    }
}
