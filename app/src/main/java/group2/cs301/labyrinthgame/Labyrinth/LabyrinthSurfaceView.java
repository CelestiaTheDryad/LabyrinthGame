package group2.cs301.labyrinthgame.Labyrinth;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.SurfaceView;

import java.util.ArrayList;

import group2.cs301.labyrinthgame.R;

/**
 * @author G. Emily Nitzberg, Ben Rumptz, Brendan Thomas, Andrew Williams
 * @version December 14, 2015
 *
 * This class is the drawing surface for the game board
 */
public class LabyrinthSurfaceView extends SurfaceView {

    private int drawLeft;
    private int drawTop;
    private int drawSize;
    private int tileSize;
    private int borderSize = 6;
    private Board board;
    private ArrayList<PlayerData> players;
    private ShiftAnimation shiftAnim;
    private MoveAnimation moveAnim;
    private AnimationThread ticker;
    private Bitmap[] treasureImages;

    private int backColor;
    private int lightGrey;

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

        backColor = Color.rgb(112, 56, 13);
        lightGrey = Color.rgb(200,200,200);

        //set the background of the canvas to a nice brown
        setBackgroundColor(backColor);

        moveAnim = null;
        shiftAnim = null;
        ticker = null;

        //protect from null pointer crashes
        board = new Board();
        players = new ArrayList<PlayerData>();

