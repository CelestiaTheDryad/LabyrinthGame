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
    private LabyrinthGameState myState;


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
        // use the info we are given to init our gamestate
        if(info instanceof LabyrinthGameState)
            myState = (LabyrinthGameState) info;
        else
        // if we are given a non-gamestate GameInfo, return for now
            return;

        //First Action to send is Insert
        if(isActionInsert){
            int[] val = Board.INSERT_LOCATIONS[(int) Math.random() *12];
            int xx = val[0];
            int yy = val[1];
            game.sendAction(new InsertTileAction(this, xx, yy));
            isActionInsert = false;
        }//if
        else{
            //if we are not inserting, we must be moving
            int myPlayerNum = myState.getCurrentPlayer();
            PlayerData myData = myState.getPlayers().get(myPlayerNum);
            myState.highlightToMove(myPlayerNum);

            //first get all my tiles
            //loop through each one at a time
            //if the tile is highlighted and has my treasure, go there and stop
            //if it is highlighted and farther away than the last highlighted tile, go there  **FIND A WAY TO CALCULATE DISTANCE FROM CURR TILE
            Board currBoard = myState.getGameBoard();
            double curMaxDist = -1;
            int xx = 0;
            int yy = 0;
            for(int x = 0; x < 7; x++){
                for(int y = 0; y < 7; y++){
                    Tile thisTile = currBoard.getTile(x,y);
                    if(thisTile.isHighlighted() && (thisTile.getTreasure() == myData.getCurrentTreasure())){
                        game.sendAction(new MoveAction(this, x, y));
                        curMaxDist = -1;
                        break;
                    }
                    //calculate the furthest tile away and store that tile's x and y coords
                    double dist = (x - myData.getXposition()) * (x - myData.getXposition());
                    dist += (y - myData.getYposition()) * (y - myData.getYposition());
                    dist = Math.sqrt(dist);
                    if(dist > curMaxDist){
                        //holds the x,y values that we want to go to
                        xx = x;
                        yy = y;
                    }
                }
            }
            //if we did not move to a treasure tile, move now
            if(curMaxDist != -1)
                game.sendAction(new MoveAction(this, xx,yy));
        }//else
    }
}
