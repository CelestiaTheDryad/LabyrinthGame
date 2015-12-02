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
    private int drawRow;
    private int drawColumn;
    private int tickNum;
    private int tickMax;
    private int tickRate;
    private Board board;
    private boolean horizontal;
    private boolean normal;

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
        drawRow = insertRow + 1;
        drawColumn = insertColumn + 1;
        tickMax = tileSize;
        tickNum = 0;
        tickRate = tileSize / 45 + 1;
        if (initColumn == 0) {
            horizontal = true;
            normal = true;
        }
        else if (initColumn == 6) {
            horizontal = true;
            normal = false;
        }
        else if (initRow == 0) {
            horizontal = false;
            normal = true;
        }
        else {
            horizontal = false;
            normal = false;
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
        if (horizontal && normal) {
            view.drawBlank(canvas, true, drawRow);
            view.drawTile(board.getExtraTile(), 7, drawRow, tickNum, 0, canvas);
            view.drawTile(board.getTile(0, insertRow), 0, drawRow, tickNum, 0, canvas);
            view.drawTile(board.getTile(1, insertRow), 1, drawRow, tickNum, 0, canvas);
            view.drawTile(board.getTile(2, insertRow), 2, drawRow, tickNum, 0, canvas);
            view.drawTile(board.getTile(3, insertRow), 3, drawRow, tickNum, 0, canvas);
            view.drawTile(board.getTile(4, insertRow), 4, drawRow, tickNum, 0, canvas);
            view.drawTile(board.getTile(5, insertRow), 5, drawRow, tickNum, 0, canvas);
            view.drawTile(board.getTile(6, insertRow), 6, drawRow, tickNum, 0, canvas);
        }
        else {
            view.drawBlank(canvas, false, drawColumn);
            view.drawTile(board.getExtraTile(), drawColumn, 7, 0, tickNum, canvas);
            view.drawTile(board.getTile(insertColumn, 0), drawColumn, 0, 0, tickNum, canvas);
            view.drawTile(board.getTile(insertColumn, 1), drawColumn, 1, 0, tickNum, canvas);
            view.drawTile(board.getTile(insertColumn, 2), drawColumn, 2, 0, tickNum, canvas);
            view.drawTile(board.getTile(insertColumn, 3), drawColumn, 3, 0, tickNum, canvas);
            view.drawTile(board.getTile(insertColumn, 4), drawColumn, 4, 0, tickNum, canvas);
            view.drawTile(board.getTile(insertColumn, 5), drawColumn, 5, 0, tickNum, canvas);
            view.drawTile(board.getTile(insertColumn, 6), drawColumn, 6, 0, tickNum, canvas);
        }

        tickNum += tickRate;
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
