package byow.Core;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.*;

import static byow.Core.Setting.*;
import static byow.Core.Utils.join;

public class Display {
    public static final int OFFSET = 13;

    /** Displays the beginning of the game and get user's choice
     * from the displayed options. */
    public static void displayStartingScreen() {
        // Display the game's background image
        StdDraw.clear();
        StdDraw.picture(WIDTH / 2, HEIGHT - OFFSET, join(CWD, "front.png").toString());
        // Write the name of the game
        StdDraw.setFont(HEADING);
        StdDraw.setPenColor(32, 129, 196);

        StdDraw.text(WIDTH / 2, HEIGHT - (15 + OFFSET), "Cookie Monster Game");
        // Write menu options
        StdDraw.setFont(MENU);

        StdDraw.text(WIDTH / 2, HEIGHT - (20 + OFFSET), "New Game (N)");
        StdDraw.text(WIDTH / 2, HEIGHT - (23 + OFFSET), "Load Game (L)");
        StdDraw.text(WIDTH / 2, HEIGHT - (26 + OFFSET), "Replay Game (R)");
        StdDraw.text(WIDTH / 2, HEIGHT - (29 + OFFSET), "Quit (Q)");

        // Display instructions
        StdDraw.setFont(ITALIC);
        StdDraw.text(WIDTH / 2, 4, "Choose a key from the menu");

        // Display on screen
        StdDraw.show();
    }

    /** Displays box for seed input. */
    public static void displaySeed() {
        // Display the game's background image
        StdDraw.clear();
        StdDraw.picture(WIDTH / 2, HEIGHT - OFFSET, join(CWD, "front.png").toString());
        // Write the name of the game
        StdDraw.setFont(HEADING);
        StdDraw.setPenRadius(0.005);

        StdDraw.text(WIDTH / 2, HEIGHT - (15 + OFFSET), "Cookie Monster Game");

        // Instructions
        StdDraw.setFont(NORMAL);
        StdDraw.setPenColor(COOKIE_LT_BLUE);
        StdDraw.circle((WIDTH / 2) + 1.2, HEIGHT - (21.65 + OFFSET), 0.65);
        StdDraw.text(WIDTH / 2, HEIGHT - (20 + OFFSET), "Enter some number.");
        StdDraw.text((WIDTH / 2), HEIGHT - (21.75 + OFFSET), "Then, press   S   to start");

        // Input text field
        StdDraw.setPenColor(COOKIE_LT_BLUE);
        StdDraw.filledRectangle(WIDTH / 2, HEIGHT - (24 + OFFSET), 7, 1);

        StdDraw.setPenColor(COOKIE_BLUE);
        StdDraw.rectangle(WIDTH / 2, HEIGHT - (24 + OFFSET), 7, 1);

        StdDraw.show();
    }
    /** Displays user's input of seed. */
    public static void displaySeedInput(String input) {
        // Input text field.
        StdDraw.setPenColor(COOKIE_LT_BLUE);
        StdDraw.filledRectangle(WIDTH / 2, HEIGHT - (24 + OFFSET), 7, 1);
        StdDraw.setPenColor(32, 129, 196);
        StdDraw.setPenRadius(0.005);
        StdDraw.rectangle(WIDTH / 2, HEIGHT - (24 + OFFSET), 7, 1);

        // Display user input.
        StdDraw.setPenColor(Color.white);
        StdDraw.text(WIDTH / 2, HEIGHT - (24 + OFFSET), input);
        StdDraw.show();
    }

    public static void displayHud(String desc) {
        // Display the text
        StdDraw.disableDoubleBuffering();
        StdDraw.setPenColor(Color.white);
        StdDraw.textRight(WIDTH - 4.5, 2, desc);
        StdDraw.enableDoubleBuffering();

        // Hides the text
        StdDraw.setPenColor(Color.black);
        StdDraw.filledRectangle(WIDTH - 4.5, 2, 30, 2);

    }
    /** Display instruction legend on the footer. */
    public static void displayLegend() {
        StdDraw.setFont(SMALL);
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(Color.white);

        StdDraw.rectangle(5.5, 2, 0.75, 0.75);
        StdDraw.textLeft(5.13, 2, ":" + QUIT + "     Quit");

        StdDraw.rectangle(10.25, 2, 0.75, 0.75);
        StdDraw.textLeft(10, 2, UP + "      Up");

        StdDraw.rectangle(14.63, 2, 0.75, 0.75);
        StdDraw.textLeft(14.43, 2, DOWN + "      Down");

        StdDraw.rectangle(20, 2, 0.75, 0.75);
        StdDraw.textLeft(19.80, 2, LEFT + "      Left");

        StdDraw.rectangle(24.80, 2, 0.75, 0.75);
        StdDraw.textLeft(24.60, 2, RIGHT + "      Right");

        StdDraw.show();
    }
    /** Displays how many lives the user has. */
    public static void displayLife(int life) {
        double space = 2.25;
        double offsetY = 1.65;
        int offsetX = 0;
        for (int i = 0;  i < life && i < MAX_LIFE; i++) {
            double ht = HEIGHT - offsetY;
            offsetX += 1;
            // prints LIFE image on the rightmost first
            StdDraw.picture((WIDTH - 3) - (space * offsetX), ht, join(CWD, "life.png").toString());
        }
        StdDraw.show();
    }
    /** Displays user's current score. */
    public static void displayCalories(int calories) {
        StdDraw.setFont(MENU);

        StdDraw.setPenColor(Color.white);
        StdDraw.text(8, HEIGHT - 2, "Calories:  " + calories);
        StdDraw.setFont(NORMAL);

        StdDraw.show();
    }
    /** Displays the end of the game to the user. */
    public static void displayEndGame(String msg) {
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(HEADING);

        StdDraw.setPenColor(Color.white);
        StdDraw.text(WIDTH / 2, HEIGHT / 2 + 2, msg);
        if (msg.charAt(0) == 'C') {
            StdDraw.text(WIDTH / 2, HEIGHT / 2 - 3, "You found the Golden Cookie!");
        } else if (msg.charAt(0) == 'R') {
            StdDraw.text(WIDTH / 2, HEIGHT / 2 - 3, "Game over!");
        }
        StdDraw.show();
    }
    /** Displays instructions for the user to terminate
     * the program or play again. */
    public static void displayEndScreen() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(HEADING);

        StdDraw.setPenColor(Color.white);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "Play again? (Y/N)");

        StdDraw.show();
    }
}
