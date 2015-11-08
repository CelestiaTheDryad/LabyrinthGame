package group2.cs301.labyrinthgame.Labyrinth;

import group2.cs301.labyrinthgame.Game.infoMsg.GameState;

/**
 * @author Brendan Thomas, Ben Rumptz
 * @version November 7, 2015
 */
public class LabyrinthGameState extends GameState {
    private int currentPlayer;
    private int numPlayers;
    private Board gameBoard;
    private int[] player1Targets;
    private int[] player2Targets;
    private int[] player3Targets;
    private int[] player4Targets;

    private int lastXInserted;
    private int lastYInserted;

    /**
     * LabyrinthGameState
     *
     * @param initNumPlayers the number of players playing this game
     *
     * creates a new game state (default)
     */
    public LabyrinthGameState (int initNumPlayers) {
        currentPlayer = 0;
        numPlayers = initNumPlayers;
        gameBoard = new Board();
        player1Targets = new int[4];
        player2Targets = new int[4];
        player3Targets = new int[4];
        player4Targets = new int[4];
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

        player1Targets = new int[toCopy.player1Targets.length];
        for(int i = 0; i < player1Targets.length; i++) {
            player1Targets[i] = toCopy.player1Targets[i];
        }

        player2Targets = new int[toCopy.player2Targets.length];
        for(int i = 0; i < player2Targets.length; i++) {
            player2Targets[i] = toCopy.player2Targets[i];
        }

        player3Targets = new int[toCopy.player3Targets.length];
        for(int i = 0; i < player3Targets.length; i++) {
            player3Targets[i] = toCopy.player3Targets[i];
        }

        player4Targets = new int[toCopy.player4Targets.length];
        for(int i = 0; i < player4Targets.length; i++) {
            player4Targets[i] = toCopy.player4Targets[i];
        }
        numPlayers = toCopy.numPlayers;
        lastXInserted = toCopy.lastXInserted;
        lastYInserted = toCopy.lastYInserted;
    }//ctor

    /*
    * insertTile
    * @param xx, yy coordinates to insert the extra tile
    * calls the insertExtraTile function from gameBoard
    */
    public void insertTile(int xx, int yy){
        gameBoard.insertExtraTile(xx, yy);
        lastXInserted = xx;
        lastYInserted = yy;
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
        gameBoard.movePlayer(x, y, currentPlayer);
    }//move


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
                gameBoard.linkTile(j, i);
            }
        }
    }//linkTiles
}
