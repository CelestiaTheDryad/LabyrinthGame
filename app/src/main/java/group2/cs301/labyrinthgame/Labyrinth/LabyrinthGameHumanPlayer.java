package group2.cs301.labyrinthgame.Labyrinth;

import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import group2.cs301.labyrinthgame.Game.GameHumanPlayer;
import group2.cs301.labyrinthgame.Game.GameMainActivity;
import group2.cs301.labyrinthgame.Game.infoMsg.GameInfo;
import group2.cs301.labyrinthgame.Game.infoMsg.IllegalMoveInfo;
import group2.cs301.labyrinthgame.Game.infoMsg.NotYourTurnInfo;
import group2.cs301.labyrinthgame.Game.infoMsg.TimerInfo;
import group2.cs301.labyrinthgame.R;

/**
 * @author G. Emily Nitzberg, Ben Rumptz, Brendan Thomas, Andrew Williams
 * @version December 1, 2015
 */
public class LabyrinthGameHumanPlayer extends GameHumanPlayer implements View.OnClickListener, View.OnTouchListener {


    private GameMainActivity myActivity; // the current activity

    private LabyrinthGameState labyrinthGameState;

    private LabyrinthSurfaceView surfView;
    private ImageView extraTileBase;
    private ImageView extraTileTreasure;
    private ImageView extraTileHighlight;
    private TextView playerTurnDisplay;
    private TextView targetCountDisplay;
    private ImageView targetDisplay;
    private Button nextTurnButton;
    private int curTreasure;
    private TextView selectTile;
    private boolean doAction;
    private boolean doAnim;
    private int lastStateStage;
    private Button gameRules;

    private int[] treasuresResources;

    public LabyrinthGameHumanPlayer(String name) {
        super(name);

        treasuresResources = new int[25];
        treasuresResources[0] = R.drawable.tile_blank;
        treasuresResources[1] = R.drawable.treasure_alarm_bell;
        treasuresResources[2] = R.drawable.treasure_anchor;
        treasuresResources[3] = R.drawable.treasure_bear;
        treasuresResources[4] = R.drawable.treasure_beer;
        treasuresResources[5] = R.drawable.treasure_black_board;
        treasuresResources[6] = R.drawable.treasure_books;
        treasuresResources[7] = R.drawable.treasure_bow;
        treasuresResources[8] = R.drawable.treasure_briefcase;
        treasuresResources[9] = R.drawable.treasure_cake;
        treasuresResources[10] = R.drawable.treasure_candy;
        treasuresResources[11] = R.drawable.treasure_cash_register;
        treasuresResources[12] = R.drawable.treasure_clock;
        treasuresResources[13] = R.drawable.treasure_dna;
        treasuresResources[14] = R.drawable.treasure_dog;
        treasuresResources[15] = R.drawable.treasure_eagle;
        treasuresResources[16] = R.drawable.treasure_grapes;
        treasuresResources[17] = R.drawable.treasure_microscope;
        treasuresResources[18] = R.drawable.treasure_piggy_bank;
        treasuresResources[19] = R.drawable.treasure_pile_of_gold;
        treasuresResources[20] = R.drawable.treasure_pill;
        treasuresResources[21] = R.drawable.treasure_spaceship;
        treasuresResources[22] = R.drawable.treasure_squirrel;
        treasuresResources[23] = R.drawable.treasure_table_lamp;
        treasuresResources[24] = R.drawable.treasure_telescope;


    }//constructor

    @Override
    public View getTopView() {
        return null;
    }

    @Override
    public void receiveInfo(GameInfo info) {
        int i = 0;

        //update everything if it's a gameState
        if(info instanceof LabyrinthGameState) {
            labyrinthGameState = (LabyrinthGameState)info;
            doAction = true;
            if(labyrinthGameState.getStage() == LabyrinthGameState.MOVING
                    && lastStateStage != LabyrinthGameState.MOVING) {
                doAnim = true;
            }
            if(labyrinthGameState.getStage() != lastStateStage) {
                lastStateStage = labyrinthGameState.getStage();
            }
            updateGUI();
        }
    }

