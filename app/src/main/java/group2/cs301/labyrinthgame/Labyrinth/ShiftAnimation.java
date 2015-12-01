package group2.cs301.labyrinthgame.Labyrinth;

import android.graphics.Canvas;

/**
 * @author Brendan Thomas
 * @version November 29, 2015
 *
 * class that controls the drawing of a shifting row of tiles on a LabyrinthSurfaceView
 */
public class ShiftAnimation{

    private int insertRow;
    private int insertColumn;
    private int tickNum;
    private int tickMax;
    private Board board;
    private boolean horizontal;

    /**
     * constructor for a shift animation
     *
     * @param initColumn - the column the tile was inserted into
     * @param initRow - the row the tile was inserted into
     * @param tileSize - the width of a tile in pixels
     */
    public ShiftAnimation(int initColumn, int initRow, Board initBoard, int tileSize) {
        super();
        board = initBoard;
        insertRow = initRow;
        insertColumn = initColumn;
        tickMax = tileSize;
        tickNum = 0;
        if (initColumn == 0) {
            horizontal = true;
        }
        else {
            horizontal = false;
        }
    }//ctor

    /**
     * tick
     *
     * progresses the animation by one frame and draws all updates
     *
     * @param canvas - the canvas to draw updates on
     * @param view - the parent view for drawing methods
     */
    public void tick(Canvas canvas, LabyrinthSurfaceView view) {
        if (horizontal) {
            view.drawTile(board.getExtraTile(), 0, insertRow, tickNum, 0, canvas);
            view.drawTile(board.getTile(0, insertRow), 1, insertRow, tickNum, 0, canvas);
            view.drawTile(board.getTile(1, insertRow), 2, insertRow, tickNum, 0, canvas);
            view.drawTile(board.getTile(2, insertRow), 3, insertRow, tickNum, 0, canvas);
            view.drawTile(board.getTile(3, insertRow), 4, insertRow, tickNum, 0, canvas);
            view.drawTile(board.getTile(4, insertRow), 5, insertRow, tickNum, 0, canvas);
            view.drawTile(board.getTile(5, insertRow), 6, insertRow, tickNum, 0, canvas);
            view.drawTile(board.getTile(6, insertRow), 7, insertRow, tickNum, 0, canvas);
        }
        else {
            view.drawTile(board.getExtraTile(), insertColumn, 0, 0, tickNum, canvas);
            view.drawTile(board.getTile(insertColumn, 0), insertColumn, 1, 0, tickNum, canvas);
            view.drawTile(board.getTile(insertColumn, 1), insertColumn, 2, 0, tickNum, canvas);
            view.drawTile(board.getTile(insertColumn, 2), insertColumn, 3, 0, tickNum, canvas);
            view.drawTile(board.getTile(insertColumn, 3), insertColumn, 4, 0, tickNum, canvas);
            view.drawTile(board.getTile(insertColumn, 4), insertColumn, 5, 0, tickNum, canvas);
            view.drawTile(board.getTile(insertColumn, 5), insertColumn, 6, 0, tickNum, canvas);
            view.drawTile(board.getTile(insertColumn, 6), insertColumn, 7, 0, tickNum, canvas);
        }

        tickNum++;
    }//tick

    /**
     * isOver
     *
     * @return returns true if the animation is finished, false if it is not finished
     */
    public boolean isOver() {
        if (tickNum > tickMax) {
           return true;
        }
        return false;
    }//
}
