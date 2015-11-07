package group2.cs301.labyrinthgame.Labyrinth;

import junit.framework.Assert.*;
import junit.framework.TestCase;

/**
 * Created by Andrew Williams on 11/7/15.
 */
public class LabyrinthGameStateTest extends TestCase {

    public void testInsertTile() throws Exception {
    }

    public void testRotateTile() throws Exception {

        LabyrinthGameState labyrinthGameState = new LabyrinthGameState();
        Tile origTile = labyrinthGameState.getGameBoard().getExtraTile();

        //assertEquals(origTile.tickTile(), labyrinthGameState.rotateTile());
    }

    public void testMove() throws Exception {

    }
}