    private void updateGUI() {
        surfView.setBoardToDraw(labyrinthGameState.getGameBoard());
        surfView.setPlayerData(labyrinthGameState.getPlayers());

        String playerColor;

        if(labyrinthGameState.getCurrentPlayer() == 0) {
            playerColor = "red";
        }
        else if (labyrinthGameState.getCurrentPlayer() == 1) {
            playerColor = "blue";
        }
        else if(labyrinthGameState.getCurrentPlayer() == 2) {
            playerColor = "orange";
        }
        else {
            playerColor = "green";
        }

        playerTurnDisplay.setText("It is the " + playerColor + " player's turn.");
        curTreasure = labyrinthGameState.getPlayers().get(labyrinthGameState.getCurrentPlayer()).getCurrentTreasure();
        targetDisplay.setImageResource(treasuresResources[curTreasure]);
        int remainingTreasures = labyrinthGameState.getCurrentPlayerData().getRemainingTreasures();
        if(labyrinthGameState.getCurrentPlayer() > 0) { //todo: Make this work with multiple humans
            if (remainingTreasures > 0) {
                targetCountDisplay.setText("The " + playerColor + " player has " + remainingTreasures + " treasures to collect.");
            } else {
                targetCountDisplay.setText("The " + playerColor + " player must return to their starting position.");
            }
        } else {
            if (remainingTreasures > 0) {
                targetCountDisplay.setText("You have " + remainingTreasures + " treasures to collect.");
            } else {
                targetCountDisplay.setText("You must return to your starting position.");
            }
        }

        Tile extraTile = labyrinthGameState.getGameBoard().getExtraTile();
        if(extraTile.getType() == Tile.CORNER) {
            if(extraTile.getRotation() == Tile.UP) {
                extraTileBase.setImageResource(R.drawable.tile_corner_rot_0);
            }
            else if(extraTile.getRotation() == Tile.RIGHT) {
                extraTileBase.setImageResource(R.drawable.tile_corner_rot_1);
            }
            else if(extraTile.getRotation() == Tile.DOWN) {
                extraTileBase.setImageResource(R.drawable.tile_corner_rot_2);
            }
            else if(extraTile.getRotation() == Tile.LEFT) {
                extraTileBase.setImageResource(R.drawable.tile_corner_rot_3);
            }
        }
        else if(extraTile.getType() == Tile.TEE) {
            if(extraTile.getRotation() == Tile.UP) {
                extraTileBase.setImageResource(R.drawable.tile_cross_rot_3);
            }
            else if(extraTile.getRotation() == Tile.RIGHT) {
                extraTileBase.setImageResource(R.drawable.tile_cross_rot_0);
            }
            else if(extraTile.getRotation() == Tile.DOWN) {
                extraTileBase.setImageResource(R.drawable.tile_cross_rot_1);
            }
            else if(extraTile.getRotation() == Tile.LEFT) {
                extraTileBase.setImageResource(R.drawable.tile_cross_rot_2);
            }
        }
        else if(extraTile.getType() == Tile.LINE) {
            if(extraTile.getRotation() == Tile.UP) {
                extraTileBase.setImageResource(R.drawable.tile_line_rot_0);
            }
            else if(extraTile.getRotation() == Tile.RIGHT) {
                extraTileBase.setImageResource(R.drawable.tile_line_rot_1);
            }
            else if(extraTile.getRotation() == Tile.DOWN) {
                extraTileBase.setImageResource(R.drawable.tile_line_rot_0);
            }
            else if(extraTile.getRotation() == Tile.LEFT) {
                extraTileBase.setImageResource(R.drawable.tile_line_rot_1);
            }
        }

        extraTileTreasure.setImageResource(treasuresResources[extraTile.getTreasure()]);

        if(extraTile.isHighlighted()) {
            extraTileHighlight.setImageResource(R.drawable.tile_highlight);
        }
        else {
            extraTileHighlight.setImageResource(R.drawable.tile_blank);
        }

        surfView.postInvalidate();

        if(doAnim) {
            surfView.startShiftAnimation(labyrinthGameState.getLastXInserted(), labyrinthGameState.getLastYInserted());
            doAnim = false;
        }

        //set info text
        if(labyrinthGameState.getStage() == LabyrinthGameState.INSERTING) {
            selectTile.setText("Rotate and insert the extra tile.");
        }
        else if(labyrinthGameState.getStage() == LabyrinthGameState.MOVING) {
            selectTile.setText("Please select a tile to move.");
        }
        else if(labyrinthGameState.getStage() == LabyrinthGameState.ENDING) {
            selectTile.setText("Press NEXT TURN to end your turn.");
        }

    }


