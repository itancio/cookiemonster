package byow.Core;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.*;

import static byow.Core.RandomUtils.uniform;

/** This class pseudorandomly generates a map with rooms and hallways based
 * on the seed, width and length received by the constructor. */
public class MapGenerator {
    private int mapWidth;
    private int mapHeight;
    private static final int MIN_ROOM_SIZE = 6;
    private static final int MAX_ROOM_SIZE = 18;
    private Random rand;
    private TETile[][] world;
    private Room previousRoom;
    private Room currentRoom;
    private Boolean roomDrawn;
    private int numOfRooms;

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

    private class Room {
        int height;
        int length;
        Coordinates coordinates;
        Coordinates entrance;
        String entranceDirection;
        HashMap<String, Coordinates> sides = new HashMap<>();

        Room(Coordinates coord, int h, int l) {
            coordinates = new Coordinates(coord.x, coord.y);
            height = h;
            length = l;
            // North
            sides.put("North", new Coordinates(coord.x + length / 2, coord.y + height));
            // South
            sides.put("South", new Coordinates(coord.x + length / 2, coord.y));
            // West
            sides.put("West", new Coordinates(coord.x, coord.y + height / 2));
            // East
            sides.put("East", new Coordinates(coord.x + length, coord.y + height / 2));
        }
    }

    /** Constructor. */
    MapGenerator(int w, int h, long s) {
        mapWidth = w;
        mapHeight = h;
        rand = new Random(s);
        world = new TETile[w][h];
        numOfRooms = randNumber(20, 30);
        //numOfRooms = 3;
        roomDrawn = false;
        int x = randNumber(0, mapWidth - MAX_ROOM_SIZE);
        int y = randNumber(0, mapHeight - MAX_ROOM_SIZE);
        currentRoom = new Room(new Coordinates(x, y), 0, 0);
        fillMap();
    }
    /** Receives a min and max value and returns
     * a random integer uniformly in [min, max)*/
    private int randNumber(int min, int max) {
        return uniform(rand, min, max);
    }

