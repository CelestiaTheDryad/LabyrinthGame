package group2.cs301.labyrinthgame.Labyrinth;

import group2.cs301.labyrinthgame.Game.*;
import group2.cs301.labyrinthgame.Game.infoMsg.GameInfo;

/**
 * This is an easy AI player that knows how to play The A-Maze-ing Labyrinth.
 * This player is EASY - It will randomly select a place to insert a tile (does not rotate).
 * It also really enjoys walking so it walks as far away from its starting place as possible.
 *
 * @author Ben Rumptz
 * @version 12/1/15
 */
public class EasyAIPlayer extends GameComputerPlayer {
    private LabyrinthGameState myState;  //holds my state

    /*
    * EasyAIPlayer()
    * initializes an Easy AI by calling the super constructor
    */
    public EasyAIPlayer(){
        super("Easy AI");
    }

    @Override
    /*
    * Keeping this here since idk if we need to override this
    */
    protected void initAfterReady(){
        super.initAfterReady();
    }

    @Override
    /*
    * recieveInfo
    *
    * When we are passed a GameInfo that is a GameState, begins running
    * if it is not our turn stop exectuting this ftn
    * if it is our turn create GameActions with the following precedence (Insert > Move > EndTurn)
    * Completes each section of the flowchart in order && resets at the start after an EndTurn is created
    *
    * returns void after sending a GameAction to not run into threading issues
    */
    protected void receiveInfo(GameInfo info) {
        // use the info we are given to init our gamestate
        if(info instanceof LabyrinthGameState){
            myState = (LabyrinthGameState) info;
        }
        // if we are given a non-gamestate GameInfo, return for now
        else
            return;

        //don't do anything if it is not my turn
        if(myState.getCurrentPlayer() != super.playerNum)
            return;

        if(myState.getStage() == LabyrinthGameState.INSERTING){
            int[] val = Board.INSERT_LOCATIONS[(int) (Math.random() *12)];
            int xx = val[0];
            int yy = val[1];
            game.sendAction(new InsertTileAction(this, xx, yy));
            return;
        }//if
        else if(myState.getStage() == LabyrinthGameState.MOVING){
            PlayerData myData = myState.getPlayers().get(super.playerNum);
            myState.highlightToMove(super.playerNum);

            //first get all my tiles
            //loop through each one at a time
            //if the tile is highlighted and has my treasure, go there and stop
            //if it is highlighted and farther away than the last highlighted tile, go there
            Board currBoard = myState.getGameBoard();
            double curMaxDist = -1;
            int xx = 0;
            int yy = 0;
            for(int x = 0; x < 7; x++){
                for(int y = 0; y < 7; y++){
                    Tile thisTile = currBoard.getTile(x,y);
                    if(thisTile.isHighlighted() && (thisTile.getTreasure() == myData.getCurrentTreasure())){
                        curMaxDist = -1;
                        game.sendAction(new MoveAction(this, x, y));
                        return;
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
            game.sendAction(new MoveAction(this, xx, yy));
        }
        else if(myState.getStage() == LabyrinthGameState.ENDING){
            //end our turn last
            game.sendAction(new NextTurnAction(this));
        }
    }
}
