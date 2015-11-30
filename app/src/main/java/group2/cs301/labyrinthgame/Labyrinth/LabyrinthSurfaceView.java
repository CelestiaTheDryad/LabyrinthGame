package group2.cs301.labyrinthgame.Labyrinth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * @author Brendan Thomas
 * @version November 29, 2015
 *
 * This class is the drawing surface for the game board
 */
public class LabyrinthSurfaceView extends SurfaceView {

    private int drawLeft;
    private int drawTop;
    private int drawSize;
    private int tileSize;
    private Board board;
    private ShiftAnimation shiftAnim;
    private MoveAnimation moveAnim;

    public LabyrinthSurfaceView(Context context) {
        super(context);
        init();
    }//ctor

    public LabyrinthSurfaceView(Context context, AttributeSet set) {
        super(context, set);
        init();
    }//ctor

    public LabyrinthSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }//ctor

    /**
     * init
     *
     * sets up several values that are used for future drawing of the view
     */
    private void init() {
        //allow drawing on the canvas
        setWillNotDraw(false);

        //set the background of the canvas to a nice brown
        setBackgroundColor(Color.rgb(112, 56, 13));

        //get values for the canvas size and tile size
        int canvasHeight = getHeight();
        int canvasWidth = getWidth();
        int widthBuffer;
        int heightBuffer;
        int genBuffer;

        //get what we need to draw the canvas as a square
        //canvasHeight - heightBuffer*2 should equal canvasWidth - widthBuffer*2
        if (canvasHeight < canvasWidth) {
            widthBuffer = (canvasWidth - canvasHeight) / 2;
            heightBuffer = 0;
        }
        else if (canvasHeight > canvasWidth) {
            heightBuffer = (canvasHeight - canvasWidth) / 2;
            widthBuffer = 0;
        }
        else {
            heightBuffer = 0;
            widthBuffer = 0;
        }

        //get the general space buffer around the square, at least 4 pixels
        //the difference of all the buffers should eb even divisible by 9
        if ((canvasHeight - heightBuffer * 2) % 9 < 5) {
            genBuffer = (canvasHeight - heightBuffer * 2) % 9 + 9;
        }
        else {
            genBuffer = (canvasHeight - heightBuffer * 2) % 9;
        }

        //find out how big to draw the tiles
        drawSize = (canvasHeight - heightBuffer - genBuffer);
        tileSize = drawSize / 9;
        drawTop = canvasHeight - genBuffer - heightBuffer;
        drawLeft = canvasWidth - genBuffer - widthBuffer;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }//draw

    /**
     * drawTile
     *
     * draws a given tile at a given spot on the grid, with additional offset if it's being drawn
     * part of an animation.
     *
     * @param toDraw - The tile object to be drawn
     * @param column - The column to draw the tile in, 0-8
     * @param row - The row to draw the tile in, 0-8
     * @param columnOffset - The number of pixels to offset the tile's column by
     * @param rowOffset - the number of pixels to offset the tile's row by
     * @param canvas - the canvas to draw the tile on
     */
    private void drawTile(Tile toDraw, int column, int row, int rowOffset, int columnOffset, Canvas canvas) {
        int lightGrey = Color.rgb(200,200,200);
        int medGrey = Color.rgb(125,125,125);
        int darkGrey = Color.rgb(50,50,50);

        //find pixel boundaries to draw the tile in
        int tileLeft = drawLeft + column * tileSize + columnOffset + 2;
        int tileRight = drawLeft + (column + 1) * tileSize + columnOffset - 2;
        int tileTop = drawTop + row * tileSize + rowOffset + 2;
        int tileBottom = drawTop + (row + 1) * tileSize + rowOffset - 2;

        //draw the background
        for(int blockLeft = tileLeft; blockLeft < tileRight; blockLeft += 5) {
            for(int blockTop = tileTop; blockTop < tileBottom; blockTop += 5) {
                int color;
                int blockRight = blockLeft + 5;
                int blockBottom = blockTop + 5;

                //get the color to draw this background chunk
                int rand = (int)(Math.random() * 3);
                if(rand == 2) {
                    color = lightGrey;
                }
                else if (rand == 1) {
                    color = medGrey;
                }
                else {
                    color = darkGrey;
                }

                //keep the right and bottom from overflowing the tile's space
                if(blockRight > tileRight) {
                    blockRight = tileRight;
                }
                if(blockBottom > tileBottom) {
                    blockBottom = tileBottom;
                }

                Paint toColor = new Paint();
                toColor.setColor(color);
                canvas.drawRect(blockLeft, blockTop, blockRight, blockBottom, toColor);
            }
        }//draw background


    }//drawTile

    /**
     * setBoardToDraw
     *
     * takes a board and sets that to be drawn on the canvas
     *
     * @param toDraw - the board to be drawn
     */
    public void setBoardToDraw(Board toDraw) {
        board = toDraw;
    }//setBoardToDraw

    /**
     * startShiftAnimation
     *
     * starts the thread to animate a shifting row of tiles, the setBoardToDraw method
     * should have already been called to update the board to the post-move state
     *
     * @param insertColumn - column that the tile was inserted in
     * @param insertRow - row that the tile was inserted in
     */
    public void startShiftAnimation(int insertColumn, int insertRow) {

    }//startShiftAnimation
}
