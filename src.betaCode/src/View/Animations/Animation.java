package View.Animations;

import javax.swing.*;
import java.awt.*;

public abstract class Animation {
    protected final Timer timer;
    protected int delay;

    protected Animation(){
        int framesPerSecond = 140;
        delay = 1000 / framesPerSecond;

        timer = new Timer(delay, null);
    }

    public boolean isRunning(){
        return timer.isRunning();
    }

    public abstract void paint(Graphics2D g);
}
