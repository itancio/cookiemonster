package byow.Core;
import byow.InputDemo.InputSource;
import byow.InputDemo.StringInputDevice;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import java.util.Random;
import static byow.Core.RandomUtils.uniform;
import static byow.Core.Setting.*;

public class CookieMonsterGame {
    private int mapWidth;
    private int mapHeight;
    private TETile[][] world;
    private Random rand;
    private Coordinates avatar;
    private int avatarLife;
    private int calories;
    private int numOfFood;
    private String endMsg;
    private Boolean goldenCookieFound;

    //@Source HexWorld Live Code (Lab 12)
    public class Coordinates {
        private int x;
        private int y;

        Coordinates(int x1, int y1) {
            x = x1;
            y = y1;
        }
        /** This method receives two variables dx and dy.
         * It uses it to shift the current coordinate
         * in the dx and dy direction and  returns
         * the new shifted coordinate. */
        public Coordinates shift(int dx, int dy) {
            return new Coordinates(x + dx, y + dy);
        }
    }

    /** Constructor. */
    CookieMonsterGame(int width, int height, long seed) {
        mapWidth = width;
        mapHeight = height;
        rand = new Random(seed);
        numOfFood = uniform(rand, 50, 200);
        goldenCookieFound = false;
        avatarLife = INITIAL_LIFE;
        calories = INITIAL_CALORIES;
        // Generate map
        MapGenerator map = new MapGenerator(WIDTH, HEIGHT, seed);
        world = map.getWorld();

        placeFoodRandomly();
        placeAvatar();
        placeGoldenCookie();
    }
    /** This method will look for a place in the map
     * that has floor tiles to place the avatar. Once
     * the place for the avatar is found, the method
     * will save its coordinates to the avatar
     * variable. */
    private void placeAvatar() {
        Boolean foundPlace = false;
        while (!foundPlace) {
            int x = rand.nextInt(mapWidth / 2);
            int y = rand.nextInt(mapHeight / 2);
            if (world[x][y] == Tileset.FLOOR) {
                world[x][y] = Tileset.AVATAR;
                avatar = new Coordinates(x, y);
                foundPlace = true;
            }
        }
    }
    /** Finds empty places within rooms and hallways
     * to place food. */
    private void placeFoodRandomly() {
        int count = numOfFood;
        boolean foodPlaced = false;
        while (count > 0) {
            int x = rand.nextInt(mapWidth - 3);
            int y = rand.nextInt(mapHeight - 3);
            if (world[x][y] == Tileset.FLOOR) {
                world[x][y] = randomFood();
                foodPlaced = true;
            }
            if (foodPlaced) {
                count -= 1;
                foodPlaced = false;
            }
        }
    }
    /** Randomly selects which types of food are going to be
     * added to the game. */
    private TETile randomFood() {
        int tileNum = rand.nextInt(11);
        switch (tileNum) {
            case 0: return Tileset.CAKE;
            case 1: return Tileset.COOKIE;
            case 2: return Tileset.COOKIES;
            case 3: return Tileset.BISCUIT;
            case 4: return Tileset.BROCCOLI;
            case 5: return Tileset.CARROT;
            case 6: return Tileset.PIZZA;
            case 7: return Tileset.BURGER;
            case 8: return Tileset.KFC;
            case 9: return Tileset.FRIES;
            case 10: return Tileset.HOT_DOG;
            default: return Tileset.COOKIE;
        }
    }
    /** Finds an empty place within rooms and hallways
     * to place the golden cookie. */
    private void placeGoldenCookie() {
        int count = 1;
        boolean cookiePlaced = false;
        while (count > 0) {
            int x = rand.nextInt(mapWidth - 3);
            int y = rand.nextInt(mapHeight - 3);
            int dist = distanceBetweenTwoPoints(new Coordinates(x, y), avatar);
            if (world[x][y] == Tileset.FLOOR && dist > 25) {
                world[x][y] = randomCookie();
                cookiePlaced = true;
            }
            if (cookiePlaced) {
                count -= 1;
                cookiePlaced = false;
            }
        }
    }
    /** Calculate distance between two points using the
     * distance formula: squareRoot((x1-x)ˆ2 + (y1-y)ˆ2). */
    private int distanceBetweenTwoPoints(Coordinates p1, Coordinates p2) {
        int distSquareX =  (int) Math.pow(distance(p1.x, p2.x), 2);
        int distSquareY = (int) Math.pow(distance(p1.y, p2.y), 2);

        return (int) Math.sqrt(distSquareX + distSquareY);
    }
    /** Calculates the distance between two numbers. */
    private int distance(int num1, int num2) {
        return Math.abs(num1 - num2);
    }

