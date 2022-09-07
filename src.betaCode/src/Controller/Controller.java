package Controller;

import java.awt.*;

public abstract class Controller <T extends Container>
{
    protected final T view;

    protected Controller(T view) { this.view = view; }
    public void setVisible(boolean visibility){ view.setVisible(visibility); }

    public  T getView(){return view;}
}
