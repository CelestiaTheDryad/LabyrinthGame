package group2.cs301.labyrinthgame.Game.actionMsg;

import group2.cs301.labyrinthgame.Game.GamePlayer;

/**
 * @author Ben Rumptz
 * @version 11/1/15
 */
public class RotateTileGameAction extends GameAction {

    // to satisfy the Serializable interface
    private static final long serialVersionUID = 237810L;

    public RotateTileGameAction(GamePlayer p){
        super(p);
    }
}
