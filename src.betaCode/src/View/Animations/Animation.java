package View.Animations;

import View.Elements.ViewCard;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Abstract class used to provide the common attributes and methods of the {@link Animation}s.
 * It needs to be extended.
 * @author Luigi D'Annibale, Daniele Venturini
 */
public abstract class Animation extends Thread
{
    protected int width = ViewCard.width;
    protected int height = ViewCard.height;

    protected BufferedImage image;
    protected int delay;
    protected boolean running;

    /**
     * Instantiate a new Thread Animation and calculates the delay between every update of the run method
     */
    protected Animation(){
        int framesPerSecond = 100;
        delay = 1000 / framesPerSecond;
        running = true;
    }

    /**
     * Delegates the implementation of the paint method to the subclasses
     */
    public abstract void paint(Graphics2D g);

    /**
     * Delegates the implementation of the run method to the subclasses
     */
    @Override
    public abstract void run();

    /**
     * Causes the currently executing thread to sleep (temporarily cease execution) for the specified delay of milliseconds calculated in the constructor.
     */
    protected void sleep()
    {
        try
        {
            sleep(delay);
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Waits for this thread to end.
     */
    public void Join()
    {
        try
        {
            join();
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Stops the cycle in the run method
     */
    public void Stop(){
        running = false;
    }
}
