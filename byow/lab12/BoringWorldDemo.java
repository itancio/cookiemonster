package byow.lab12;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

/**
 *  Draws a world that is mostly empty except for a small region.
 */
public class BoringWorldDemo {

    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.FLOWER;
            }
        }

        // fills in a block 14 tiles wide by 4 tiles tall
        for (int x = 10; x < 20; x += 9) {
            for (int y = 10; y < 12; y += 1) {
                world[x][y] = Tileset.WATER;
            }
        }
        for (int x = 11; x < 19; x += 7) {
            for (int y = 9; y < 13; y += 1) {
                world[x][y] = Tileset.WATER;
            }
        }
        for (int x = 12; x < 18; x += 5) {
            for (int y = 8; y < 14; y += 1) {
                world[x][y] = Tileset.WATER;
            }
        }
        for (int x = 13; x < 17; x += 1) {
            for (int y = 7; y < 15; y += 1) {
                world[x][y] = Tileset.WATER;
            }
        }

        // draws the world to the screen
        ter.renderFrame(world);
    }


}
