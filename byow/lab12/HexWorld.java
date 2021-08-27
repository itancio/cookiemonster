package byow.lab12;
import edu.princeton.cs.algs4.StdRandom;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
//@Source RandomWorldDemo/BoringWorldDemo
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final Random RANDOM = new Random();

    public static void addHexagon(int s, int x1, int y1, TETile[][] world) {
        if (s == 2) {
            hexTwo(world, x1, y1);
        } else if (s == 3) {
            hexThree(world, x1, y1);
        } else if (s == 4) {
            hexFour(world, x1, y1);
        } else if (s == 5) {
            hexFive(world, x1,y1);
        }
    }

    private static void hexTwo(TETile[][] world, int x1, int y1) {
        TETile tile = randomTile();
        for (int x = x1; x < x1 + 4; x += 3) {
            for (int y = y1; y < y1 + 2; y += 1) {
                world[x][y] = tile;
            }
        }
        for (int x = x1 + 1; x < x1 + 3; x += 1) {
            for (int y = y1 - 1; y < y1 + 3; y += 1) {
                world[x][y] = tile;
            }
        }
    }
    private static void hexThree(TETile[][] world, int x1, int y1){
        TETile tile = randomTile();
        for (int x = x1; x < x1 + 7; x += 6) {
            for (int y = y1; y < y1 + 2; y += 1) {
                world[x][y] = tile;
            }
        }
        for (int x = x1 + 1; x < x1 + 6; x += 4) {
            for (int y = y1 - 1; y < y1 + 3; y += 1) {
                world[x][y] = tile;
            }
        }
        for (int x = x1 + 2; x < x1 + 5; x += 1) {
            for (int y = y1 - 2; y < y1 + 4; y += 1) {
                world[x][y] = tile;
            }
        }
    }
    private static void hexFour(TETile[][] world, int x1, int y1){
        TETile tile = randomTile();
        for (int x = x1; x < x1 + 10; x += 9) {
            for (int y = y1; y < y1 + 2; y += 1) {
                world[x][y] = tile;
            }
        }
        for (int x = x1 + 1; x < x1 + 9; x += 7) {
            for (int y = y1 - 1; y < y1 + 3; y += 1) {
                world[x][y] = tile;
            }
        }
        for (int x = x1 + 2; x < x1 + 8; x += 5) {
            for (int y = y1 - 2; y < y1 + 4; y += 1) {
                world[x][y] = tile;
            }
        }
        for (int x = x1 + 3; x < x1 + 7; x += 1) {
            for (int y = y1 - 3; y < y1 + 5; y += 1) {
                world[x][y] = tile;
            }
        }
    }
    private static void hexFive(TETile[][] world, int x1, int y1){
        TETile tile = randomTile();
        for (int x = x1; x < x1 + 13; x += 12) {
            for (int y = y1; y < y1 + 2; y += 1) {
                world[x][y] = tile;
            }
        }
        for (int x = x1 + 1; x < x1 + 12; x += 10) {
            for (int y = y1 - 1; y < y1 + 3; y += 1) {
                world[x][y] = tile;
            }
        }
        for (int x = x1 + 2; x < x1 + 11; x += 8) {
            for (int y = y1 - 2; y < y1 + 4; y += 1) {
                world[x][y] = tile;
            }
        }
        for (int x = x1 + 3; x < x1 + 10; x += 6) {
            for (int y = y1 - 3; y < y1 + 5; y += 1) {
                world[x][y] = tile;
            }
        }
        for (int x = x1 + 4; x < x1 + 9; x += 1) {
            for (int y = y1 - 4; y < y1 + 6; y += 1) {
                world[x][y] = tile;
            }
        }
    }
    private static void printAllHex(TETile[][] world){
        hexTwo(world, 5, 3);
        hexThree(world, 5,9);
        hexFour(world, 5,19);
        hexFive(world, 5,29);
    }
    private static void printOnlyHexTwo(TETile[][] world){
        for (int x = 15; x < 30; x += 12) {
            for (int y = 13; y < 24; y += 4) {
                hexTwo(world, x, y);
            }
        }
        for (int x = 18; x < 28; x += 6) {
            for (int y = 11; y < 24; y += 4) {
                hexTwo(world, x, y);
            }
        }
        for (int x = 21; x < 22; x += 1) {
            for (int y = 9; y < 28; y += 4) {
                hexTwo(world, x, y);
            }
        }
    }
    private static void printOnlyHexThree(TETile[][] world){
        for (int x = 15; x < 37; x += 20) {
            for (int y = 13; y < 31; y += 6) {
                hexThree(world, x, y);
            }
        }
        for (int x = 20; x < 31; x += 10) {
            for (int y = 10; y < 34; y += 6) {
                hexThree(world, x, y);
            }
        }
        for (int x = 25; x < 26; x += 1) {
            for (int y = 7; y < 37; y += 6) {
                hexThree(world, x, y);
            }
        }
    }
    private static void printOnlyHexFour(TETile[][] world){
        for (int x = 10; x < 39; x += 28) {
            for (int y = 13; y < 36; y += 8) {
                hexFour(world, x, y);
            }
        }
        for (int x = 17; x < 32; x += 14) {
            for (int y = 9; y < 34; y += 8) {
                hexFour(world, x, y);
            }
        }
        for (int x = 24; x < 25; x += 1) {
            for (int y = 5; y < 39; y += 8) {
                hexFour(world, x, y);
            }
        }
    }
    private static void printOnlyHexFive(TETile[][] world){
        for (int x = 5; x < 34; x += 28) {
            for (int y = 13; y < 36; y += 8) {
                hexFour(world, x, y);
            }
        }
        for (int x = 12; x < 27; x += 14) {
            for (int y = 9; y < 36; y += 8) {
                hexFour(world, x, y);
            }
        }
        for (int x = 19; x < 20; x += 1) {
            for (int y = 5; y < 42; y += 8) {
                hexFour(world, x, y);
            }
        }
    }

    /** Picks a RANDOM tile. */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(4);
        switch (tileNum) {
            case 0: return Tileset.SAND;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.TREE;
            case 3: return Tileset.MOUNTAIN;
            case 4: return Tileset.GRASS;
            default: return Tileset.NOTHING;
        }
    }

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        int random = StdRandom.uniform(0, 5);
        if (random == 0) {
            printAllHex(world);
        } else if (random == 1) {
            printOnlyHexTwo(world);
        } else if (random == 2) {
            printOnlyHexThree(world);
        } else if (random == 3) {
            printOnlyHexFour(world);
        } else if (random == 4) {
            printOnlyHexFive(world);
        }


        // draws the world to the screen
        ter.renderFrame(world);
    }
}
