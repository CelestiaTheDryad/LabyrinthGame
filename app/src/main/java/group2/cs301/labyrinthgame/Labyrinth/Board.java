package group2.cs301.labyrinthgame.Labyrinth;

import group2.cs301.labyrinthgame.R;

/**
 * Created by thomasb18 on 11/5/2015.
 */
public class Board {
    private Tile[][] gameTiles;
    private Tile extraTile;

    public Board() {
        gameTiles = new Tile[7][7];
        setNew();
    }

    //creates a deep copy of a passed Board object
    public Board(Board toCopy) {
        gameTiles = new Tile[7][7];
        for(int i = 0; i < 7; i++) {
            for(int j = 0; j < 7; j++) {
                gameTiles[i][j] = new Tile(toCopy.gameTiles[i][j]);
            }
        }
        extraTile = toCopy.extraTile;
    }

    //sets the board to a random state
    private void setNew() {
        int linesToAdd = 13;
        int teesToAdd = 6;
        int cornersToAdd = 15;

        gameTiles[0][0] = new Tile(Tile.CORNER, Tile.RIGHT, true, false, false, false, 0);
        gameTiles[2][0] = new Tile(Tile.TEE, Tile.DOWN, false, false, false ,false, 0);
        gameTiles[4][0] = new Tile(Tile.TEE, Tile.DOWN, false, false,false,false, 0);
        gameTiles[6][0] = new Tile(Tile.CORNER, Tile.DOWN, false, true, false ,false , 0);
        gameTiles[0][2] = new Tile(Tile.TEE, Tile.RIGHT, false, false, false, false, 0);
        gameTiles[2][2] = new Tile(Tile.TEE, Tile.RIGHT, false, false, false, false, 0);

    }

}