    @Override
    public void setAsGui(GameMainActivity activity) {
        myActivity = activity;
        myActivity.setContentView(R.layout.labyrinth_activity_main);
        surfView = (LabyrinthSurfaceView) myActivity.findViewById(R.id.GameBoard);
        extraTileBase = (ImageView) myActivity.findViewById(R.id.extra_tile_base);
        extraTileHighlight = (ImageView) myActivity.findViewById(R.id.extra_tile_highlight);
        extraTileTreasure = (ImageView) myActivity.findViewById(R.id.extra_tile_treasure);
        playerTurnDisplay = (TextView) myActivity.findViewById(R.id.turnView);
        targetCountDisplay = (TextView) myActivity.findViewById(R.id.targetCountView);
        targetDisplay = (ImageView) myActivity.findViewById(R.id.targetView);
        nextTurnButton = (Button) myActivity.findViewById(R.id.NextTurnButton);
        extraTileBase = (ImageView) myActivity.findViewById(R.id.extra_tile_base);
        extraTileTreasure = (ImageView) myActivity.findViewById(R.id.extra_tile_treasure);
        extraTileHighlight = (ImageView) myActivity.findViewById(R.id.extra_tile_highlight);
        selectTile = (TextView) myActivity.findViewById(R.id.select_tile_text);
        gameRules = (Button) myActivity.findViewById(R.id.game_rules);


        nextTurnButton.setOnClickListener(this);
        surfView.setOnTouchListener(this);

        extraTileBase.setOnTouchListener(this);
        extraTileTreasure.setOnTouchListener(this);
        extraTileHighlight.setOnTouchListener(this);
        gameRules.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v == nextTurnButton && labyrinthGameState.getStage() == LabyrinthGameState.ENDING
                && doAction && !surfView.animRunning()) {
            game.sendAction(new NextTurnAction(this));
            Log.println(Log.VERBOSE, "", "nextTurn");
            doAction = false;
        }

        if(v == gameRules)
        {
            myActivity.startActivity(new Intent(myActivity, GameRules.class));
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {



        if(v == surfView) {

            int height = v.getHeight();
            int width = v.getWidth();

            int touchY = (int)(( ((double)event.getY())/((double)height) ) * 9 ) ;
            int touchX = (int)(( ((double)event.getX())/((double)width) ) * 9 ) ;

            Log.println(Log.VERBOSE, "", ""+ touchX + ", " + touchY);

            if(touchY <= 0 || touchY >= 8 || touchX <= 0 || touchX >= 8) {
                return false;
            }

            touchY--;
            touchX--;
            //0-6

            if(labyrinthGameState.getStage() == LabyrinthGameState.INSERTING
                    && doAction && !surfView.animRunning()) {
                if(labyrinthGameState.getGameBoard().getTile(touchX, touchY).isHighlighted()) {
                    game.sendAction(new InsertTileAction(this, touchX, touchY));
                    Log.println(Log.VERBOSE, "", "insert");
                    doAction = false;
                }
            }
            else if(labyrinthGameState.getStage() == LabyrinthGameState.MOVING
                    && doAction && !surfView.animRunning()) {
                if(labyrinthGameState.getGameBoard().getTile(touchX, touchY).isHighlighted()) {
                    game.sendAction(new MoveAction(this, touchX, touchY));
                    Log.println(Log.VERBOSE, "", "move");
                    doAction = false;
                }
            }
            else {
                return false;
            }

            return true;
        }
        else if((v == extraTileBase || v == extraTileTreasure || v == extraTileHighlight)&&
                labyrinthGameState.getStage() == LabyrinthGameState.INSERTING
                && doAction && !surfView.animRunning()) {
            game.sendAction(new RotateTileAction(this));
            Log.println(Log.VERBOSE, "", "rotate");
            doAction = false;
        }

        return false;
    }
}















