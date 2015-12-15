package group2.cs301.labyrinthgame.Labyrinth;

import group2.cs301.labyrinthgame.Game.*;
import group2.cs301.labyrinthgame.Game.infoMsg.GameInfo;

/**
 * @author G. Emily Nitzberg, Ben Rumptz, Brendan Thomas, Andrew Williams
 * @version 12/9/15
 */
public class HardAIPlayer extends GameComputerPlayer {
    private LabyrinthGameState myState;  //holds my state

    /*
    * HardAIPlayer()
    * initializes a Hard AI by calling the super constructor
    */
    public HardAIPlayer(String name){
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
            //if it is highlighted and close to my target, go there since I can get there soon
            Board currBoard = myState.getGameBoard();
            double curClosestDist = 100;
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
                    //calculate the best tile and store that tile's x and y coords
                    if(thisTile.isHighlighted()){
                        //find how far the highlighted tile is from my target treasure
                        double tileToTreasure = findDistToMyTreasurefromX(currBoard, myData, x, y);
                        //if it is the closest tile to my treasure, go there
                        if(tileToTreasure < curClosestDist){
                            tarX = x;
                            tarY = y;
                            curClosestDist = tileToTreasure;
                        }
                    }
                }
            }
            //if we can move, go to that location
            if(curClosestDist != 100) {
                game.sendAction(new MoveAction(this, tarX, tarY));
            }
            //if we can't move, stay still
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
        double dist = (myX - tarX) * (myX - tarX);
        dist += (myY - tarY) * (myY - tarY);
        dist = Math.sqrt(dist);
        return Math.abs(dist);
    }

    //finds the distance from a coord (theX,theY) to the treasure that PlayerData p is looking for
    private double findDistToMyTreasurefromX(Board b, PlayerData p, int theX, int theY){
        int x = 0;
        int y = 0;
        for(int i = 0; i < 7; ++i){
            for(int j = 0; j < 7; ++j){
                if(b.getTile(i,j).getTreasure() == p.getCurrentTreasure()){
                    x = i;
                    y = j;
                    break;
                }
            }
        }
        return findDist(x,y, theX, theY);
    }
}
