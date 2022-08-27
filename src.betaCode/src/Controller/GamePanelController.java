package Controller;

import Model.Cards.CardColor;
import Model.Cards.CardValue;
import Model.Exceptions.NoSelectedColorException;
import Model.Exceptions.NoSelectedPlayerToSwapException;
import Model.Player.AIPlayer;
import Model.Player.HumanPlayer;
import Model.Player.Player;
import Model.Rules.*;
import Model.UnoGameTable;
import View.Animations.Animation;
import View.Animations.FlipAnimation;
import View.Animations.PlayAnimation;
import View.Elements.CardImage;
import View.Pages.GamePanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class GamePanelController {
    //UnoGame uno = new tipoDiUno(new Player[] {mario,filippo,marco,gianfranco});
    private GamePanel view;
    private UnoGameTable model;



    //qui dentro ci sono anche la view e tutti i suoi eventi

    public GamePanelController(GameChoiceController.GameMode gameMode)
    {
        UnoGameRules rules;
        switch (gameMode){
            case MEME -> rules = new MemeRules();
            case SEVENO -> rules = new SevenoRules();
            default -> rules = new ClassicRules();
        }
        model = new UnoGameTable(new Player[]{new HumanPlayer("Piero"),new AIPlayer("Ai 1"),new AIPlayer("Ai 2"),new AIPlayer("Ai 3")}, rules);
        view = new GamePanel();
        view.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseReleased(MouseEvent e) {

                int x = e.getX();
                int y = e.getY();
                Player currentPlayer = view.getCurrentPlayer();

                if (view.getCurrentState() == GamePanel.State.PLAYER_TURN)
                {
                    if (!currentPlayer.HasDrew() && view.getDeck().isInMouse(x, y))
                        drawOutCard(currentPlayer);

                    //var iterator = playerHands.get(players[0]).listIterator();
                    for (CardImage card : view.getPlayerHands().get(currentPlayer)) { //le carte dell'umano
                        if (!card.isInMouse(x, y)) continue;

                        if (model.getPLayableCards().contains(card.getCard()))
                        {
                            Animation anim = view.playCardAnimation(card);
                            new Thread( () -> {
                                if (anim != null){
                                    anim.Join();

                                    model.playCard(card.getCard());
                                    Options.OptionsBuilder parameters = model.getOptions();

                                    tryCardActionPerformance(parameters);

                                    //si puo usare un try catch
//                                    if(card.getCard().getValue() == CardValue.WILD)
//                                        parameters.color(view.choseColorByUser());
//                                    if(card.getCard().getValue() == CardValue.SEVEN && rules instanceof SevenoRules)
//                                        parameters.playerToSwapCards(view.chosePlayerToSwap());
//                                    model.cardActionPerformance(parameters.build());
                                }
                            },"playing card").start();
                        }
                    }

                    if (currentPlayer.HasDrew() && view.getSkipTurnPosition().contains(x , y)) model.passTurn();
                }
                if (currentPlayer.HasOne() && view.getUnoPosition().contains(x, y)) currentPlayer.setSaidOne(true);
            }

            private void tryCardActionPerformance(Options.OptionsBuilder parameters) {
                try
                {
                    model.cardActionPerformance(parameters.build());
                }
                catch (NoSelectedPlayerToSwapException nspe)
                {
                    parameters.playerToSwapCards(view.chosePlayerToSwap());
                    tryCardActionPerformance(parameters);
                }
                catch (NoSelectedColorException nsce)
                {
                    parameters.color(view.choseColorByUser());
                    tryCardActionPerformance(parameters);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        view.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                view.animateCardsOnHovering(e); }
        });

        model.addObserver(view);
        view.setVisible(true);
        model.startGame();
    }
    private void drawOutCard(Player currentPlayer)
    {
        currentPlayer.setHasDrew(true);
        CardImage drawnCard = new CardImage(model.peekNextCard());
        Animation flipCardAnimation = view.flipCardAnimation(drawnCard);
        new Thread( () -> {
            if (flipCardAnimation == null) return;
            flipCardAnimation.Join();
            view.drawCardAnimation(drawnCard).Join();
            model.drawCard();
        },"drawing card").start();
    }
    public GamePanel getView() {
        return view;
    }

    public void startGame() {
        model.startGame();
    }

    public void quitGame(){
        view.stopTimer();
    }
}
