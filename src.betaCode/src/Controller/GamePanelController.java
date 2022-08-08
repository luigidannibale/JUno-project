package Controller;

import Model.Player.AIPlayer;
import Model.Player.HumanPlayer;
import Model.Player.Player;
import Model.Rules.ClassicRules;
import Model.Rules.MemeRules;
import Model.Rules.SevenoRules;
import Model.Rules.UnoGameRules;
import Model.UnoGame;
import View.GameChoicePanel;
import View.GamePanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanelController {
    //UnoGame uno = new tipoDiUno(new Player[] {mario,filippo,marco,gianfranco});
    private GamePanel view;

    private UnoGame model;

    //qui dentro ci sono anche la view e tutti i suoi eventi

    public GamePanelController(GameChoiceController.GameMode gameMode)
    {
        UnoGameRules rules;
        switch (gameMode){
            case MEME -> rules = new MemeRules();
            case SEVENO -> rules = new SevenoRules();
            default -> rules = new ClassicRules();
        }
        model = new UnoGame(new Player[]{new HumanPlayer("Piero"),new AIPlayer("Ai 1"),new AIPlayer("Ai 2"),new AIPlayer("Ai 3")}, rules);
        view = new GamePanel(model);

        model.addObserver(view);

        view.setVisible(true);



        model.startGame();
    }

    public GamePanel getView() {
        return view;
    }

    public void startGame() {
        model.startGame();
    }
}
