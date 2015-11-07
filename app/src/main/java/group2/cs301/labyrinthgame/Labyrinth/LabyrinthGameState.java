package group2.cs301.labyrinthgame.Labyrinth;

import group2.cs301.labyrinthgame.Game.infoMsg.GameState;

/**
 * @author Brendan, Bern
 * @version 11/7/15
 */
public class LabyrinthGameState extends GameState {
    private int currentPlayer;
    private Board gameBoard;
    private int[] player1Targets;
    private int[] player2Targets;
    private int[] player3Targets;
    private int[] player4Targets;

    private int lastXInserted;
    private int lastYInserted;

    //creates a new gameState
    public LabyrinthGameState () {
        currentPlayer = 0;
        gameBoard = new Board();
        player1Targets = new int[4];
        player2Targets = new int[4];
        player3Targets = new int[4];
        player4Targets = new int[4];
        lastXInserted = 0;
        lastYInserted = 0;
    }//ctor

    //creates a deep copy of a given game state
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
    }

    //rotates the extra tile one tick
    public void rotateTile() {
        gameBoard.rotateExtraTile();
    }//rotateTile

    //move a player to a tile
    public void move(int x, int y) {
        gameBoard.movePlayer(x, y, currentPlayer);
    }
}
