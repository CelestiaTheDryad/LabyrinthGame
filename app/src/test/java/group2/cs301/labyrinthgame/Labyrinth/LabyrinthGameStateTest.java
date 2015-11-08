package group2.cs301.labyrinthgame.Labyrinth;

import junit.framework.TestCase;

/**
 * Created by Andrew Williams on 11/7/15.
 */
public class LabyrinthGameStateTest extends TestCase {

    public void testInsertTile() throws Exception {

    }

    public void testRotateTile() throws Exception {
        LabyrinthGameState labyrinthGameState = new LabyrinthGameState(4);
        Tile origTile = labyrinthGameState.getGameBoard().getExtraTile();
        origTile.tickTile();
        labyrinthGameState.rotateTile();
        assertEquals(origTile.getRotation(), labyrinthGameState.getGameBoard().getExtraTile().getRotation());
    }

    public void testMove() throws Exception {
        LabyrinthGameState labyrinthGameState = new LabyrinthGameState(4);
        labyrinthGameState.move(3,6);
        int curPlayer = labyrinthGameState.getCurrentPlayer();
        Tile tile = labyrinthGameState.getGameBoard().getTile(3,6);
        assertTrue(tile.getPlayer(curPlayer));
    }
}