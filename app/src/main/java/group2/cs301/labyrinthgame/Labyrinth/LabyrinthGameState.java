package group2.cs301.labyrinthgame.Labyrinth;

import group2.cs301.labyrinthgame.Game.infoMsg.GameState;

/**
 * Created by Brendan on 11/1/2015.
 */
public class LabyrinthGameState extends GameState {
    private int currentPlayer;
    private Board gameBoard;
    private String[] player1Targets;
    private String[] player2Targets;
    private String[] player3Targets;
    private String[] player4Targets;

    //creates a new gameState
    public LabyrinthGameState () {
        currentPlayer = 0;
        gameBoard = new Board();
        player1Targets = new String[4];
        player2Targets = new String[4];
        player3Targets = new String[4];
        player4Targets = new String[4];
    }

    //creates a deep copy of a given game state
    public LabyrinthGameState (LabyrinthGameState toCopy) {
        currentPlayer = toCopy.currentPlayer;

        gameBoard = new Board(toCopy.gameBoard);

        player1Targets = new String[toCopy.player1Targets.length];
        for(int i = 0; i < player1Targets.length; i++) {
            player1Targets[i] = toCopy.player1Targets[i];
        }

        player2Targets = new String[toCopy.player2Targets.length];
        for(int i = 0; i < player2Targets.length; i++) {
            player2Targets[i] = toCopy.player2Targets[i];
        }

        player3Targets = new String[toCopy.player3Targets.length];
        for(int i = 0; i < player3Targets.length; i++) {
            player3Targets[i] = toCopy.player3Targets[i];
        }

        player4Targets = new String[toCopy.player4Targets.length];
        for(int i = 0; i < player4Targets.length; i++) {
            player4Targets[i] = toCopy.player4Targets[i];
        }
    }
}
