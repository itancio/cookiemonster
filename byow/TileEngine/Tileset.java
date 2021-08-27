package byow.TileEngine;
import java.awt.Color;
import java.io.File;
import java.nio.file.Paths;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    public static final File CWD = new File(System.getProperty("user.dir"));
    public static final String COOKIE_MONSTER = "Cookie monster ";
    public static final String EAT = "Eat it and earn ";
    public static final String EXTRA_LIFE = " is good for you! Eat it and earn an extra life!!";
    public static final String BAKED_GOOD_CALS = "150 cals!!";
    public static final String FAST_FOOD_CALS = "250 cals!!";

    // Cookie monster avatar
    public static final TETile AVATAR = new TETile('@', Color.white, Color.black, "You",
            join(CWD, "cookie-monster.png").toString());
    // Baked goods
    public static final TETile COOKIE = new TETile('b', Color.white, Color.black,
            COOKIE_MONSTER + "LOVES cookies!! " + EAT + BAKED_GOOD_CALS,
            join(CWD, "cookie.png").toString());
    public static final TETile COOKIES = new TETile('b', Color.white, Color.black,
            COOKIE_MONSTER + "LOVES cookies!! " + EAT + BAKED_GOOD_CALS,
            join(CWD, "cookies.png").toString());
    public static final TETile BISCUIT = new TETile('b', Color.white, Color.black,
            COOKIE_MONSTER + "LOVES biscuits!! " + EAT + BAKED_GOOD_CALS,
            join(CWD, "cookie2.png").toString());
    public static final TETile CAKE = new TETile('b', Color.white, Color.black,
            COOKIE_MONSTER + "LOVES cakes!! " + EAT + BAKED_GOOD_CALS,
            join(CWD, "cake.png").toString());
    // Vegetables
    public static final TETile BROCCOLI = new TETile('v', Color.white, Color.black,
            "Broccoli" + EXTRA_LIFE,
            join(CWD, "broccoli.png").toString());
    public static final TETile CARROT = new TETile('v', Color.white, Color.black,
            "Carrot" + EXTRA_LIFE,
            join(CWD, "carrot.png").toString());
    // Fast food
    public static final TETile PIZZA = new TETile('f', Color.white, Color.black,
            COOKIE_MONSTER + "likes pizza!! " + EAT + FAST_FOOD_CALS,
            join(CWD, "pizza.png").toString());
    public static final TETile BURGER = new TETile('f', Color.white, Color.black,
            COOKIE_MONSTER + "likes burguers!! " + EAT + FAST_FOOD_CALS,
            join(CWD, "burger.png").toString());
    public static final TETile KFC = new TETile('f', Color.white, Color.black,
            COOKIE_MONSTER + "likes KFC chicken!! " + EAT + FAST_FOOD_CALS,
            join(CWD, "kfc.png").toString());
    public static final TETile FRIES = new TETile('f', Color.white, Color.black,
            COOKIE_MONSTER + "likes french fries!! " + EAT + FAST_FOOD_CALS,
            join(CWD, "fries.png").toString());
    public static final TETile HOT_DOG = new TETile('f', Color.white, Color.black,
            COOKIE_MONSTER + "likes hot dogs!! " + EAT + FAST_FOOD_CALS,
            join(CWD, "hot-dog.png").toString());
    // Winning cookies
    public static final TETile GOLDEN_COOKIE = new TETile('g', Color.white, Color.black,
            COOKIE_MONSTER + "LOVES cookies!! " + EAT + BAKED_GOOD_CALS,
            join(CWD, "cookie.png").toString());
    public static final TETile GOLDEN_COOKIES = new TETile('g', Color.white, Color.black,
            COOKIE_MONSTER + "LOVES cookies!! " + EAT + BAKED_GOOD_CALS,
            join(CWD, "cookies.png").toString());
    // Tiles walked by avatar
    public static final TETile RED_FLOOR = new TETile('·', Color.red, Color.black,
            "Don't go back to a previously walked path!! You will lose a life!");
    // Previous tiles
    public static final TETile WALL = new TETile('#', Color.darkGray, Color.darkGray,
            "Wall");
    public static final TETile FLOOR = new TETile('·', Color.white, Color.black,
            "Floor");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "Nothing");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "Grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "Water");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "Flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "Locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "Unlocked door");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "Sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "Mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "Tree");

    //@Source: project2 - gitlet.Utils. Written by: P. N. Hilfinger.
    /** Return the concatentation of FIRST and OTHERS into a File designator,
     *  analogous to the {"@link java.nio.file.Paths.get(String, String[])"}
     *  method. */
    static File join(File first, String... others) {
        return Paths.get(first.getPath(), others).toFile();
    }
}


