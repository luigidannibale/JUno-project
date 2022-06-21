package Controller;

import Model.UnoGame;
import View.MainFrame;

public class UnoGameTable {
    //UnoGame uno = new tipoDiUno(new Player[] {mario,filippo,marco,gianfranco});
    MainFrame view;
    UnoGame model;

    //qui dentro ci sono anche la view e tutti i suoi eventi

    public UnoGameTable(UnoGame model)
    {
        view = new MainFrame();
        this.model = model;

        //System.out.println(Arrays.toString(view.getComponents()));
        //this.model = model;
        //this.model.addObserver(view);
        /*my computer hates me
        while(true) {
            view.setVisible(true);
            view.setVisible(false);
        }*/



    }

    public void setVisible(){
        view.setVisibile();
    }

    public void startGame() {
        model.startGame();
    }
}
