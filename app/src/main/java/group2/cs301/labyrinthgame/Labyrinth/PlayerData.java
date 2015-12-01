package group2.cs301.labyrinthgame.Labyrinth;

import java.io.Serializable;

/**
 * @author Andrew Williams, Brendan Thomas
 * @version December 1, 2015
 */
public class PlayerData implements Serializable{

    private int Xposition;
    private int Yposition;
    // Treasure Info: 0-24 possible treasures
    // 0 == no treasure's remaining
    // 1-24 == treasure ID
    private int[] treasures;
    // current treasure to search for
    private int currentTreasure;

    public PlayerData(int X, int Y) {
        Yposition = Y;
        Xposition = X;
        treasures = new int[4];
        currentTreasure = 0;
    }

    public PlayerData(PlayerData toCopy) {
        this.Xposition = toCopy.Xposition;
        this.Yposition = toCopy.Yposition;
        this.treasures = new int[4];
        for(int i = 0; i < 4; i++) {
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



    public int[] getTreasures() {
        return treasures;
    }


    public boolean takeTreasure(int takenTreasure) {
        if(currentTreasure > 3) {
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
}
