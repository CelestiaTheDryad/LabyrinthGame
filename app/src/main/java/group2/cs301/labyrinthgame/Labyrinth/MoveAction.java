package group2.cs301.labyrinthgame.Labyrinth;

import group2.cs301.labyrinthgame.Game.GamePlayer;
import group2.cs301.labyrinthgame.Game.actionMsg.GameAction;

/**
 * @author G. Emily Nitzberg, Brendan Thomas, Ben Rumptz, Andrew Williams
 * @version November 9, 2015
 */
public class MoveAction extends GameAction {

    //coordinates to move to
    private int x;
    private int y;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     * @param xx the y coord
     * @param yy the x coord
     */
    public MoveAction(GamePlayer player, int xx, int yy) {
        super(player);
        int x = xx;
        int y = yy;
    }//ctor

    //basic getter functions
    public int getX() { return x; }
    public int getY() { return y; }
}
