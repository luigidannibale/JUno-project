package Controller;

import Model.UnoGame;
import View.GameFrame;

public class UnoGameTable {
    //UnoGame uno = new tipoDiUno(new Player[] {mario,filippo,marco,gianfranco});
    GameFrame view;
    UnoGame model;

    //qui dentro ci sono anche la view e tutti i suoi eventi

    public UnoGameTable(UnoGame model,GameFrame view)
    {
        view = new GameFrame();
        this.model = model;
        this.model.addObserver(view);
    }

    public void startGame() {
        model.startGame(7);
    }
}