        treasureImages = new Bitmap[24];
        treasureImages[0] = BitmapFactory.decodeResource(getResources(), R.drawable.treasure_alarm_bell);
        treasureImages[1] = BitmapFactory.decodeResource(getResources(), R.drawable.treasure_anchor);
        treasureImages[2] = BitmapFactory.decodeResource(getResources(), R.drawable.treasure_bear);
        treasureImages[3] = BitmapFactory.decodeResource(getResources(), R.drawable.treasure_beer);
        treasureImages[4] = BitmapFactory.decodeResource(getResources(), R.drawable.treasure_black_board);
        treasureImages[5] = BitmapFactory.decodeResource(getResources(), R.drawable.treasure_books);
        treasureImages[6] = BitmapFactory.decodeResource(getResources(), R.drawable.treasure_bow);
        treasureImages[7] = BitmapFactory.decodeResource(getResources(), R.drawable.treasure_briefcase);
        treasureImages[8] = BitmapFactory.decodeResource(getResources(), R.drawable.treasure_cake);
        treasureImages[9] = BitmapFactory.decodeResource(getResources(), R.drawable.treasure_candy);
        treasureImages[10] = BitmapFactory.decodeResource(getResources(), R.drawable.treasure_cash_register);
        treasureImages[11] = BitmapFactory.decodeResource(getResources(), R.drawable.treasure_clock);
        treasureImages[12] = BitmapFactory.decodeResource(getResources(), R.drawable.treasure_dna);
        treasureImages[13] = BitmapFactory.decodeResource(getResources(), R.drawable.treasure_dog);
        treasureImages[14] = BitmapFactory.decodeResource(getResources(), R.drawable.treasure_eagle);
        treasureImages[15] = BitmapFactory.decodeResource(getResources(), R.drawable.treasure_grapes);
        treasureImages[16] = BitmapFactory.decodeResource(getResources(), R.drawable.treasure_microscope);
        treasureImages[17] = BitmapFactory.decodeResource(getResources(), R.drawable.treasure_piggy_bank);
        treasureImages[18] = BitmapFactory.decodeResource(getResources(), R.drawable.treasure_pile_of_gold);
        treasureImages[19] = BitmapFactory.decodeResource(getResources(), R.drawable.treasure_pill);
        treasureImages[20] = BitmapFactory.decodeResource(getResources(), R.drawable.treasure_spaceship);
        treasureImages[21] = BitmapFactory.decodeResource(getResources(), R.drawable.treasure_squirrel);
        treasureImages[22] = BitmapFactory.decodeResource(getResources(), R.drawable.treasure_table_lamp);
        treasureImages[23] = BitmapFactory.decodeResource(getResources(), R.drawable.treasure_telescope);
    }

    private void calcStuff(Canvas canvas) {
        //get values for the canvas size and tile size
        int canvasHeight = canvas.getHeight();
        int canvasWidth = canvas.getWidth();
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
        //the difference of all the buffers should be evenly divisible by 9
        if ((canvasHeight - heightBuffer * 2) % 9 < 5) {
            genBuffer = (canvasHeight - heightBuffer * 2) % 9 + 9;
        }
        else {
            genBuffer = (canvasHeight - heightBuffer * 2) % 9;
        }

        //find out how big to draw the tiles
        drawSize = canvasHeight - heightBuffer - genBuffer;
        tileSize = drawSize / 9;
        drawTop = genBuffer + heightBuffer;
        drawLeft = genBuffer + widthBuffer;
    }

    @Override
    public void onDraw(Canvas canvas) {
        calcStuff(canvas);
        drawNormalBoard(canvas);

        //there should never be a time when both animations are running
        if (moveAnim != null) {
            //moveAnim.tick();
            //TODO do everything required for player movement animation
        }
        else if (shiftAnim != null) {
            shiftAnim.tick(canvas, this);
            if(shiftAnim.isOver()) {
                ticker.setStopped();
                shiftAnim = null;
            }
        }
        else {
            drawHighlights(canvas);
            drawPlayers(canvas);
        }
        drawInsertArrows(canvas);
        drawPlayerStarts(canvas);
    }//draw

    /**
     * drawInsertArrows
     *
     * draws the arrows on the board to show where tiles may be inserted
     *
     * @param canvas - canvas to draw on
     */
    private void drawInsertArrows(Canvas canvas) {
        drawArrowUp(canvas, 2);
        drawArrowUp(canvas, 4);
        drawArrowUp(canvas, 6);

        drawArrowDown(canvas, 2);
        drawArrowDown(canvas, 4);
        drawArrowDown(canvas, 6);

        drawArrowLeft(canvas, 2);
        drawArrowLeft(canvas, 4);
        drawArrowLeft(canvas, 6);

        drawArrowRight(canvas, 2);
        drawArrowRight(canvas, 4);
        drawArrowRight(canvas, 6);
    }

    private void drawArrowUp(Canvas canvas, int column) {
        int boxTop = drawTop + tileSize * 8;
        int boxLeft = drawLeft + tileSize * column;

        Path arrow = new Path();
        arrow.moveTo(boxLeft + 2 * (tileSize / 5), boxTop + 3 * (tileSize / 4));
        arrow.lineTo(boxLeft + 2 * (tileSize / 5), boxTop + (tileSize / 2));
        arrow.lineTo(boxLeft + (tileSize / 5), boxTop + (tileSize / 2));
        arrow.lineTo(boxLeft + (tileSize / 2), boxTop + (tileSize / 4));
        arrow.lineTo(boxLeft + 4 * (tileSize / 5), boxTop + (tileSize / 2));
        arrow.lineTo(boxLeft + 3 * (tileSize / 5), boxTop + (tileSize / 2));
        arrow.lineTo(boxLeft + 3 * (tileSize / 5), boxTop + 3 * (tileSize / 4));
        arrow.lineTo(boxLeft + 2 * (tileSize / 5), boxTop + 3 * (tileSize / 4));

        Paint toColor = new Paint();
        toColor.setColor(lightGrey);
        canvas.drawPath(arrow, toColor);
    }

    private void drawArrowDown(Canvas canvas, int column) {
        int boxTop = drawTop;
        int boxLeft = drawLeft + tileSize * column;

        Path arrow = new Path();
        arrow.moveTo(boxLeft + 2 * (tileSize / 5), boxTop + (tileSize / 4));
        arrow.lineTo(boxLeft + 2 * (tileSize / 5), boxTop + (tileSize / 2));
        arrow.lineTo(boxLeft + (tileSize / 5), boxTop + (tileSize / 2));
        arrow.lineTo(boxLeft + (tileSize / 2), boxTop + 3 * (tileSize / 4));
        arrow.lineTo(boxLeft + 4 * (tileSize / 5), boxTop + (tileSize / 2));
        arrow.lineTo(boxLeft + 3 * (tileSize / 5), boxTop + (tileSize / 2));
        arrow.lineTo(boxLeft + 3 * (tileSize / 5), boxTop + (tileSize / 4));
        arrow.lineTo(boxLeft + 2 * (tileSize / 5), boxTop + (tileSize / 4));

        Paint toColor = new Paint();
        toColor.setColor(lightGrey);
        canvas.drawPath(arrow, toColor);
    }

    private void drawArrowLeft(Canvas canvas, int row) {
        int boxTop = tileSize * row + drawTop;
        int boxLeft = drawLeft + tileSize * 8;

        Path arrow = new Path();
        arrow.moveTo(boxLeft + 3 * (tileSize / 4), boxTop + 2 * (tileSize / 5));
        arrow.lineTo(boxLeft + (tileSize / 2), boxTop + 2 * (tileSize / 5));
        arrow.lineTo(boxLeft + (tileSize / 2), boxTop + (tileSize / 5));
        arrow.lineTo(boxLeft + (tileSize / 4), boxTop + (tileSize / 2));
        arrow.lineTo(boxLeft + (tileSize / 2), boxTop + 4 * (tileSize / 5));
        arrow.lineTo(boxLeft + (tileSize / 2), boxTop + 3 * (tileSize / 5));
        arrow.lineTo(boxLeft + 3 * (tileSize / 4), boxTop + 3 * (tileSize / 5));
        arrow.lineTo(boxLeft + 3 * (tileSize / 4), boxTop + 2 * (tileSize / 5));

        Paint toColor = new Paint();
        toColor.setColor(lightGrey);
        canvas.drawPath(arrow, toColor);
    }

    private void drawArrowRight(Canvas canvas, int row) {
        int boxTop = tileSize * row + drawTop;
        int boxLeft = drawLeft;

        Path arrow = new Path();
        arrow.moveTo(boxLeft + (tileSize / 4), boxTop + 2 * (tileSize / 5));
        arrow.lineTo(boxLeft + (tileSize / 2), boxTop + 2 * (tileSize / 5));
        arrow.lineTo(boxLeft + (tileSize / 2), boxTop + (tileSize / 5));
        arrow.lineTo(boxLeft + 3 * (tileSize / 4), boxTop + (tileSize / 2));
        arrow.lineTo(boxLeft + (tileSize / 2), boxTop + 4 * (tileSize / 5));
        arrow.lineTo(boxLeft + (tileSize / 2), boxTop + 3 * (tileSize / 5));
        arrow.lineTo(boxLeft + (tileSize / 4), boxTop + 3 * (tileSize / 5));
        arrow.lineTo(boxLeft + (tileSize / 4), boxTop + 2 * (tileSize / 5));

        Paint toColor = new Paint();
        toColor.setColor(lightGrey);
        canvas.drawPath(arrow, toColor);
    }

    /**
     * drawPlayerStarts
     *
     * highlights each player's original tile
     *
     * @param canvas - canvas to draw on
     */
    private void drawPlayerStarts(Canvas canvas) {
        Paint color = new Paint();

        int tileLeft;
        int tileRight;
        int tileTop;
        int tileBottom;

        for (PlayerData player : players) {
            if (player.getPlayerColor() == Color.RED) {
                color.setColor(Color.RED);

                tileLeft = drawLeft + tileSize + (borderSize / 2);
                tileRight = drawLeft + 2 * tileSize - (borderSize / 2);
                tileTop = drawTop + tileSize + (borderSize / 2);
                tileBottom = drawTop + 2 * tileSize - (borderSize / 2);
            }
            else if (player.getPlayerColor() == Color.BLUE) {
                color.setColor(Color.BLUE);

                tileLeft = drawLeft + 7 * tileSize + (borderSize / 2);
                tileRight = drawLeft + 8 * tileSize - (borderSize / 2);
                tileTop = drawTop + tileSize + (borderSize / 2);
                tileBottom = drawTop + 2 * tileSize - (borderSize / 2);
            }
            else if (player.getPlayerColor() == Color.rgb(255,130,0)) {
                color.setColor(Color.rgb(255,130,0));

                tileLeft = drawLeft + tileSize + (borderSize / 2);
                tileRight = drawLeft + 2 * tileSize - (borderSize / 2);
                tileTop = drawTop + 7 * tileSize + (borderSize / 2);
                tileBottom = drawTop + 8 * tileSize - (borderSize / 2);
            }
            else {
                color.setColor(Color.GREEN);

                tileLeft = drawLeft + 7 * tileSize + (borderSize / 2);
                tileRight = drawLeft + 8 * tileSize - (borderSize / 2);
                tileTop = drawTop + 7 * tileSize + (borderSize / 2);
                tileBottom = drawTop + 8 * tileSize - (borderSize / 2);
            }

            canvas.drawRect(tileLeft, tileTop, tileRight, tileTop + borderSize, color);
            canvas.drawRect(tileLeft, tileTop, tileLeft + borderSize, tileBottom, color);
            canvas.drawRect(tileRight - borderSize, tileTop, tileRight, tileBottom, color);
            canvas.drawRect(tileLeft, tileBottom - borderSize, tileRight, tileBottom, color);
        }
    }

    /**
     * drawHighlights
     *
     * draws the highlights on all of the tiles that need to be highlighted
     *
     * @param canvas - canvas to draw the highlights on
     */
    public void drawHighlights(Canvas canvas) {
        for (int column = 1; column < 8; column++) {
            for (int row = 1; row < 8; row++) {
                if (board.getTile(column - 1, row -1).isHighlighted()) {
                    //find pixel boundaries to draw the tile in
                    int tileLeft = drawLeft + column * tileSize + (borderSize / 2);
                    int tileRight = drawLeft + (column + 1) * tileSize - (borderSize / 2);
                    int tileTop = drawTop + row * tileSize + (borderSize / 2);
                    int tileBottom = drawTop + (row + 1) * tileSize - (borderSize / 2);

                    Paint color = new Paint();
                    color.setColor(Color.rgb(249, 4, 148));

                    canvas.drawRect(tileLeft, tileTop, tileRight, tileTop + borderSize, color);
                    canvas.drawRect(tileLeft, tileTop, tileLeft + borderSize, tileBottom, color);
                    canvas.drawRect(tileRight - borderSize, tileTop, tileRight, tileBottom, color);
                    canvas.drawRect(tileLeft, tileBottom - borderSize, tileRight, tileBottom, color);
                }
            }
        }
    }//drawHighlights

    public void drawPlayers(Canvas canvas) {
        for(PlayerData player: players) {
            int column = player.getXposition();
            int row = player.getYposition();

            int columnOffset;
            int rowOffset;

            if (player.getPlayerColor() == Color.RED) {
                columnOffset = tileSize / 4 * -1;
                rowOffset = tileSize / 4 * -1;
            }
            else if (player.getPlayerColor() == Color.BLUE) {
                columnOffset = tileSize / 4;
                rowOffset = tileSize / 4 * -1;
            }
            else if (player.getPlayerColor() == Color.GREEN) {
                columnOffset = tileSize / 4;
                rowOffset = tileSize / 4;
            }
            else {
                columnOffset = tileSize / 4 * -1;
                rowOffset = tileSize / 4;
            }

            Paint color = new Paint();
            color.setColor(player.getPlayerColor());

            int xPos = drawLeft + (column + 1) * tileSize + columnOffset + tileSize / 2;
            int yPos = drawTop + (row + 1) * tileSize + rowOffset + tileSize / 2;

            canvas.drawCircle(xPos, yPos, 15, color);
        }
    }

    /**
     * drawNormalBoard
     *
     * draws the board with all the tiles in their permanent spot
     *
     * @param canvas - canvas to draw the tiles on
     */
    public void drawNormalBoard(Canvas canvas) {
        for (int column = 1; column < 8; column ++) {
            for (int row = 1; row < 8; row++) {
                drawTile(board.getTile(column-1, row-1), column, row, 0, 0, canvas);
            }
        }
    }

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
    public void drawTile(Tile toDraw, int column, int row, int columnOffset, int rowOffset, Canvas canvas) {

        //find pixel boundaries to draw the tile in
        int tileLeft = drawLeft + column * tileSize + columnOffset + (borderSize / 2);
        int tileRight = drawLeft + (column + 1) * tileSize + columnOffset - (borderSize / 2);
        int tileTop = drawTop + row * tileSize + rowOffset + (borderSize / 2);
        int tileBottom = drawTop + (row + 1) * tileSize + rowOffset - (borderSize / 2);

        //draw the background
        //disabled because a new random background each time is too much computation
        /*
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
        */
        Paint color = new Paint();
        color.setColor(lightGrey);
        canvas.drawRect(tileLeft, tileTop, tileRight, tileBottom, color);

        //draw the paths
        int centerTop = (tileSize / 3) + tileTop - (borderSize / 2);
        int centerLeft = (tileSize / 3) + tileLeft - (borderSize / 2);
        int centerRight = centerLeft + (tileSize / 3);
        int centerBottom = centerTop + (tileSize / 3);

        Paint lightOrange = new Paint();
        lightOrange.setColor(Color.rgb(236, 196, 34));
        Paint darkOrange = new Paint();
        darkOrange.setColor(Color.rgb(219,154,34));

        //center
        canvas.drawRect(centerLeft, centerTop, centerRight, centerBottom, lightOrange);

        //handle top side connection
        if(toDraw.isConnected(Tile.UP)) {
            canvas.drawRect(centerLeft - borderSize, tileTop, centerLeft,  centerTop, darkOrange);
            canvas.drawRect(centerLeft, tileTop, centerRight, centerTop, lightOrange);
            canvas.drawRect(centerRight, tileTop, centerRight + borderSize, centerTop, darkOrange);
        }
        else {
            canvas.drawRect(centerLeft - borderSize, centerTop - borderSize,centerRight + borderSize, centerTop, darkOrange);
        }

        //handle left side connection
        if(toDraw.isConnected(Tile.LEFT)) {
            canvas.drawRect(tileLeft, centerTop - borderSize, centerLeft, centerTop, darkOrange);
            canvas.drawRect(tileLeft, centerTop, centerLeft, centerBottom, lightOrange);
            canvas.drawRect(tileLeft, centerBottom, centerLeft, centerBottom + borderSize, darkOrange);
        }
        else {
            canvas.drawRect(centerLeft - borderSize, centerTop - borderSize, centerLeft, centerBottom + borderSize, darkOrange);
        }

        //handle right side connection
        if(toDraw.isConnected(Tile.RIGHT)) {
            canvas.drawRect(centerRight, centerTop - borderSize, tileRight, centerTop, darkOrange);
            canvas.drawRect(centerRight, centerTop, tileRight, centerBottom, lightOrange);
            canvas.drawRect(centerRight, centerBottom, tileRight, centerBottom + borderSize, darkOrange);
        }
        else {
            canvas.drawRect(centerRight, centerTop - borderSize, centerRight + borderSize, centerBottom + borderSize, darkOrange);
        }

        //handle bottom side connection
        if(toDraw.isConnected(Tile.DOWN)) {
            canvas.drawRect(centerLeft - borderSize, centerBottom, centerLeft, tileBottom, darkOrange);
            canvas.drawRect(centerLeft, centerBottom, centerRight,tileBottom, lightOrange);
            canvas.drawRect(centerRight, centerBottom, centerRight + borderSize, tileBottom, darkOrange);
        }
        else {
            canvas.drawRect(centerLeft - borderSize, centerBottom, centerRight + borderSize, centerBottom + borderSize, darkOrange);
        }

        if(toDraw.getTreasure() != 0) {
            canvas.drawBitmap(treasureImages[toDraw.getTreasure() - 1], centerLeft - 5, centerTop - 5, null);
        }

    }//drawTile

    public void drawBlank(Canvas canvas, boolean row, int num) {
        Paint color = new Paint();
        color.setColor(backColor);
        if(row) {
            canvas.drawRect(0, drawTop + num * tileSize, canvas.getWidth(), drawTop + (num + 1) * tileSize, color);
        }
        else {
            canvas.drawRect(drawLeft + num * tileSize, 0, drawLeft + (num + 1) * tileSize, canvas.getHeight(), color);
        }
    }

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
     * setPlayerData
     *
     * gives a set of player data to the view for drawing
     *
     * @param list - the arraylist of playerdata to use
     */
    public void setPlayerData(ArrayList<PlayerData> list) {
        players = list;
    }//setPlayerData

    /**
     * startShiftAnimation
     *
     * starts the thread to animate a shifting row of tiles, the setBoardToDraw method
     * should have already been called to update the board to the post-move state
     *
     * @param insertColumn - column that the tile was inserted in
     * @param insertRow - row that the tile was inserted in
     * @return - returns true if an animation was started, false if it was not started
     */
    public boolean startShiftAnimation(int insertColumn, int insertRow) {
        if(animRunning()) {
            return false;
        }
        shiftAnim = new ShiftAnimation(insertColumn, insertRow, board, tileSize);
        ticker = new AnimationThread(this);
        ticker.start();
        return true;
    }//startShiftAnimation

    /**
     * animRunning
     *
     * tells if an animation is currently running by checking the animations
     *
     * @return - true if there is an animation going, false if not
     */
    public boolean animRunning() {
        return shiftAnim != null || moveAnim != null;
    }//animRunning
}