    /** Randomly selects a golden cookie. */
    private TETile randomCookie() {
        int tileNum = rand.nextInt(2);
        switch (tileNum) {
            case 0: return Tileset.GOLDEN_COOKIE;
            case 1: return Tileset.GOLDEN_COOKIES;
            default: return Tileset.GOLDEN_COOKIE;
        }
    }
    /** Receives a string as command and moves the
     * avatar coordinates accordingly. */
    public Boolean moveAvatar(String commands) {
        InputSource input = new StringInputDevice(commands);
        while (input.possibleNextInput()) {
            char direction = input.getNextKey();

            if (direction == UP) {
                moveAvatarIfValid(0, 1);
            } else if (direction == DOWN) {
                moveAvatarIfValid(0, -1);
            } else if (direction == LEFT) {
                moveAvatarIfValid(-1, 0);
            } else if (direction == RIGHT) {
                moveAvatarIfValid(1, 0);
            }
            // Check if the game has to end
            if (endGame()) {
                return true;
            }
        }
        return false;
    }
    /** Check if the position the avatar needs to be
     * moved is valid. If it is, it moves the avatar
     * to that position and changes the avatar's
     * previous position to floor tile. */
    private void moveAvatarIfValid(int x, int y) {
        TETile nextMoveTile = world[avatar.x + x][avatar.y + y];
        if (nextMoveTile != Tileset.WALL) {
            updateScoreAndLife(nextMoveTile);
            // Change previous avatar position red floor
            world[avatar.x][avatar.y] = Tileset.RED_FLOOR;
            // Change avatar on the map
            world[avatar.x + x][avatar.y + y] = Tileset.AVATAR;
            avatar = avatar.shift(x, y);
            // Decrease calories
            calories -= 25;
        }
    }
    /** Updates the score if nextMoveTile equals to
     * a type of baked treats or fast food. Increase
     * life if nextMoveTile is a vegetable and decrease
     * a life if nextMoveTile has been walked by already
     * (If floor is red). */
    private void updateScoreAndLife(TETile nextMoveTile) {
        // If baked treats + 150 calories -1 food
        if (nextMoveTile.character() == 'b') {
            calories += 150;
            numOfFood -= 1;
        // If fast food + 250 calories -1 food
        } else if (nextMoveTile.character() == 'f') {
            calories += 250;
            numOfFood -= 1;
        // If vegetables  + 50 calories + 1 s2 -1 food
        } else if (nextMoveTile.character() == 'v') {
            calories += 50;
            avatarLife += 1;
            numOfFood -= 1;
        // If golden cookie user wins
        } else if (nextMoveTile.character() == 'g') {
            goldenCookieFound = true;
        // If previously walked path
        } else if (nextMoveTile == Tileset.RED_FLOOR) {
            avatarLife -= 1;
        }
    }
    /** The game will end if the cookie monster has no
     * more lives or the cookie moster ate all the food. */
    private Boolean endGame() {
        if (goldenCookieFound || numOfFood == 0) {
            setEndMsg(0);
            return true;
        } else if (avatarLife == 0) {
            setEndMsg(1);
            return true;
        } else if (calories <= 0) {
            setEndMsg(2);
            return true;
        }
        return false;
    }
    /** Sets the endMsg. To let the user know if they won
     * or lost the game. */
    private void setEndMsg(int num) {
        if (num == 0) {
            endMsg = "Congratulations!!";
        } else if (num == 1) {
            endMsg = "Ran out of lives!!";
        } else if (num == 2) {
            endMsg = "Ran out of calories!!";
        }
    }
    /** Current map. */
    public TETile[][] getWorld() {
        return world;
    }
    /** Returns the final game msg. */
    public String getEndMsg() {
        return endMsg;
    }
    /** Returns the current score. */
    public int getCalories() {
        return calories;
    }
    /** Returns the current life. */
    public int getAvatarLife() {
        return avatarLife;
    }
}
