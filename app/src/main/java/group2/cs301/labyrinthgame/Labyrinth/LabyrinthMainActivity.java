package group2.cs301.labyrinthgame.Labyrinth;

import java.util.ArrayList;

import group2.cs301.labyrinthgame.Game.GameMainActivity;
import group2.cs301.labyrinthgame.Game.GamePlayer;
import group2.cs301.labyrinthgame.Game.LocalGame;
import group2.cs301.labyrinthgame.Game.config.GameConfig;
import group2.cs301.labyrinthgame.Game.config.GamePlayerType;

/**
 * @author G. Emily Nitzberg, Ben Rumptz, Brendan Thomas, Andrew Williams
 * @version 12/1/15.
 */
public class LabyrinthMainActivity extends GameMainActivity {

    private static final int PORT_NUMBER = 2278;

    @Override
    public GameConfig createDefaultConfig() {

        ArrayList<GamePlayerType> playerTypes = new ArrayList<>();

        playerTypes.add(new GamePlayerType("Local Human Player") {
            public GamePlayer createPlayer(String name) {
                return new LabyrinthGameHumanPlayer(name);
            }});
        playerTypes.add(new GamePlayerType("Easy Computer Player") {
            public GamePlayer createPlayer(String name) {
                return new EasyAIPlayer(name);
            }});
        playerTypes.add(new GamePlayerType("Hard Computer Player") {
            public GamePlayer createPlayer(String name) {
                return new HardAIPlayer(name);
            }
        });

        GameConfig defaultConfig = new GameConfig(playerTypes, 2, 4, "Labyrinth", PORT_NUMBER);
        defaultConfig.addPlayer("Human", 0); // player 1: a human player
        defaultConfig.addPlayer("Computer", 1); // player 2: Easy AI
        defaultConfig.addPlayer("Computer", 2); // player 2: Easy AI
        defaultConfig.setRemoteData("Remote Player", "", 0);

        return defaultConfig;
    }

    @Override
    public LocalGame createLocalGame(int numPlayers) {
        return new LabyrinthLocalGame(numPlayers);
    }
}
