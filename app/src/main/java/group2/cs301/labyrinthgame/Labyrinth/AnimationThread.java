package group2.cs301.labyrinthgame.Labyrinth;

/**
 * @author Brendan Thomas
 * @version December 1, 2015
 */
public class AnimationThread extends Thread {

    private boolean running = true;
    private LabyrinthSurfaceView view;

    /**
     * AnimationThread
     *
     * Constructor for the animation thread that assigns passed values to
     * instance variables
     *
     * @param toTick - the surfaceView that will be updated
     */
    public AnimationThread(LabyrinthSurfaceView toTick) {
        super();
        view = toTick;
    }

    @Override
    public void run() {
        while(running) {
            view.postInvalidate();
            try {
                Thread.sleep(34); //runs at just under 30hz
            }
            catch (InterruptedException e) {
                //interruption is not an issue
            }
        }
    }

    /**
     * setStopped
     *
     * stops the execution of this thread.
     */
    public void setStopped() {
        running = false;
    }
}
