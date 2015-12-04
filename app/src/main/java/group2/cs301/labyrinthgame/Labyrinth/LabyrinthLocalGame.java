package group2.cs301.labyrinthgame.Labyrinth;

import group2.cs301.labyrinthgame.Game.GamePlayer;
import group2.cs301.labyrinthgame.Game.LocalGame;
import group2.cs301.labyrinthgame.Game.actionMsg.GameAction;
import group2.cs301.labyrinthgame.Labyrinth.InsertTileAction;
import group2.cs301.labyrinthgame.Labyrinth.LabyrinthGameHumanPlayer;
import group2.cs301.labyrinthgame.Labyrinth.LabyrinthGameState;
import group2.cs301.labyrinthgame.Labyrinth.MoveAction;
import group2.cs301.labyrinthgame.Labyrinth.NextTurnAction;
import group2.cs301.labyrinthgame.Labyrinth.RotateTileAction;

/**
 * @author Created by Andrew Williams
 * @version 11/22/15.
 */
public class LabyrinthLocalGame extends LocalGame {

    private LabyrinthGameState labyrinthGameState;

    /**
     * This ctor creates a new game state
     */
    public LabyrinthLocalGame(int numPlayers) {
        labyrinthGameState = new LabyrinthGameState(numPlayers);
    }

    /**
     * can the player with the given id take an action right now?
     */
    @Override
    public boolean canMove(int playerId) {
        return (playerId == labyrinthGameState.getCurrentPlayer());
    }

    /**
     * This method is called when a new action arrives from a player
     *
     * @return true if the action was taken or false if the action was invalid/illegal.
     */
    @Override
    public boolean makeMove(GameAction action) {
        if(!canMove(getPlayerIdx(action.getPlayer()))) {
            return false;
        }
        if(action instanceof InsertTileAction) {
            labyrinthGameState.insertTile(((InsertTileAction) action).getX(), ((InsertTileAction) action).getY());
            labyrinthGameState.setStage(LabyrinthGameState.MOVING);
            labyrinthGameState.highlightToMove(labyrinthGameState.getCurrentPlayer());
            return true;
        }
        else if(action instanceof MoveAction) {
            labyrinthGameState.move(((MoveAction) action).getX(), ((MoveAction) action).getY());
            labyrinthGameState.setStage(LabyrinthGameState.ENDING);
            labyrinthGameState.clearHighlights();
            return true;
        }
        else if(action instanceof RotateTileAction) {
            labyrinthGameState.rotateTile();
            return true;
        }
        else if(action instanceof NextTurnAction) {
            labyrinthGameState.nextTurn();
            labyrinthGameState.setStage(LabyrinthGameState.INSERTING);
            labyrinthGameState.highlightToInsert();
            return true;
        }

        return false;

    }//makeMove

    /**
     * send the updated state to a given player
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        LabyrinthGameState updatedState = new LabyrinthGameState(labyrinthGameState);
        p.sendInfo(updatedState);
    }//sendUpdatedSate


    /**
     * Check if the game is over
     *
     * @return
     * 		a message that tells who has won the game, or null if the
     * 		game is not over
     */
    @Override
    public String checkIfGameOver() {
        String message = null;

        if(labyrinthGameState.getCurrentPlayerData().hasWon()) {
            message = "Game Over! Player " + (labyrinthGameState.getCurrentPlayer()+1) + "has won!";
        }

        return message;
    }

}