    /** This method draws rooms and hallways. It only draws
     * a hallway if the map has at least two rooms. */
    public void fillMap() {
        nothingMap();
        while (numOfRooms > 0) {
            drawRoom();
            if (previousRoom != null && roomDrawn) {
                drawHallway();
            }
            previousRoom = new Room(currentRoom.coordinates,
                    currentRoom.height, currentRoom.length);
            roomDrawn = false;
            numOfRooms -= 1;
        }
    }
    /** Initializes the map with nothing. */
    public void nothingMap() {
        for (int x = 0; x < mapWidth; x += 1) {
            for (int y = 0; y < mapHeight; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }
    /** Creates and returns a random coordinate. */
    private Coordinates setRandomCoordinates() {
        int x = rand.nextInt(mapWidth);
        int y = rand.nextInt(mapHeight);

        return new Coordinates(x, y);
    }
    /** This method draws a random sized room to the map.
     * The room can have square or rectangle shape. */
    public void drawRoom() {
        boolean validRoom = false;
        Coordinates room = new Coordinates(0, 0);
        int roomLength = 0;
        int roomHeight = 0;
        int count = 0;
        // Tries to find a valid space for the room 10 times
        while (!validRoom && count < 10) {
            room = setRandomCoordinates();
            roomLength = randomSizeOfRoom() - 1;
            roomHeight = randomSizeOfRoom() - 1;
            validRoom = validateCurrentRoom(room, roomLength, roomHeight);
            count++;
        }
        if (validRoom) {
            // Fill top row
            fillRow(room.shift(0, roomHeight), Tileset.WALL, roomLength + 1);
            // Fill bottom row
            fillRow(room, Tileset.WALL, roomLength);
            // Fill right column
            fillColumn(room.shift(roomLength, 0), Tileset.WALL, roomHeight);
            // Fill left column
            fillColumn(room.shift(0, 1), Tileset.WALL, roomHeight - 1);

            int i = 1;
            // Fill middle
            while (i <= roomLength - 1) {
                fillColumn(room.shift(i, 1), Tileset.FLOOR, roomHeight - 1);
                i += 1;
            }
            currentRoom = new Room(room, roomHeight, roomLength);
            roomDrawn = true;
        }
    }
    /** Picks a random sizes for a room. */
    private int randomSizeOfRoom() {
        int size = rand.nextInt(MAX_ROOM_SIZE);
        if (size < MIN_ROOM_SIZE) {
            size = MIN_ROOM_SIZE;
        }
        return size;
    }
    /** Checks if there is enough space to draw the room
     * on the map with them given room coordinates and size. */
    private boolean validateCurrentRoom(Coordinates room, int roomLength, int roomHeight) {
        if (room.x + roomLength >= mapWidth - 3 || room.x <= 3
                || room.y + roomHeight >= mapHeight - 3 || room.y <= 3) {
            return false;
        }
        for (int x = room.x; x <= room.x + roomLength; x++) {
            for (int y = room.y; y <= room.y + roomHeight; y++) {
                if (world[x][y] != Tileset.NOTHING) {
                    return false;
                }
            }
        }
        return true;
    }
    /** Fills a row with the specified coordinates,
     * tile type and length. If we reach the end of
     * the map, this method will fill the tile as a
     * wall. If the coordinates already have floor
     * tiles, this method does not override it. */
    private void fillRow(Coordinates coord, TETile tile, int len) {
        if (coord.y >= mapHeight) {
            coord.y = mapHeight - 1;
        }
        for (int x = coord.x; x < coord.x + len && x < mapWidth; x++) {
            if (x == mapWidth - 1 || coord.y == mapHeight - 1) {
                world[x][coord.y] = Tileset.WALL;
            } else if (world[x][coord.y].equals(Tileset.FLOOR)) {
                continue;
            } else {
                world[x][coord.y] = tile;
            }
        }
    }
    /** Fills a column with the specified coordinates,
     * tile type and length. If we reach the end of
     * the map, this method will fill the tile as a
     * wall. If the coordinates already have floor
     * tiles, this method does not override it. */
    private void fillColumn(Coordinates coord, TETile tile, int h) {
        if (coord.x >= mapWidth) {
            coord.x = mapWidth - 1;
        }
        for (int y = coord.y; y < coord.y + h && y < mapHeight; y++) {
            if (y == mapHeight - 1 || coord.x == mapWidth - 1) {
                world[coord.x][y] = Tileset.WALL;
            } else if (world[coord.x][y].equals(Tileset.FLOOR)) {
                continue;
            } else {
                world[coord.x][y] = tile;
            }
        }
    }
    /** Draws a hallway horizontally and vertically. */
    public void drawHallway() {
        findBestCoordinateForHallway();
        // Simplifying room position
        Room left = previousRoom;
        Room right = currentRoom;
        if (left.entrance.x >= right.entrance.x) {
            left = currentRoom;
            right = previousRoom;
        }
        drawHorizontalHallway(left, right);
        drawVerticalHallway(left, right);
    }
    /** Finds the best coordinates to create the shortest
     * L shaped hallway between two rooms. It sets each room
     * entrance coordinate and direction. */
    private void findBestCoordinateForHallway() {
        HashMap<String, Coordinates> prevRoom = previousRoom.sides;
        HashMap<String, Coordinates> currRoom = currentRoom.sides;
        Coordinates prevRoomEntrance = new Coordinates(0, 0);
        Coordinates currRoomEntrance = new Coordinates(0, 0);
        String prevRoomSide = "";
        String currtRoomSide = "";
        int shortestDistance = Integer.MAX_VALUE;

        //@Source:
        // https://www.programiz.com/java-programming/examples/get-key-from-hashmap-using-value
        for (Map.Entry<String, Coordinates> room1 : prevRoom.entrySet()) {
            for (Map.Entry<String, Coordinates> room2 : currRoom.entrySet()) {
                // Creating conditions so we only have L shaped hallways
                if (room1.getKey().equals("North") && room2.getKey().equals("South")) {
                    continue;
                } else if (room1.getKey().equals("South") && room2.getKey().equals("North")) {
                    continue;
                } else if (room1.getKey().equals("West") && room2.getKey().equals("East")) {
                    continue;
                } else if (room1.getKey().equals("East") && room2.getKey().equals("West")) {
                    continue;
                } else {
                    int distance = distanceBetweenTwoPoints(room1.getValue(), room2.getValue());
                    if (shortestDistance > distance) {
                        shortestDistance = distance;
                        prevRoomEntrance = room1.getValue();
                        prevRoomSide = room1.getKey();
                        currRoomEntrance = room2.getValue();
                        currtRoomSide = room2.getKey();
                    }
                }
            }
        }
        // Save each room entrance info
        previousRoom.entrance = prevRoomEntrance;
        previousRoom.entranceDirection = prevRoomSide;
        currentRoom.entrance = currRoomEntrance;
        currentRoom.entranceDirection = currtRoomSide;
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

    /** This method draws a horizontal hallway starting at the
     * left room on the map. It has height 3 (1 tile for flooring
     * and 2 tiles for walls) length equal to the hallLength. */
    private void drawHorizontalHallway(Room leftRoom, Room rightRoom) {
        Coordinates hallway = horizontalCoordinates(leftRoom, rightRoom);
        int hallLength = distance(previousRoom.entrance.x, currentRoom.entrance.x) + 3;
        // Top
        fillRow(hallway.shift(0, 2), Tileset.WALL, hallLength);
        // Middle
        fillRow(hallway.shift(0, 1), Tileset.FLOOR, hallLength - 1);
        // Bottom
        fillRow(hallway, Tileset.WALL, hallLength);
        // Left end column
        if (hallway.x > 0 && hallway.y < mapHeight - 1
                && world[hallway.x - 1][hallway.y + 1] == Tileset.NOTHING) {
            world[hallway.x][hallway.y + 1] = Tileset.WALL;
        }
        // Right end column
        fillColumn(hallway.shift(hallLength - 1, 0), Tileset.WALL, 3);
    }
    /** Receives two rooms on the map and returns a coordinate that
     * allows the hallway to be drawn from left to right. */
    private Coordinates horizontalCoordinates(Room left, Room right) {
        int x = Math.min(left.entrance.x, right.entrance.x);
        int y;

        if (right.entranceDirection.equals("South")
                || right.entranceDirection.equals("North")) {
            y = left.entrance.y;
        } else {
            y = right.entrance.y;
        }

        return new Coordinates(x, y);
    }
    /** This method draws a vertical hallway starting at the
     * lowest y-coordinate to the highest y-coordinate. It
     * has length equal to hallLength and width 3 (1 tile for
     * flooring and 2 tiles for walls). */
    private void drawVerticalHallway(Room leftRoom, Room rightRoom) {
        Coordinates hallway = verticalCoordinates(leftRoom, rightRoom);
        int hallLength = distance(previousRoom.entrance.y, currentRoom.entrance.y) + 1;
        // Left
        fillColumn(hallway, Tileset.WALL, hallLength);
        // Middle
        fillColumn(hallway.shift(1, 0), Tileset.FLOOR, hallLength);
        // Right
        fillColumn(hallway.shift(2, 0), Tileset.WALL, hallLength);
        //Top
        fillRow(hallway.shift(0, hallLength), Tileset.WALL, 3);
        // Bottom
        if (hallway.y > 0 && world[hallway.x + 1][hallway.y - 1].equals(Tileset.NOTHING)) {
            world[hallway.x + 1][hallway.y] = Tileset.WALL;
        }
    }
    /** Receives two rooms on the map and returns a coordinate
     * that allows the hallway to be drawn from the lowest
     * y-coordinate to the highest y-coordinate. */
    private Coordinates verticalCoordinates(Room left, Room right) {
        int x;
        int y = Math.min(left.entrance.y, right.entrance.y);

        if (right.entranceDirection.equals("South")
                || right.entranceDirection.equals("North")) {
            x = right.entrance.x;
        } else {
            x = left.entrance.x;
        }

        return new Coordinates(x, y);
    }
    /** Returns the generated map. */
    public TETile[][] getWorld() {
        return world;
    }
}
