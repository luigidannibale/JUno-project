package Controller;

import View.MainFrame;

public class MainFrameController {

    MainFrame view;

    public MainFrameController(){
        view = new MainFrame();

    }

    public void setVisible(){
        view.setVisible(true);
    }
}
