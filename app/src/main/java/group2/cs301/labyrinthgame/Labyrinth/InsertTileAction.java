package group2.cs301.labyrinthgame.Labyrinth;

import group2.cs301.labyrinthgame.Game.GamePlayer;
import group2.cs301.labyrinthgame.Game.actionMsg.GameAction;

/**
 * Created by R2-D2 on 11/5/15.
 */
public class InsertTileAction extends GameAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public InsertTileAction(GamePlayer player) {
        super(player);
    }
}
