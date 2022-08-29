package Controller;

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

public class GamePanelController
{
    private GamePanel view;
    private UnoGameTable gameTable;

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
        gameTable = new UnoGameTable(new Player[]{new HumanPlayer("Piero"),new AIPlayer("Ai 1"),new AIPlayer("Ai 2"),new AIPlayer("Ai 3")}, rules);
        view = new GamePanel();

        view.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseReleased(MouseEvent e)
                {
                    viewPlayerTurn(e);
                }
            });
        view.addMouseMotionListener(new MouseMotionAdapter()
            {
                @Override
                public void mouseMoved(MouseEvent e) { if(playersTurn()) view.animateCardsOnHovering(e); }
            });

        gameTable.addObserver(view);
        view.setVisible(true);

        startGame();
    }

    private boolean playersTurn() { return view.getCurrentState() == GamePanel.State.PLAYER_TURN; }

    private void viewPlayerTurn(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();

        if (!playersTurn()) return; // not players turn
        Player currentPlayer = view.getCurrentPlayer();
        if (currentPlayer != gameTable.currentPlayer()) return; //not his turn

        //if clicked on deck
        if (view.getDeck().isInMouse(x, y))
            drawCardBranch(currentPlayer);

        //see if clicked on a card
        for (CardImage card : view.getPlayerHands().get(currentPlayer))
            if (card.isInMouse(x, y))
                playCardBranch(card);


        //if clicked on skip
        if (view.getSkipTurnPosition().contains(x , y) && currentPlayer.hasDrew() )//if has not drew yet player can't skip
            skipTurnBranch();


        //if clicked on uno
        if (view.getUnoPosition().contains(x, y) && currentPlayer.hasOne())  //can shout uno only if has one card
            currentPlayer.shoutUno();
    }

    private void skipTurnBranch() { gameTable.passTurn(); }

    private void drawCardBranch(Player currentPlayer)
    {
        if (playerMustDraw() || playerCanDraw(currentPlayer))
            drawOutCard(currentPlayer);
    }

    private void playCardBranch(CardImage card)
    {
        if (!gameTable.getCurrentPlayerPLayableCards().contains(card.getCard()))
        {
            cardNotPLayable();
            return;
        }

        Animation anim = view.playCardAnimation(card);
        new Thread( () -> {
            if (anim != null){
                anim.Join();
                gameTable.playCard(card.getCard());
                tryCardActionPerformance(gameTable.getOptions());
            }
        },"playing card").start();
    }

    private void cardNotPLayable()
    {}

    private void tryCardActionPerformance(Options.OptionsBuilder parameters)
    {
        ActionPerformResult a = gameTable.cardActionPerformance(parameters.build());
        switch (a) {
            case NO_COLOR_PROVIDED -> parameters.color(view.choseColorByUser());
            case NO_PLAYER_PROVIDED -> parameters.playerToSwapCards(view.chosePlayerToSwap());
            case SUCCESSFUL -> {
                return;
            }
        }
        tryCardActionPerformance(parameters);
    }

    private void drawOutCard(Player currentPlayer)
    {
        currentPlayer.setDrew(true);
        CardImage drawnCard = new CardImage(gameTable.peekNextCard());
        Animation flipCardAnimation = view.flipCardAnimation(drawnCard);
        new Thread( () -> {
            if (flipCardAnimation == null) return;
            flipCardAnimation.Join();

            view.drawCardAnimation(currentPlayer, drawnCard).Join();
            gameTable.drawCard(currentPlayer);
            hasFinishedDrawing = true;
        },"drawing card").start();
    }

    public GamePanel getView() { return view; }
    public void startGame() { gameTable.startGame(); }
    public void quitGame(){ view.stopTimer(); }
    private boolean playerMustDraw()  { return gameTable.getCurrentPlayerPLayableCards().size() == 0; }
    private boolean playerCanDraw(Player currentPlayer) { return !currentPlayer.hasDrew(); }
}
