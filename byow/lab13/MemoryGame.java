package byow.lab13;

import edu.princeton.cs.introcs.StdDraw;
import byow.Core.RandomUtils;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

import static byow.Core.RandomUtils.uniform;

public class MemoryGame {
    /** The width of the window of this game. */
    private int width;
    /** The height of the window of this game. */
    private int height;
    /** The current round the user is on. */
    private int round;
    /** The Random object used to randomly generate Strings. */
    private Random rand;
    /** Whether or not the game is over. */
    private boolean gameOver;
    /** Whether or not it is the player's turn. Used in the last section of the
     * spec, 'Helpful UI'. */
    private boolean playerTurn;
    /** The characters we generate random Strings from. */
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    /** Encouraging phrases. Used in the last section of the spec, 'Helpful UI'. */
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        /**
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }
         */

        String arg = "93481373434434";
        long seed = Long.parseLong(arg);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        String letters = "";
        for (int i = 0; i < n; i++) {
            int index = uniform(rand, CHARACTERS.length);
            letters += CHARACTERS[index];
        }
        return letters;
    }

    //public void drawFrame(String s) {
    public void drawFrame() {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.disableDoubleBuffering();
        StdDraw.clear(Color.BLACK);

        StdDraw.enableDoubleBuffering();
        displayLeftHeader();
        displayEncourage();
        displayLine();

        reset();

    }
    private void displayLeftHeader() {
        /** Set header styling */
        int padding = 2;
        double leftPadding = padding;
        double topPadding = height - padding;

        Font font = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font);

        /** Display level of the game on the left side */
        StdDraw.textLeft(leftPadding, topPadding, "Round: " + round);

        reset();
    }
    private void displayEncourage() {
        int padding = 2;
        double rightPadding = width - padding;
        double topPadding = height - padding;

        Font font = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font);

        /** Display Encouragements on the right side */
        int index = uniform(rand, ENCOURAGEMENT.length);
        StdDraw.textRight(rightPadding, topPadding, ENCOURAGEMENT[index]);

        reset();
    }
    private void displayLine() {
        int padding = 2;
        double bottomPadding = height - (2 * padding);
        StdDraw.line(0, bottomPadding, width, bottomPadding);
        StdDraw.show();
        reset();
    }

    private void reset() {
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);

        StdDraw.setPenColor(Color.WHITE);
        StdDraw.enableDoubleBuffering();
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        String displayed = "";

        for (int i = 0; i < letters.length(); i++) {
            displayed = Character.toString(letters.charAt(i));
            drawFrame();
            displayInstruct("Watch");
            displayFlash(displayed);
        }
    }

    private void displayInstruct(String inst) {
        /** Set header styling */
        int padding = 2;
        double topPadding = height - padding;

        double centerX = .40 * width;
        Font font = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.CYAN);

        /** Display instructions at the center */
        StdDraw.disableDoubleBuffering();
        StdDraw.textLeft(centerX, topPadding, inst);

        reset();
    }
    private void displayFlash(String msg) {
        /** Sets the coordinate of text to be displayed */
        double centerX = 0.5 * width;
        double centerY = 0.5 * height;

        /** Displays the text */
        StdDraw.text(centerX, centerY, msg);
        StdDraw.show();

        /** This text flashes on the screen */
        StdDraw.disableDoubleBuffering();
        StdDraw.pause(1000);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledCircle(centerX, centerY, 6);
        StdDraw.pause(500);

        reset();
    }

    //public String solicitNCharsInput(int n) {
    public String solicitNCharsInput() {
        //TODO: Read n letters of player input
        String input = "";
        while(StdDraw.hasNextKeyTyped()) {
            char typed = StdDraw.nextKeyTyped();
            input += typed;
        }
        displayInput(input);
        return input;
    }

    private void displayInput(String s) {
        double centerX = 0.5 * width;
        double centerY = 0.5 * height;

        /** Displays the text */
        for (int i = 1; i <= s.length(); i++) {
            StdDraw.text(centerX, centerY, s.substring(0, i));
            StdDraw.show();
            StdDraw.clear(Color.BLACK);
            StdDraw.pause(100);
        }

        reset();
    }

    public void startGame() {
        //Set any relevant variables before the game starts
        round = 1;

        //Engine loop
        boolean cond = true;
        while (cond) {
            String randStr = generateRandomString(round);

            /** Display all data for the game */
            displayCountDown();
            displayRound();
            drawFrame();
            flashSequence(randStr);

            drawFrame();
            displayInstruct("Type");

            int time = ((1 + (1 / 5 * round)) * 1500);
            StdDraw.pause(time);
            reset();

            /** Process player's input */
            String playerInput = solicitNCharsInput();
            StdDraw.pause(500);

            if (!playerInput.equals(randStr)) {
                StdDraw.clear(Color.BLACK);
                displayGameOver(round);
                cond = false;
            }
            round++;
        }
    }
    private void displayRound() {
        double centerX = 0.5 * width;
        double centerY = 0.5 * height;

        /** Displays the text */
        StdDraw.clear(Color.BLACK);
        StdDraw.text(centerX, centerY, "Round: " + round);
        StdDraw.show();

        StdDraw.pause(800);

        reset();
    }

    private void displayCountDown() {
        /** Displays the text */
        if (round == 1) {
            for (int i = 3; i > 0; i--) {
                StdDraw.text( 0.5 * width, 0.8 * height, "Starts in");

                String countDown = Integer.toString(i);
                displayFlash(countDown);
            }
        }

        reset();
    }
    private void displayGameOver(int round) {
        String message = String.format ("Game Over! " +
                "You made it to round: %s", round);
        StdDraw.text(0.5 * width, 0.5 * height, message);
        StdDraw.show();

        reset();
    }



}
