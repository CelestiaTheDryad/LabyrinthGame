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

    private boolean player1present;
    private boolean player2present;
    private boolean player3present;
    private boolean player4present;

    private boolean highlighted;

    private Tile tileUpwards = null;
    private Tile tileDownwards = null;
    private Tile tileRightwards = null;
    private Tile tileLeftWards = null;

    private int treasure;

    //creates a new tile with default values
    public Tile() {
        type = Tile.LINE;
        rotation = Tile.UP;
        player1present = false;
        player2present = false;
        player3present = false;
        player4present = false;

        treasure = 0;
    }

    //creates a new tile with passed in properties
    public Tile(int initType, int initRotation, boolean initP1, boolean initP2,
                 boolean initP3, boolean initP4, int initTreasure) {
        type = initType;
        rotation = initRotation;
        player1present = initP1;
        player2present = initP2;
        player3present = initP3;
        player4present = initP4;

        treasure = initTreasure;
    }

    /**
     * Tile
     *
     * @param toCopy tile to be compied into a new tile
     *
     * constructor for the Tile class that creates a new tile identical to one passed in
     * creates a deep copy
     */
    public Tile(Tile toCopy) {
        type = toCopy.type;
        rotation = toCopy.rotation;

        player1present = toCopy.player1present;
        player2present = toCopy.player2present;
        player3present = toCopy.player3present;
        player4present = toCopy.player4present;

        treasure = toCopy.treasure;
    }

    /**
     * gatRotation
     *
     * @return int representing the rotation of the tile
     *
     * returns the rotation of the tile
     */
    public int getRotation() {
        return rotation;
    }

    /**
     * getType
     *
     * @return int representing the type of the tile
     *
     * returns the type of the tile
     */
    public int getType() {
        return type;
    }

    public void tickTile() {
        rotation++;
        if(rotation == LEFT+1) {
            rotation = UP;
        }
    }//tickTile

}
