package group2.cs301.labyrinthgame.Labyrinth;

import group2.cs301.labyrinthgame.Game.GamePlayer;
import group2.cs301.labyrinthgame.Game.actionMsg.GameAction;

/**
 * @author G. Emily Nitzberg, Brendan Thomas, Ben Rumptz, Andrew Williams
 * @version November 9, 2015
 */
public class RotateTileAction extends GameAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public RotateTileAction(GamePlayer player) {
        super(player);
    } //ctor
}
