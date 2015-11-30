package group2.cs301.labyrinthgame.Labyrinth;

/**
 * @author Brendan Thomas
 * @version November 29, 2015
 *
 * thread that controls the drawing of a shifting row of tiles
 */
public class ShiftAnimation extends Thread {

    private int insertRow;
    private int insertColumn;
    private int tickNum;
    private Board board;

    /**
     * constructor for a shift animation
     *
     * @param initColumn - the column the tile was inserted into
     * @param initRow - the row the tile was inserted into
     */
    public ShiftAnimation(int initColumn, int initRow, Board initBoard, int tileSize) {
        super();
        board = initBoard;
        insertRow = initRow;
        insertColumn = initColumn;
        tickNum = tileSize;
    }

    public void run() {
        while(tickNum > 0) {


            try {
                Thread.sleep(33);
            }
            catch (InterruptedException e) {
                //Interruption isn't a bad problem
            }
            tickNum--;
        }
    }
}
