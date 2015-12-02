package group2.cs301.labyrinthgame.Labyrinth;

import android.graphics.Color;

import java.io.Serializable;

/**
 * @author Andrew Williams, Brendan Thomas
 * @version December 1, 2015
 */
public class PlayerData implements Serializable{

    private int Xposition;
    private int Yposition;
    private int Xstart;
    private int Ystart;
    // Treasure Info: 0-24 possible treasures
    // 0 == no treasure's remaining
    // 1-24 == treasure ID
    private int[] treasures;
    // current treasure to search for
    private int currentTreasure;
    private int playerColor;

    public PlayerData(int X, int Y) {
        Yposition = Y;
        Xposition = X;
        Ystart = Y;
        Xstart = X;
        if (X == 0 && Y == 0) {
            playerColor = Color.RED;
        }
        else if (X == 0 && Y == 6) {
            playerColor = Color.BLUE;
        }
        else if (X == 6 && Y == 0) {
            playerColor = Color.rgb(200,100,0);
        }
        else if (X == 6 && Y == 6) {
            playerColor = Color.GREEN;
        }
        treasures= new int[LabyrinthGameState.MAX_NUM_CARDS];
        currentTreasure = 0;
    }

    public PlayerData(PlayerData toCopy) {
        this.Xposition = toCopy.Xposition;
        this.Yposition = toCopy.Yposition;
        this.treasures = new int[LabyrinthGameState.MAX_NUM_CARDS];
        for(int i = 0; i < LabyrinthGameState.MAX_NUM_CARDS; i++) {
            this.treasures[i] = toCopy.treasures[i];
        }

        this.currentTreasure = toCopy.currentTreasure;
    }

    /**
     * getCurentTreasure
     * @return
     */
    public int getCurrentTreasure() {
        return treasures[currentTreasure];
    }


    /**
     * todo
     * @param initX
     * @param initY
     */
    public void movePlayer(int initX, int initY) {
        Xposition = initX;
        Yposition = initY;
    }


    //todo
    public int[] getTreasures() {
        return treasures;
    }



    public boolean takeTreasure(int takenTreasure) {
        if(currentTreasure >= LabyrinthGameState.MAX_NUM_CARDS) {
            return false;
        }
        if(treasures[currentTreasure] == takenTreasure) {
            currentTreasure++;
            return true;
        }
        return false;
    }

    public int getXposition() {
        return Xposition;
    }

    public int getYposition() {
        return Yposition;
    }

    public boolean hasWon() {
        if (currentTreasure >= LabyrinthGameState.MAX_NUM_CARDS && Xposition == Xstart && Yposition == Ystart) {
            return true;
        }
        return false;
    }

}
