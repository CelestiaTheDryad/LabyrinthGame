package group2.cs301.labyrinthgame.Labyrinth;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

import group2.cs301.labyrinthgame.Game.infoMsg.GameState;

/**
 * @author G. Emily Nitzberg, Brendan Thomas, Ben Rumptz, Andrew Williams
 * @version November 9, 2015
 */
public class LabyrinthGameState extends GameState implements Serializable {
    private int currentPlayer;
    private int numPlayers;
    private Board gameBoard;

    private int lastXInserted;
    private int lastYInserted;

    private ArrayList<PlayerData> players;

    /**
     * LabyrinthGameState
     *
     * @param initNumPlayers the number of players playing this game
     *
     * creates a new game state (default)
     */
    public LabyrinthGameState (int initNumPlayers) {
        if(numPlayers < 2 || numPlayers > 4) {
            Log.println(Log.ERROR, "", "WRONG NUMBER OF PLAYERS");
            initNumPlayers = 4;
        }

        players = new ArrayList<>();

        switch (initNumPlayers) {
            case 4: players.add(new PlayerData(6,6));
            case 3: players.add(new PlayerData(0,6));
            case 2: players.add(new PlayerData(6,0));
            case 1: players.add(new PlayerData(0,0));
        }

        numPlayers = initNumPlayers;
        currentPlayer = 0;

        gameBoard = new Board();
        lastXInserted = 0;
        lastYInserted = 0;
    }//ctor

    /**
     * LabyrinthGameState
     *
     * creates a game state that's an identical deep copy of a passed in game state
     * @param toCopy game state to be copied
     */
    public LabyrinthGameState (LabyrinthGameState toCopy) {
        currentPlayer = toCopy.currentPlayer;

        gameBoard = new Board(toCopy.gameBoard);

        for(PlayerData playerData: toCopy.players) {
            this.players.add(new PlayerData(playerData));
        }

        numPlayers = toCopy.numPlayers;
        lastXInserted = toCopy.lastXInserted;
        lastYInserted = toCopy.lastYInserted;
    }//ctor

    /*
    * insertTile
    * @param xx, yy coordinates to insert the extra tile
    * @precondition xx and yy must be valid coordinates for inserting
    * calls the insertExtraTile function from gameBoard
    */
    public void insertTile(int xx, int yy){
        gameBoard.insertExtraTile(xx, yy);
        //update our tiles' links
        this.linkTiles();
        lastXInserted = xx;
        lastYInserted = yy;

        if(xx == 0) {
            move( (xx++)%7, yy);
        }
        else if(xx == 6) {
            move( (xx--)%7 , yy);
        }
        else if(yy == 0) {
            move(xx, (yy++)%7 );
        }
        else if(yy == 6) {
            move(xx, (yy--)%7 );
        }
    }//insertTile

    /**
     * rotateTile
     *
     * calls the board's rotateExtraTile function to rotate the extra tile one tick
     */
    public void rotateTile() {
        gameBoard.rotateExtraTile();
    }//rotateTile

    /**
     * move
     *
     * moves the current player to a new spot on the game board
     *
     * @param x column to be moved to
     * @param y row to be moved to
     */
    public void move(int x, int y) {
        players.get(currentPlayer).movePlayer(x, y);
    }//move


    /**
     * nextTurn
     *
     * advances currentPlayer to the next player in a cycle
     */
    public void nextTurn() {
        currentPlayer++;
        if(currentPlayer == numPlayers) {
            currentPlayer = 0;
        }
    }


    /**
     * getGameBoard - gets the board
     * @return  the board
     */
    public Board getGameBoard() {
        return gameBoard;
    }//getGameBoard

    /**
     * linkTiles
     *
     * links all of the tiles of the board together
     */
    public void linkTiles() {
        for(int i = 0; i < 7; ++i) {
            for(int j = 0; j < 7; ++j) {
                gameBoard.linkTile(i, j);
            }
        }
    }//linkTiles

    /**
     * highlightToInsert
     *
     * highlights all the valid places to insert a tile
     */
    public void highlightToInsert() {
        gameBoard.clearHighlights();

        //find the x location of the reverse move
        int revX;
        if(lastXInserted == 0) {
            revX = 6;
        }
        else if(lastXInserted == 6) {
            revX = 0;
        }
        else {
            revX = lastXInserted;
        }

        //find the y location of the reverse move
        int revY;
        if(lastYInserted == 0) {
            revY = 6;
        }
        else if(lastYInserted == 6) {
           revY = 0;
        }
        else {
            revY = lastYInserted;
        }

        //loop through each insert location and highlight if it doesn't
        for(int i = 0; i < 12; ++i) {
            int[] location = Board.INSERT_LOCATIONS[i];
            if(!(revX == location[0] && revY == location[1])) {
                gameBoard.highlightTile(location[0], location[1]);
            }
        }
    }//highlightToInsert

    /**
     * highlightToMove
     *
     * highlights the tiles that a specified player can move to
     * @param player the number of the player who's movements are to be highlighted
     */
    public void highlightToMove(int player) {
        gameBoard.clearHighlights();
        gameBoard.highlightToMove(players.get(player).getXposition(), players.get(player).getYposition());
    }//highlightToMove

    //getter functions
    public int getLastXInserted(){ return lastXInserted; }
    public int getLastYInserted(){ return lastYInserted; }

    /**
     * getCurrentPlayer - gets current player
     * @return  current player
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }
}
