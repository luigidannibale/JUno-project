package View.Animations;

import View.CardImage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Animation
{
    protected int width = CardImage.width;
    protected int height = CardImage.height;
    protected BufferedImage image;
    protected final Timer timer;
    //protected int delay;

    protected Animation(){
        int framesPerSecond = 140;
        int delay = 1000 / framesPerSecond;

        timer = new Timer(delay, null);
    }

    public boolean isRunning(){
        return timer.isRunning();
    }

    public void stop(){timer.stop();}

    public abstract void paint(Graphics2D g);
}
