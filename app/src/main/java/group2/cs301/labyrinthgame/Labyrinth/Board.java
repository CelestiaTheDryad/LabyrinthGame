package group2.cs301.labyrinthgame.Labyrinth;

/**
 * Created by thomasb18 on 11/5/2015.
 */
public class Board {
    private Tile[][] gameTiles;
    private Tile extraTile;

    public Board() {
        gameTiles = new Tile[7][7];
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
    private void setRandom() {
        int linesToAdd = 0;
        int teesToAdd = 0;
        int cornersToAdd = 0;
    }

}
