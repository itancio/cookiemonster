package byow.Core;
import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/** This class contains all the default settings and constant variables for the game */
public class Setting {

    // Default command keys
    /** Creates a new game. */
    public static final char NEW_GAME = 'n';
    /** Uploads a previously saved game. */
    public static final char LOAD = 'l';
    /** Marks the end of the seed. */
    public static final char END_OF_SEED = 's';
    /** Ends the game and save current state. */
    public static final char QUIT = 'q';
    /** Replay last played game. */
    public static final char REPLAY = 'r';
    public static final Set<Character> VALID_OPTIONS =
            new HashSet<>(Arrays.asList(NEW_GAME, LOAD, QUIT, REPLAY));

    // Direction command keys
    public static final char UP = 'w';
    public static final char DOWN = 's';
    public static final char LEFT = 'a';
    public static final char RIGHT = 'd';
    public static final Set<Character> VALID_COMMANDS =
            new HashSet<>(Arrays.asList(UP, DOWN, LEFT, RIGHT));

    // Default start values
    public static final int INITIAL_CALORIES = 1000;
    public static final int INITIAL_LIFE = 5;
    public static final int MAX_LIFE = 25;

    // Default Map Dimensions
    public static final int WIDTH = 80;
    public static final int HEIGHT = 50;

    // Default Stylesheet
    public static final String FONT_STYLE = "Arial";
    public static final Font HEADING = new Font(FONT_STYLE, Font.BOLD, 50);
    public static final Font MENU = new Font(FONT_STYLE, Font.BOLD, 30);
    public static final Font NORMAL = new Font(FONT_STYLE, Font.PLAIN, 16);
    public static final Font SMALL = new Font(FONT_STYLE, Font.BOLD, 12);
    public static final Font ITALIC = new Font(FONT_STYLE, Font.ITALIC, 16);

    // Default colors
    public static final Color COOKIE_LT_BLUE = new Color(134, 195, 232);
    public static final Color COOKIE_BLUE = new Color(32, 129, 196);

    // Working directory
    public static final File CWD = new File(System.getProperty("user.dir"));
}
