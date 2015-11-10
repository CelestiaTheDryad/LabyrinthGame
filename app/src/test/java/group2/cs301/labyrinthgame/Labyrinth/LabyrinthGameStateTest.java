package group2.cs301.labyrinthgame.Labyrinth;

import junit.framework.TestCase;

/**
 * @author Andrew Williams, Brendan Thomas, Ben Rumptz
 * @version November 8, 2015
 */
public class LabyrinthGameStateTest extends TestCase {

    //test insert into (0,1)
    public void testInsertTile() throws Exception {
        LabyrinthGameState gs = new LabyrinthGameState(4);
        //x and y should be 0 here
        int lastXBeforeInsert = gs.getLastXInserted();
        int lastYBeforeInsert = gs.getLastYInserted();
        //test that our lastX,lastY are correct
        assertEquals(0, lastXBeforeInsert);
        assertEquals(0, lastYBeforeInsert);
        Tile startExtraTile = gs.getGameBoard().getExtraTile();
        Tile initFirstTile = gs.getGameBoard().getTile(0, 1);
        Tile initSecondTile = gs.getGameBoard().getTile(1, 1);
        Tile initThirdTile = gs.getGameBoard().getTile(2, 1);
        Tile initFourthTile = gs.getGameBoard().getTile(3, 1);
        Tile initFifthTile = gs.getGameBoard().getTile(4, 1);
        Tile initSixthTile = gs.getGameBoard().getTile(5, 1);
        Tile initSeventhTile = gs.getGameBoard().getTile(6, 1);
        //call insert here
        gs.insertTile(0,1);
        //check that it worked below here
        int lastXAfterInsert = gs.getLastXInserted();
        int lastYAfterInsert = gs.getLastYInserted();
        //test that our lastX,lastY are correct
        assertEquals(0, lastXAfterInsert);
        assertEquals(1, lastYAfterInsert);
        Tile finalExtraTile = gs.getGameBoard().getExtraTile();
        Tile finalFirstTile = gs.getGameBoard().getTile(0, 1);
        Tile finalSecondTile = gs.getGameBoard().getTile(1, 1);
        Tile finalThirdTile = gs.getGameBoard().getTile(2, 1);
        Tile finalFourthTile = gs.getGameBoard().getTile(3, 1);
        Tile finalFifthTile = gs.getGameBoard().getTile(4, 1);
        Tile finalSixthTile = gs.getGameBoard().getTile(5, 1);
        Tile finalSeventhTile = gs.getGameBoard().getTile(6, 1);
        //test that our tiles shifted correctly
        assertEquals(startExtraTile, finalFirstTile);
        assertEquals(initFirstTile, finalSecondTile);
        assertEquals(initSecondTile, finalThirdTile);
        assertEquals(initThirdTile, finalFourthTile);
        assertEquals(initFourthTile, finalFifthTile);
        assertEquals(initFifthTile, finalSixthTile);
        assertEquals(initSixthTile, finalSeventhTile);
        assertEquals(initSeventhTile, finalExtraTile);
    }//testInsertTile

    //test the rotate tile method for rotating the spare tile
    public void testRotateTile() throws Exception {
        LabyrinthGameState labyrinthGameState = new LabyrinthGameState(4);
        Tile origTile = labyrinthGameState.getGameBoard().getExtraTile();
        origTile.tickTile();
        labyrinthGameState.rotateTile();
        assertEquals(origTile.getRotation(), labyrinthGameState.getGameBoard().getExtraTile().getRotation());
    }

    //tests the move method for moving a player
    public void testMove() throws Exception {
        LabyrinthGameState labyrinthGameState = new LabyrinthGameState(4);
        labyrinthGameState.move(3, 6);
        int curPlayer = labyrinthGameState.getCurrentPlayer();
        Tile tile = labyrinthGameState.getGameBoard().getTile(3,6);
        assertTrue(tile.getPlayer(curPlayer));
    }

    //test the basic constructor
    public void testLabyrinthGameState() throws Exception {
        LabyrinthGameState labyrinthGameState = new LabyrinthGameState(4);
        assertEquals(0, labyrinthGameState.getCurrentPlayer());
        assertEquals(0, labyrinthGameState.getLastXInserted());
        assertEquals(0, labyrinthGameState.getLastYInserted());
        assertNotNull(labyrinthGameState.getGameBoard().getExtraTile());
        assertNotNull(labyrinthGameState.getGameBoard().getTile(1, 1));
    }

    //test the copy constructor
    public void testLabyrinthGameState1() throws Exception {
        LabyrinthGameState labyrinthGameState = new LabyrinthGameState(4);
        LabyrinthGameState labyrinthGameState1 = new LabyrinthGameState(labyrinthGameState);
        assertEquals(labyrinthGameState.getGameBoard().getExtraTile(), labyrinthGameState1.getGameBoard().getExtraTile());
        assertEquals(labyrinthGameState.getCurrentPlayer(), labyrinthGameState1.getCurrentPlayer());
        assertEquals(labyrinthGameState.getLastXInserted(), labyrinthGameState1.getLastXInserted());
        assertEquals(labyrinthGameState.getLastYInserted(), labyrinthGameState1.getLastYInserted());
        assertEquals(labyrinthGameState.getGameBoard().getTile(1, 1).getRotation(),
                labyrinthGameState1.getGameBoard().getTile(1, 1).getRotation());
    }

    //test nextTurn method
    public void testNextTurn() throws Exception {
        LabyrinthGameState labyrinthGameState = new LabyrinthGameState(3);
        labyrinthGameState.nextTurn();
        labyrinthGameState.nextTurn();
        labyrinthGameState.nextTurn();
        labyrinthGameState.nextTurn();
        assertEquals(1, labyrinthGameState.getCurrentPlayer());

        LabyrinthGameState labyrinthGameState1 = new LabyrinthGameState(4);
        labyrinthGameState1.nextTurn();
        labyrinthGameState1.nextTurn();
        labyrinthGameState1.nextTurn();
        assertEquals(3, labyrinthGameState1.getCurrentPlayer());
    }

    public void testTile() throws Exception {
        Tile tile = new Tile(Tile.TEE, Tile.DOWN, true, false, false, true, 0);
        assertEquals(tile.getTreasure(), 0);
        assertEquals(tile.getRotation(), Tile.DOWN);
        assertTrue(tile.hasPlayer(0));
        assertFalse(tile.hasPlayer(1));
        assertFalse(tile.hasPlayer(2));
        assertTrue(tile.hasPlayer(3));
        assertEquals(tile.getType(), Tile.TEE);
    }

    public void testBoard() throws Exception {
        Board board = new Board();
        int x = (int)Math.random()*7;
        int y = (int)Math.random()*7;
        assertNotNull(board.getTile(x, y)); //checks if a random tile exists
        assertNotNull(board.getExtraTile());
        Tile tile = new Tile(Tile.TEE, Tile.DOWN, true, false, false, true, 0);
        board.setTile(x, y, tile);
        assertEquals(board.getTile(x, y), tile);
    }

    //test tile linking
    public void testTileLink() throws Exception {

    }

}