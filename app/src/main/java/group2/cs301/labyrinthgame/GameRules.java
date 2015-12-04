package group2.cs301.labyrinthgame;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import group2.cs301.labyrinthgame.Game.GameMainActivity;


public class GameRules extends ActionBarActivity {

    private TextView gameRulesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_rules);

        gameRulesText = (TextView) findViewById(R.id.game_rules);

        gameRulesText.setText(
                "Current player: Look at your first treasure. This is your first goal in the labyrinth. \n" +
                        "To reach your goal you must shift the walls of the maze. This is done by pushing the \n" +
                        "extra maze card into the labyrinth at some point so that you can move as far as you \n" +
                        "wish along its open passageways. From the edge of the board push the extra tile into the \n" +
                        "labyrinth. The places where the extra tile can be added are indicated by highlighted boarders.\n" +
                        " The tile that is pushed out will become the next player's means of shifting the maze.\n" +
                        "Until the next player's turn, the new extra tile is left where it is so that all players\n" +
                        "will know how the maze has been shifted on this turn.\n\n"
                + "You must move the maze before each turn, even if you don't need to in order to reach your goal.\n" +
                        "This way you can wall in another player!\n" +
                        "You may not immediately reverse the last player's move by returning the extra tile to the \n" +
                        "position it was just pushed out of.\n\n" +
                        "If the shifting maze pushes out a player's piece, the piece is transferred to the newly inserted \n" +
                        "tile at the other side. This applies whether the piece belongs to the person making the move or \n" +
                        "to another player. Transferring this piece does not count as a move.\n\n" +
                        "After shifting the maze, move your playing piece as far as you choose along the open corridor.\n" +
                        "You can also choose not to move at all. Often you will be able to reach your goal in one move.\n" +
                        "If not, try to get in the best possible position for your next turn.\n\n" +
                        "Once you reach your current treasure, the screen will be updated with your next treasure.\n" +
                        "Each player will have a total of six treasures.\n\n" +
                        "END OF GAME: Once you have reached all your goals you must return to the corner position from \n" +
                        "which you entered the labyrinth. The winner is the first player to get all treasures and return\n" +
                        "to his or her starting position."


        );

        //todo: return button to go back to the previous layout.

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_rules, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
