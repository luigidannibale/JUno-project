package Controller;

import java.awt.*;

/**
 * Class used to model all the controllers that manage an associated view that extends container. <br/>
 * Provides methods to access the view.
 * @param <T>
 */
public abstract class Controller <T extends Container>
{
    protected final T view;

    /**
     * Creates the associated view
     * @param view
     */
    protected Controller(T view) { this.view = view; }

    /**
     * Sets the visibility of the associated view
     * @param visibility
     */
    public void setVisible(boolean visibility){ view.setVisible(visibility); }

    /**
     * @return the associated view
     */
    public  T getView(){return view;}
}
