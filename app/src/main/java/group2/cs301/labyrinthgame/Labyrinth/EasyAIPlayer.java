package group2.cs301.labyrinthgame.Labyrinth;

import group2.cs301.labyrinthgame.Game.*;
import group2.cs301.labyrinthgame.Game.infoMsg.GameInfo;

/**
 * This is an easy AI player that knows how to play The A-Maze-ing Labyrinth.
 * This player is EASY - It will randomly select a place to insert a tile (does not rotate).
 * It also really enjoys walking so it walks as far away from its starting place as possible.
 *
 * @author Ben Rumptz
 * @version 11/18/15
 */
public class EasyAIPlayer extends GameComputerPlayer {
    private boolean isActionInsert;
    private final int[] legalXInsertSpots = {1, 3, 5};
    private final int[] legalYInsertSpots = {1, 3, 5};


    public EasyAIPlayer(){
        super("Easy AI");
        //default to true
        isActionInsert = true;
    }

    @Override
    /*
    * Keeping this here since idk if we need to override this
    */
    protected void initAfterReady(){

    }

    @Override
    protected void receiveInfo(GameInfo info) {
        if(isActionInsert){
            //select random coords from the legalInsertSpots and create a InsertTileAction
        }
    }
}
