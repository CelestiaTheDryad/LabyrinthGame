package group2.cs301.labyrinthgame.Labyrinth;

/**
 * Created by Andrew Williams
 * on 11/22/15.
 */
public class PlayerData {

    private int Xposition;
    private int Yposition;
    private int[] treasures;
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
        return getTreasures();
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
