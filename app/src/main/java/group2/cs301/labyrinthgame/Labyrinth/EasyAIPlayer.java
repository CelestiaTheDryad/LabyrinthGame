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
    public EasyAIPlayer(String name){
        super(name);
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
        else {
            return;
        }

        //do nothing if it's not my turn
        if (myState.getCurrentPlayer() != playerNum) {
            return;
        }

        //Give the players a bit of time
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            //fine
        }

        if(myState.getStage() == LabyrinthGameState.INSERTING){
           int xx, yy;
            do {
                int[] val = Board.INSERT_LOCATIONS[(int) (Math.random() * 12)];
                xx = val[0];
                yy = val[1];
            }
            while(!myState.getGameBoard().getTile(xx,yy).isHighlighted());
            game.sendAction(new InsertTileAction(this, xx, yy));
            }//if

        else if(myState.getStage() == LabyrinthGameState.MOVING){

            //wait for the humans' puny animations
            try {
                Thread.sleep(1500);
            }
            catch (InterruptedException e) {
                //fine
            }

            PlayerData myData = myState.getPlayers().get(playerNum);

            //first get all my tiles
            //loop through each one at a time
            //if the tile is highlighted and has my treasure, go there and stop
            //if it is highlighted and farther away than the last highlighted tile, go there
            Board currBoard = myState.getGameBoard();
            double curMaxDist = -1;
            //Get my x,y and the targetX, targetY
            int myX = myData.getXposition();
            int myY = myData.getYposition();
            int tarX = myX;
            int tarY = myY;
            //loop through the tiles and find where to move
            for(int x = 0; x < 7; x++){
                for(int y = 0; y < 7; y++){
                    Tile thisTile = currBoard.getTile(x,y);
                    if(thisTile.isHighlighted() && (thisTile.getTreasure() == myData.getCurrentTreasure())){
                        game.sendAction(new MoveAction(this, x, y));
                        return;
                    }
                    //calculate the furthest tile away and store that tile's x and y coords
                    if(thisTile.isHighlighted()){
                        double thisDist = findDist(myX,myY,x,y);
                        if(curMaxDist < thisDist){
                            tarX = x;
                            tarY = y;
                            curMaxDist = thisDist;
                        }
                    }
                }
            }
            //if we did not move to a treasure tile, move now
            if(curMaxDist > 0) {
                game.sendAction(new MoveAction(this, tarX, tarY));
            }
            else {
                game.sendAction(new MoveAction(this, myX, myY));
            }
        }
        else if(myState.getStage() == LabyrinthGameState.ENDING){
            game.sendAction(new NextTurnAction(this));
        }
    }

    //pythagorean theorem to find distance between 2 points
    private double findDist(int myX, int myY, int tarX, int tarY){
        double dist = (myX - tarY) * (myX - tarX);
        dist += (myY - tarY) * (myY - tarY);
        dist = Math.sqrt(dist);
        return dist;
    }
}
