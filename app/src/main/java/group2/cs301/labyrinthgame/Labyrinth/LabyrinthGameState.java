package group2.cs301.labyrinthgame.Labyrinth;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

import group2.cs301.labyrinthgame.Game.infoMsg.GameState;

/**
 * @author G. Emily Nitzberg, Brendan Thomas, Ben Rumptz, Andrew Williams
 * @version December 1, 2015
 */
public class LabyrinthGameState extends GameState implements Serializable {

    public static final int MAX_NUM_CARDS = 6;

    private int currentPlayer;
    private int numPlayers;
    private Board gameBoard;

    private int lastXInserted;
    private int lastYInserted;

    private int stage;

    public static final int INSERTING = 3000;
    public static final int MOVING = 3001;
    public static final int ENDING = 3002;

    private ArrayList<PlayerData> players;

    /**
     * LabyrinthGameState
     *
     * @param initNumPlayers the number of players playing this game
     *
     * creates a new game state (default)
     */
    public LabyrinthGameState (int initNumPlayers) {
//        if(initNumPlayers < 2 || initNumPlayers > 4) {
//            Log.println(Log.ERROR, "", "WRONG NUMBER OF PLAYERS");
//            initNumPlayers = 4;
//        }

        players = new ArrayList<>();

        if(initNumPlayers > 0) {
            players.add(new PlayerData(0,0, new int[] {10,11,12,13,14,15}, 0));
        } if(initNumPlayers > 1) {
            players.add(new PlayerData(6,0, new int[] {7,8,9,18,17,16}, 1));
        } if(initNumPlayers > 2) {
            players.add(new PlayerData(0,6, new int[] {4,5,6,21,20,19}, 2));
        } if(initNumPlayers > 3) {
            players.add(new PlayerData(6,6, new int[] {1,2,3,24,23,22}, 3));
        }

        numPlayers = initNumPlayers;
        currentPlayer = 0;

        gameBoard = new Board();
        lastXInserted = 0;
        lastYInserted = 0;

        stage = INSERTING;
        highlightToInsert();
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

        players = new ArrayList<>();

        for(PlayerData playerData: toCopy.players) {
            this.players.add(new PlayerData(playerData));
        }

        numPlayers = toCopy.numPlayers;
        lastXInserted = toCopy.lastXInserted;
        lastYInserted = toCopy.lastYInserted;

        stage = toCopy.stage;
    }//ctor

    /**
     * insertTile
     *
     * inserts the extra tile into a given spot on the game board
     *
     * @param xx - column to insert the tile
     * @param yy - row to insert the tile
     */
    public void insertTile(int xx, int yy){
        gameBoard.insertExtraTile(xx, yy);
        //update our tiles' links
        this.linkTiles();
        lastXInserted = xx;
        lastYInserted = yy;

        int xShift = 0;
        int yShift = 0;

        //handle player moving
        if(xx == 0) {
            xShift = 1;
        }
        else if (xx == 6) {
            xShift = -1;
        }
        else if (yy == 0) {
            yShift = 1;
        }
        else {
            yShift = -1;
        }

        for(PlayerData player : players) {
            //if its moving a column and the player is in that column
            if(xShift == 0 && player.getXposition() == xx) {
                int newY = player.getYposition() + yShift;
                if (newY == -1) {
                    move(player.getPlayerID(), player.getXposition(), 6);
                }
                else if (newY == 7) {
                    move(player.getPlayerID(), player.getXposition(), 0);
                }
                else {
                    move(player.getPlayerID(), player.getXposition(), newY);
                }
            }
            else if(yShift == 0 && player.getYposition() == yy) {
                int newX = player.getXposition() + xShift;
                if (newX == -1) {
                    move(player.getPlayerID(), 6, player.getYposition());
                }
                else if (newX == 7) {
                    move(player.getPlayerID(), 0, player.getYposition());
                }
                else {
                    move(player.getPlayerID(), newX, player.getYposition());
                }
            }
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
        stage = LabyrinthGameState.ENDING;
        if(players.get(currentPlayer).takeTreasure(gameBoard.getTile(x,y).getTreasure()) ) {
            gameBoard.getTile(x,y).setTreasure(0);
        }
    }//move

    /**
     * move
     *
     * moves the player with the passed in ID to a new spot
     *
     * @param player - the number of the player to be moved
     * @param x - the column to move the player to
     * @param y - the row to move the player to
     */
    public void move(int player, int x, int y) {
        int tempPlayer = currentPlayer;
        currentPlayer = player;
        move(x, y);
        currentPlayer = tempPlayer;
    }


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

        gameBoard.highlightExtraTile();
    }//highlightToInsert

    public void clearHighlights() {
        gameBoard.clearHighlights();
    }

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


    public PlayerData getCurrentPlayerData() { return players.get(currentPlayer); }

    /**
     * getPlayers - returns the data for all of the players
     * @return - ArrayList of playerData (one per player)
     */
    public ArrayList<PlayerData> getPlayers() {
        return players;
    }

    /**
     * getStage - returns the stage of the game
     * @return - int for the stage of the game
     */
    public int getStage() {
        return stage;
    }

    public void setStage(int toSet) {
        if(toSet == LabyrinthGameState.INSERTING || toSet == LabyrinthGameState.ENDING || toSet == LabyrinthGameState.MOVING) {
            stage = toSet;
        }
    }
}
