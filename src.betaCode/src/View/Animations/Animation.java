package View.Animations;

import javax.swing.*;

public abstract class Animation {
    protected Timer timer;

    public boolean isRunning(){
        return timer.isRunning();
    }
}
