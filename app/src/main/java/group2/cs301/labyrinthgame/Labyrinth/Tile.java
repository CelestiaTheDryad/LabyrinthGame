package group2.cs301.labyrinthgame.Labyrinth;

/**
 * Created by Brendan on 11/1/2015.
 */
public class Tile {
    //tile type variables
    public static final int TEE = 1000;
    public static final int CORNER = 1001;
    public static final int LINE = 1002;

    //tile rotations
    public static final int UP = 2000;
    public static final int RIGHT = 2001;
    public static final int DOWN = 2002;
    public static final int LEFT = 2003;

    private int type;
    private int rotation;

    private boolean[] playersPresent;

    private Tile tileUpwards = null;
    private Tile tileDownwards = null;
    private Tile tileRightwards = null;
    private Tile tileLeftWards = null;

    private int treasure;

    //creates a new tile with default values
    public Tile() {
        type = Tile.LINE;
        rotation = Tile.UP;
        playersPresent = new boolean[4];
        playersPresent[0] = false;
        playersPresent[1] = false;
        playersPresent[2] = false;
        playersPresent[3] = false;

        treasure = 0;
    }


    /**
     * Tile
     *
     * Creates a new tile with passed in properties
     *
     * @param initType      Type of tile: T, Corner, or Line
     * @param initRotation  The orientation of the tile
     * @param initP1        Tells whether player 1 is on the tile
     * @param initP2        Tells whether player 2 is on the tile
     * @param initP3        Tells whether player 3 is on the tile
     * @param initP4        Tells whether player 4 is on the tile
     * @param initTreasure  Tells which treasure is on the tile
     */
    public Tile(int initType, int initRotation, boolean initP1, boolean initP2,
                 boolean initP3, boolean initP4, int initTreasure) {
        type = initType;
        rotation = initRotation;
        playersPresent[0] = initP1;
        playersPresent[1] = initP2;
        playersPresent[2] = initP3;
        playersPresent[3] = initP4;

        treasure = initTreasure;
    }

    /**
     * Tile
     *
     * creates a copy of passed tile
     *
     * @param toCopy
     */
    public Tile(Tile toCopy) {
        type = toCopy.type;
        rotation = toCopy.rotation;

        playersPresent[0] = toCopy.playersPresent[0];
        playersPresent[1] = toCopy.playersPresent[1];
        playersPresent[2] = toCopy.playersPresent[2];
        playersPresent[3] = toCopy.playersPresent[3];

        treasure = toCopy.treasure;
    }

    /**
     * tickTile
     */
    public void tickTile() {
        rotation++;
        if(rotation == LEFT+1) {
            rotation = UP;
        }
    }//tickTile

}
