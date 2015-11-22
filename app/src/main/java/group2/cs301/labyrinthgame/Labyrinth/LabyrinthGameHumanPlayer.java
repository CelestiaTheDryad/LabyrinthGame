package group2.cs301.labyrinthgame.Labyrinth;

import android.view.View;

import group2.cs301.labyrinthgame.Game.GameHumanPlayer;
import group2.cs301.labyrinthgame.Game.GameMainActivity;
import group2.cs301.labyrinthgame.Game.infoMsg.GameInfo;

/**
 * Created by R2-D2 on 11/18/15.
 */
public class LabyrinthGameHumanPlayer extends GameHumanPlayer {


    public LabyrinthGameHumanPlayer(String name) {
        super(name);
    }//ctor

    @Override
    public View getTopView() {
        return null;
    }

    @Override
    public void receiveInfo(GameInfo info) {

    }


    @Override
    public void setAsGui(GameMainActivity activity) {

    }
}
