package byow.InputDemo;

/**
 * Created by hug.
 */
import edu.princeton.cs.introcs.StdDraw;

import byow.TileEngine.TETile;

import java.awt.*;

import static byow.Core.Display.displayHud;
import static byow.Core.Setting.*;

public class KeyboardInputSource implements InputSource {
    private static final boolean PRINT_TYPED_KEYS = false;
    TETile[][] world;
    public KeyboardInputSource() {
    }
    public KeyboardInputSource(TETile[][] world) {
        this.world = world;
    }

    public char getNextKey() {
        while (true) {
            display();

            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toLowerCase(StdDraw.nextKeyTyped());
                if (PRINT_TYPED_KEYS) {
                    System.out.print(c);
                }
                return c;
            }
        }
    }

    public boolean possibleNextInput() { return true; }

    private void display() {
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        try {
            if (world != null) {
                String desc = world[x][y].description();
                displayHud(desc);

            }
        } catch(ArrayIndexOutOfBoundsException ignore) {
            // Do nothing
        }

    }
}
