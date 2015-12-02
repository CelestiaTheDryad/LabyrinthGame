package group2.cs301.labyrinthgame.Labyrinth;

import android.media.Image;
import android.os.Handler;
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
 * @author Created by Andrew Williams
 * @version 11/18/15.
 */
public class LabyrinthGameHumanPlayer extends GameHumanPlayer {


    private GameMainActivity myActivity; // the current activity

    private LabyrinthGameState labyrinthGameState;

    private LabyrinthSurfaceView surfView;
    private ImageView extraTileBase;
    private ImageView extraTileTreasure;
    private ImageView extraTileHighlight;
    private ImageButton extraTileButton;
    private TextView playerTurnDisplay;
    private TextView targetCountDisplay;
    private ImageView targetDisplay;


    public LabyrinthGameHumanPlayer(String name) {super(name);}//constructor

    @Override
    public View getTopView() {
        return null;
    }

    @Override
    public void receiveInfo(GameInfo info) {
        if(info instanceof LabyrinthGameState) {
            labyrinthGameState = (LabyrinthGameState)info;
            updateGUI();
        }
    }

    private void updateGUI() {
        surfView.setBoardToDraw(labyrinthGameState.getGameBoard());
        surfView.setPlayerData(labyrinthGameState.getPlayers());
    }


    @Override
    public void setAsGui(GameMainActivity activity) {
        myActivity = activity;
        surfView = (LabyrinthSurfaceView) myActivity.findViewById(R.id.GameBoard);
        extraTileBase = (ImageView) myActivity.findViewById(R.id.extra_tile_base);
        extraTileHighlight = (ImageView) myActivity.findViewById(R.id.extra_tile_highlight);
        extraTileTreasure = (ImageView) myActivity.findViewById(R.id.extra_tile_treasure);
        extraTileButton = (ImageButton) myActivity.findViewById(R.id.extra_tile_button);
        playerTurnDisplay = (TextView) myActivity.findViewById(R.id.turnView);
        targetCountDisplay = (TextView) myActivity.findViewById(R.id.targetCountView);
        targetDisplay = (ImageView) myActivity.findViewById(R.id.targetView);
    }
}















