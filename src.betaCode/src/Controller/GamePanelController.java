package Controller;

import Model.Player.AIPlayer;
import Model.Player.HumanPlayer;
import Model.Player.Player;
import Model.Rules.*;
import Model.UnoGameTable;
import Utilities.AudioManager;
import View.Animations.Animation;
import View.Elements.ViewCard;
import View.Elements.ViewPlayer;
import View.Pages.GamePanel;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

public class GamePanelController
{
    private GamePanel view;
    private UnoGameTable gameTable;

    boolean hasFinishedDrawing = false;

    //qui dentro ci sono anche la view e tutti i suoi eventi
    public GamePanelController(GameChoiceController.GameMode gameMode, ViewPlayer player)
    {
        UnoGameRules rules;
        switch (gameMode){
            case MEME_RULES -> rules = new MemeRules();
            case SEVENO_RULES -> rules = new SevenoRules();
            default -> rules = new ClassicRules();
        }
        ViewPlayer[] viewPlayers = new ViewPlayer[]{
               player,
               new ViewPlayer(new AIPlayer("Ai 1")),
               new ViewPlayer(new AIPlayer("Ai 2")),
               new ViewPlayer(new AIPlayer("Ai 3")),
        };
        gameTable = new UnoGameTable(Stream.of(viewPlayers).map(ViewPlayer::getPlayer).toArray(Player[]::new), rules);

        view = new GamePanel(viewPlayers);

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
        view.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                view.setCurrentState(view.getState());
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                super.componentHidden(e);
                view.setCurrentState(GamePanel.State.GAME_PAUSED);
            }
        });
        gameTable.addObserver(view);
        view.setVisible(true);

        startGame();
    }

    private boolean playersTurn() { return view.getCurrentState() == GamePanel.State.PLAYER_TURN; }

    private void viewPlayerTurn(MouseEvent e)
    {
        Point mouseClickPosition = e.getPoint();

        if (!playersTurn()) return; // not players turn
        ViewPlayer currentViewPlayer = view.getCurrentViewPlayer();
        Player currentPlayer = currentViewPlayer.getPlayer();
        if (currentPlayer != gameTable.currentPlayer()) return; //not his turn

        //if clicked on deck
        if (view.getDeck().contains(mouseClickPosition))
            drawCardBranch(currentViewPlayer);

        //see if clicked on a card
        currentViewPlayer.getImagesHand().stream().filter(card -> card.contains(mouseClickPosition)).forEach(this::playCardBranch);


        //if clicked on skip
        if (view.getSkipTurnPosition().contains(mouseClickPosition) )//if has not drew yet player can't skip
            skipTurnBranch(currentPlayer );


        //if clicked on uno
        if (view.getUnoPosition().contains(mouseClickPosition) && currentPlayer.hasOne())  //can shout uno only if has one card
            currentPlayer.shoutUno();
    }

    private void skipTurnBranch(Player currentPlayer)
    {
        if(currentPlayer.hasDrew() && hasFinishedDrawing)
            gameTable.passTurn();

        //view.setCurrentState(view.getCurrentState());
    }

    private void drawCardBranch(ViewPlayer currentViewPlayer)
    {
        if (playerCanDraw(currentViewPlayer.getPlayer()) || playerMustDraw())
            drawOutCard(currentViewPlayer);
    }

    private void playCardBranch(ViewCard card)
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
    {
        AudioManager.getInstance().setEffects(AudioManager.Effects.NOT_VALID);
    }

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

    private void drawOutCard(ViewPlayer currentViewPlayer)
    {
        currentViewPlayer.getPlayer().setDrew(true);
        ViewCard drawnCard = new ViewCard(gameTable.peekNextCard());
        Animation flipCardAnimation = view.flipCardAnimation(drawnCard);
        new Thread( () -> {
            if (flipCardAnimation == null) return;
            flipCardAnimation.Join();

            view.drawCardAnimation(currentViewPlayer, drawnCard).Join();
            gameTable.drawCard(currentViewPlayer.getPlayer());
            hasFinishedDrawing = true;
        },"drawing card").start();
    }

    public GamePanel getView() { return view; }
    public void startGame() { gameTable.startGame(); }
    public void quitGame(){ view.stopTimer(); }
    private boolean playerMustDraw()  { return gameTable.getCurrentPlayerPLayableCards().size() == 0; }
    private boolean playerCanDraw(Player currentPlayer) { return !currentPlayer.hasDrew(); }

    public void setVisible(boolean visible){
        view.setVisible(visible);
    }
}
