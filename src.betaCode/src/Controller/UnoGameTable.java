package Controller;

import Model.UnoGame;
import View.GameFrame;

import java.util.Arrays;

public class UnoGameTable {
    //UnoGame uno = new tipoDiUno(new Player[] {mario,filippo,marco,gianfranco});
    GameFrame view;
    UnoGame model;

    //qui dentro ci sono anche la view e tutti i suoi eventi

    public UnoGameTable(UnoGame model)
    {
        view = new GameFrame();
        var a = view.getAccessibleContext();
        var b = a.getAccessibleComponent();

        System.out.println(Arrays.toString(view.getComponents()));
        this.model = model;
        this.model.addObserver(view);
        /*
        while(true) {
            view.setVisible(true);
            b.setVisible(false);
        }*/



    }

    public void startGame() {
        model.startGame(7);
    }
}
