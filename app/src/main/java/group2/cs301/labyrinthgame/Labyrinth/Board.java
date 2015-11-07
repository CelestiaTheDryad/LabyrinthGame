package group2.cs301.labyrinthgame.Labyrinth;

import group2.cs301.labyrinthgame.R;

/**
 * @author Brendan, Ben
 * @version 11/7/15
 */
public class Board {
    private Tile[][] gameTiles;
    private Tile extraTile;

    /**
     * Board
     *
     * constructor that creates a random board
     */
    public Board() {
        gameTiles = new Tile[7][7];
        setNew();
    }//ctor

    /**
     * Constructor that creates a new board identical to one that's passed in
     *
     * @param toCopy board to be copied
     */
    public Board(Board toCopy) {
        gameTiles = new Tile[7][7];
        for(int i = 0; i < 7; i++) {
            for(int j = 0; j < 7; j++) {
                gameTiles[i][j] = new Tile(toCopy.gameTiles[i][j]);
            }
        }
        extraTile = toCopy.extraTile;
    }//ctor

    /**
     * setNew
     *
     * sets the board to a random starting state concurrent with
     * the rules of labyrinth
     */
    private void setNew() {
        int linesToAdd = 13;
        int teesToAdd = 6;
        int cornersToAdd = 15;
        int treasureTick = 13;
        int column = 1;
        int row = 0;

        for(int i = 0; i < 7; ++i) {
            for(int j = 0; j < 7; ++j) {
                gameTiles[i][j] = null;
            }
        }
        extraTile = null;

        gameTiles[0][0] = new Tile(Tile.CORNER, Tile.RIGHT, true, false, false, false, 0);
        gameTiles[2][0] = new Tile(Tile.TEE, Tile.DOWN, false, false, false ,false, 1);
        gameTiles[4][0] = new Tile(Tile.TEE, Tile.DOWN, false, false,false,false, 2);
        gameTiles[6][0] = new Tile(Tile.CORNER, Tile.DOWN, false, true, false ,false , 0);
        gameTiles[0][2] = new Tile(Tile.TEE, Tile.RIGHT, false, false, false, false, 3);
        gameTiles[2][2] = new Tile(Tile.TEE, Tile.RIGHT, false, false, false, false, 4);
        gameTiles[4][2] = new Tile(Tile.TEE, Tile.DOWN, false, false, false, false, 5);
        gameTiles[6][2] = new Tile(Tile.TEE, Tile.LEFT, false, false, false, false, 6);
        gameTiles[0][4] = new Tile(Tile.TEE, Tile.RIGHT, false, false, false, false, 7);
        gameTiles[2][4] = new Tile(Tile.TEE, Tile.UP, false, false, false, false, 8);
        gameTiles[4][4] = new Tile(Tile.TEE, Tile.LEFT, false, false, false, false, 9);
        gameTiles[6][4] = new Tile(Tile.TEE, Tile.LEFT, false, false, false, false, 10);
        gameTiles[0][6] = new Tile(Tile.CORNER, Tile.UP, false, false, false, false, 0);
        gameTiles[2][6] = new Tile(Tile.TEE, Tile.UP, false, false, false, false, 11);
        gameTiles[4][6] = new Tile(Tile.TEE, Tile.UP, false, false, false, false, 12);
        gameTiles[6][6] = new Tile(Tile.CORNER, Tile.LEFT, false, false, false, false, 0);

        //add 34 randomly generated tiles
        for(int i = 0; i < 35; ++i) {
            int orientation = 0;
            int type = 0;

            boolean successful;

            //get random tile type
            do {
                int rand = (int) (Math.random() * 3);
                if(rand == 0 && linesToAdd > 0) {
                    linesToAdd--;
                    type = Tile.LINE;
                    successful = true;
                }
                else if (rand == 1 && cornersToAdd > 0) {
                    cornersToAdd--;
                    type = Tile.CORNER;
                    successful = true;
                }
                else if (rand == 2 && teesToAdd > 0){
                    teesToAdd--;
                    type = Tile.TEE;
                    successful = true;
                }
                else {
                    successful = false;
                }
            } while (!successful);

            switch ((int) (Math.random()*4)+1) {
                case 1 : orientation = Tile.RIGHT;
                    break;
                case 2 : orientation = Tile.LEFT;
                    break;
                case 3 : orientation = Tile.DOWN;
                    break;
                case 4 : orientation = Tile.UP;
                    break;
            }

            //create the tile in its array spot
            if(i < 34) {
                gameTiles[column][row] = new Tile(type, orientation, false, false, false, false, treasureTick);
            }
            else {
                extraTile = new Tile(type, orientation, false, false, false, false, treasureTick);
            }

            //move row and column forward
            column +=2;
            if(column > 7) {
                column = 0;
                row++;
                if(row % 2 == 0) {
                    column++;
                }
            }
            treasureTick++;
        }

    }//setNew

    /*
    * insertExtraTile
    *
    * @param xx, yy coordinates of where to insert our tile
    * @precondition xx and yy must be valid coordinates for inserting
    *
    * inserts the extra tile into a specified coordinate and places the new extra tile
    * into the extra tile inst variable
    */
    public void insertExtraTile(int xx, int yy){
        //temp variable that will hold the new extraTile at the end of this function
        Tile tempTile = null;
        if(xx == 0){
            //moves across the row from 0 to 6, moving tiles while it goes
            for(int x = 0; x < 7; x++){
                tempTile = gameTiles[x][yy];
                if(x == 0) gameTiles[x][yy] = extraTile;
                else gameTiles[x][yy] = tempTile;
            }
        }
        else if(xx == 6){
            //moves across the row from 6 to 0, moving tiles while it goes
            for(int x = 6; x > -1; x--){
                tempTile = gameTiles[x][yy];
                if(x == 6) gameTiles[x][yy] = extraTile;
                else gameTiles[x][yy] = tempTile;
            }
        }
        else if(yy == 0){
            //moves down the column from 0 to 6, moving tiles while it goes
            for(int y = 0; y < 7; y++){
                tempTile = gameTiles[xx][y];
                if(y == 0) gameTiles[xx][y] = extraTile;
                else gameTiles[xx][y] = tempTile;
            }
        }
        else if(yy == 6){
            //moves up the column from 6 to 0, moving tiles while it goes
            for(int y = 6; y > -1; y--){
                tempTile = gameTiles[xx][y];
                if(y == 6) gameTiles[xx][y] = extraTile;
                else gameTiles[xx][y] = tempTile;
            }
        }
        extraTile = tempTile;
    }//insertExtraTile

    /**
     * rotateExtraTile
     *
     * rotates the extra tile
     */
    public void rotateExtraTile() {
        extraTile.tickTile();
    }//rotateExtraTile

    /**
     * movePlayer
     *
     * @param x int for the column the player is moving to
     * @param y int for the row the player is moving to
     * @param curPlayer int for the player that is moving
     */
    public void movePlayer(int x, int y, int curPlayer) {

        //FUCK GRAMMAAR
        boolean isBraked = false;

        for(int i = 0; i < gameTiles[0].length; i++) {
            if(isBraked) {break;}//for double breaking the loops
            for(int j = 0; j < gameTiles[0].length; j++) {
                if(gameTiles[i][j].getPlayerPresent(curPlayer)) {
                    gameTiles[i][j].setPlayersPresent(curPlayer, false);
                    isBraked = true;
                    break;
                }
            }
        }

        gameTiles[x][y].setPlayersPresent(curPlayer, true);

    }//movePlayer

}
