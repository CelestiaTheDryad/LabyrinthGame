package group2.cs301.labyrinthgame.Labyrinth;

import android.os.Handler;
import android.view.View;

import group2.cs301.labyrinthgame.Game.GameHumanPlayer;
import group2.cs301.labyrinthgame.Game.GameMainActivity;
import group2.cs301.labyrinthgame.Game.infoMsg.GameInfo;
import group2.cs301.labyrinthgame.Game.infoMsg.IllegalMoveInfo;
import group2.cs301.labyrinthgame.Game.infoMsg.NotYourTurnInfo;
import group2.cs301.labyrinthgame.Game.infoMsg.TimerInfo;

/**
 * @author Created by Andrew Williams
 * @version 11/18/15.
 */
public class LabyrinthGameHumanPlayer extends GameHumanPlayer {


    private GameMainActivity myActivity; // the current activity

    private LabyrinthGameState labyrinthGameState;


    public LabyrinthGameHumanPlayer(String name) {super(name);}//constructor

    @Override
    public View getTopView() {
        return null;
    }

    @Override
    public void receiveInfo(GameInfo info) {
        if(info instanceof LabyrinthGameState) {
            labyrinthGameState = (LabyrinthGameState)info;
        }
    }


    @Override
    public void setAsGui(GameMainActivity activity) {

        myActivity = activity;




    }
}
