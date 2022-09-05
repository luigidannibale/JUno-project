package View.Animations;

import View.Elements.ViewCard;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Animation extends Thread//implements Runnable
{
    protected int width = ViewCard.width;
    protected int height = ViewCard.height;
    protected BufferedImage image;
    //protected final Timer timer;
    protected int delay;
    protected boolean running = true;
    //protected int delay;

    protected Animation(){
        int framesPerSecond = 100;
        delay = 1000 / framesPerSecond;

        //timer = new Timer(delay, null);
    }

    //public void stop(){timer.stop();}

    public abstract void paint(Graphics2D g);

    protected void sleep(){
        try
        {
            sleep(delay);
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

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

    public void Stop(){
        running = false;
    }
}
