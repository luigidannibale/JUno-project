package View.Animations;

import javax.swing.*;
import java.awt.*;

public abstract class Animation {
    protected Timer timer;

    public boolean isRunning(){
        return timer.isRunning();
    }

    public abstract void paint(Graphics2D g);
}
