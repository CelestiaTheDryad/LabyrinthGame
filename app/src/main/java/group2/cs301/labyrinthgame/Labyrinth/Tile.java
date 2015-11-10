package group2.cs301.labyrinthgame.Labyrinth;

/**
 * @author Brendan Thomas, Ben Rumptz
 * @version November 8, 2015
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

    /**
     * Tile
     *
     * creates a new tile with some default values
     * this constructor should not be used in the actual running of the game
     */
    public Tile() {
        type = Tile.LINE;
        rotation = Tile.UP;
        playersPresent = new boolean[4];
        playersPresent[0] = false;
        playersPresent[1] = false;
        playersPresent[2] = false;
        playersPresent[3] = false;

        treasure = 0;
    }//ctor


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
        playersPresent = new boolean[4];
        playersPresent[0] = initP1;
        playersPresent[1] = initP2;
        playersPresent[2] = initP3;
        playersPresent[3] = initP4;

        treasure = initTreasure;
    }//ctor

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

        playersPresent = new boolean[4];
        playersPresent[0] = toCopy.playersPresent[0];
        playersPresent[1] = toCopy.playersPresent[1];
        playersPresent[2] = toCopy.playersPresent[2];
        playersPresent[3] = toCopy.playersPresent[3];

        treasure = toCopy.treasure;
    }//ctor

    /**
     * tickTile
     *
     * rotates clockwise once
     */
    public void tickTile() {
        rotation++;
        if(rotation == LEFT+1) {
            rotation = UP;
        }
    }//tickTile


    /**
     * setPlayersPresent - sets the given player at this tile
     * @param curPlayer    the given player
     * @param isPresent    true if player is present
     */
    public void setPlayer(int curPlayer, boolean isPresent) {
        playersPresent[curPlayer] = isPresent;
    }//setPlayersPresent


    /**
     * getPlayerPresent - returns whether the given player is on this Tile
     * @param curPlayer     the current player
     * @return              true if the given player is present
     */
    public boolean getPlayer(int curPlayer) {
        return playersPresent[curPlayer];
    }

    /**
     * getRotation
     *
     * get method for the tile's rotation
     *
     * @return int of tile's rotation
     */
    public int getRotation() {return rotation;}//getRotation

    /**
     * getTreasure
     *
     * get method for the tile's treasure
     * @return int of the tile's treasure
     */
    public int getTreasure() {return treasure;}//getTreasure

    /**
     * getType
     *
     * get method for the tile's type
     * @return int of the tile's type
     */
    public int getType() {return type;}//getType

    /**
     * setTileUpwards
     *
     * sets the tileUpwards link to a passed in tile (may be set to null)
     *
     * @param toSet tile to set the link to
     */
    public void setTileUpwards(Tile toSet) {tileUpwards = toSet;}//setUpwardsTile

    /**
     * setTileRightwards
     *
     * sets the tileRightwards link to a passed in tile (may be set to null)
     *
     * @param toSet tile to set the link to
     */
    public void setTileRightwards(Tile toSet) {tileRightwards = toSet;}//setRightwardsTile

    /**
     * setTileDownwards
     *
     * sets the tileDownwards link to a passed in tile (may be set to null)
     *
     * @param toSet tile to set the link to
     */
    public void setTileDownwards(Tile toSet) {tileDownwards = toSet;}//setDownwardsTile

    /**
     * setTileLeftwards
     *
     * sets the tileLeftwards link to a passed in tile (may be set to null)
     *
     * @param toSet tile to set the link to
     */
    public void setTileLeftWards(Tile toSet) {tileLeftWards = toSet;}//setLeftwardsTile

    /**
     * isConnected
     *
     * tells whether the tile has a connection to a given direction or not
     *
     * @param direction integer representing the direction to analyze, must be one of Tile
     * @return - true if the tile has a connection in that direction, false if otherwise
     */
    public boolean isConnected(int direction) {
        if(direction == Tile.RIGHT) {
            //handle tile links according to tile type
            if (type == Tile.CORNER || type == Tile.TEE) {
                if (rotation == Tile.UP || rotation == Tile.RIGHT) {
                    return true;
                }
            }

            if (type == Tile.TEE) {
                if(rotation == Tile.DOWN) {
                    return true;
                }
            }

            if (type == Tile.LINE && (rotation == Tile.RIGHT || rotation == Tile.LEFT)) {
                return true;
            }
        }

        else if(direction == Tile.DOWN) {
            if(type == Tile.CORNER || type == Tile.TEE) {
                if(rotation == Tile.RIGHT || rotation == Tile.DOWN) {
                    return true;
                }
            }

            if(type == Tile.TEE) {
                if(rotation == Tile.LEFT) {
                    return true;
                }
            }

            if(type == Tile.LINE) {
                if(rotation == Tile.UP || rotation == Tile.DOWN) {
                    return true;
                }
            }
        }

        else if(direction == Tile.LEFT) {
            if(type == Tile.CORNER || type == Tile.TEE) {
                if(rotation == Tile.DOWN || rotation == Tile.LEFT) {
                    return true;
                }
            }

            if(type == Tile.TEE) {
                if(rotation == Tile.UP) {
                    return true;
                }
            }

            if(type == Tile.LINE) {
                if(rotation == Tile.LEFT || rotation == Tile.RIGHT) {
                    return true;
                }
            }
        }

        else if(direction == Tile.UP) {
            if(type == Tile.CORNER || type == Tile.TEE) {
                if(rotation == Tile.UP || rotation == Tile.LEFT) {
                    return true;
                }
            }

            if(type == Tile.TEE) {
                if(rotation == Tile.RIGHT) {
                    return true;
                }
            }

            if(type == Tile.LINE) {
                if(rotation == Tile.UP || rotation == Tile.DOWN) {
                    return true;
                }
            }
        }
        return false;
    }//isConnected

    /**
     * getTileConnection
     *
     * gets the tile connected to this tile in a given direction
     *
     * @param direction direction to get the connected tile
     * @return the tile's connection in that direction, null if the tile is not connected in that direction
     */
    public Tile getTileConnection(int direction) {
        return null;
    }//getTileConnection

    public boolean[] getPlayersPresent(){ return playersPresent; }
    public void setPlayersPresent(boolean[] pp){ this.playersPresent = pp; }
}
