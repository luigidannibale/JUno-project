package Controller;

import Model.Exceptions.NoSelectedColorException;
import Model.Exceptions.NoSelectedPlayerToSwapException;
import Model.Player.AIPlayer;
import Model.Player.HumanPlayer;
import Model.Player.Player;
import Model.Rules.*;
import Model.UnoGameTable;
import View.Animations.Animation;
import View.Elements.CardImage;
import View.Pages.GamePanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class GamePanelController {
    //UnoGame uno = new tipoDiUno(new Player[] {mario,filippo,marco,gianfranco});
    private GamePanel view;
    private UnoGameTable model;

    boolean hasFinishedDrawing = false;

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
                System.out.println(view.getCurrentState());
                int x = e.getX();
                int y = e.getY();
                Player currentPlayer = view.getCurrentPlayer();

                if (view.getCurrentState() == GamePanel.State.PLAYER_TURN)
                {
                    if (!currentPlayer.hasDrew() && view.getDeck().isInMouse(x, y))
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
                                    tryCardActionPerformance(model.getOptions());
                                }
                            },"playing card").start();
                        }
                    }

                    if (currentPlayer.hasDrew() && hasFinishedDrawing && view.getSkipTurnPosition().contains(x , y)) {
                        hasFinishedDrawing = false;
                        model.passTurn();
                    }
                }
                if (view.getPlayers()[0].hasOne() && view.getUnoPosition().contains(x, y)){
                    view.getPlayers()[0].shoutUno();
                    System.out.println("UNOOOOO");
                }
            }

            private void tryCardActionPerformance(Options.OptionsBuilder parameters)
            {
                ActionPerformResult a = model.cardActionPerformance(parameters.build());
                switch (a) {
                    case NO_COLOR_PROVIDED -> parameters.color(view.choseColorByUser());
                    case NO_PLAYER_PROVIDED -> parameters.playerToSwapCards(view.chosePlayerToSwap());
                    case SUCCESSFUL -> {
                        return;
                    }
                }
                tryCardActionPerformance(parameters);
            }
        });

        view.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e) { view.animateCardsOnHovering(e); }
        });

        model.addObserver(view);
        view.setVisible(true);

        startGame();
    }
    private void drawOutCard(Player currentPlayer)
    {
        currentPlayer.setDrew(true);
        CardImage drawnCard = new CardImage(model.peekNextCard());
        Animation flipCardAnimation = view.flipCardAnimation(drawnCard);
        new Thread( () -> {
            if (flipCardAnimation == null) return;
            flipCardAnimation.Join();
            view.drawCardAnimation(currentPlayer, drawnCard).Join();
            model.drawCard(currentPlayer);
            hasFinishedDrawing = true;
        },"drawing card").start();
    }
    public GamePanel getView() { return view; }
    public void startGame() { model.startGame(); }
    public void quitGame(){ view.stopTimer(); }
}
