package Controller;

import Model.Player.Player;
import Model.Rules.ClassicRules;
import Model.Rules.MemeRules;
import Model.Rules.SevenoRules;
import Model.Rules.UnoGameRules;
import Model.UnoGame;
import View.GameChoicePanel;
import View.GamePanel;

public class GamePanelController {
    //UnoGame uno = new tipoDiUno(new Player[] {mario,filippo,marco,gianfranco});
    GamePanel view;

    UnoGame model;

    //qui dentro ci sono anche la view e tutti i suoi eventi

    public GamePanelController(GameChoiceController.GameMode gameMode)
    {
        this.view = new GamePanel();
        UnoGameRules rules;
        switch (gameMode){
            case MEME -> rules = new MemeRules();
            case SEVENO -> rules = new SevenoRules();
            default -> rules = new ClassicRules();
        }
        this.model = new UnoGame(new Player[]{null,null}, rules);

        view.setVisible(true);

        //System.out.println(Arrays.toString(view.getComponents()));
        //this.model = model;
        //this.model.addObserver(view);
        /*my computer hates me
        while(true) {
            view.setVisible(true);
            view.setVisible(false);
        }*/
    }

    public GamePanel getView() {
        return view;
    }

    public void startGame() {
        model.startGame();
    }
}
