package Controller;

import Model.Player.AIPlayer;
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
import java.util.Arrays;
import java.util.stream.Stream;

public class GamePanelController extends Controller<GamePanel>
{
    private UnoGameTable gameTable;

    /*goes true when a card is drawn out*/
    private boolean hasFinishedDrawing = false;


    public GamePanelController(GameChoiceController.GameMode gameMode, ViewPlayer[] players)
    {
        super(new GamePanel(players));
        UnoGameRules rules;
        switch (gameMode)
        {
            case MEME_RULES -> rules = new MemeRules();
            case SEVENO_RULES -> rules = new SevenoRules();
            default -> rules = new ClassicRules();
        }
        gameTable = new UnoGameTable(Stream.of(players).map(ViewPlayer::getPlayer).toArray(Player[]::new), rules);
        players[0].getPlayer().getHand().removeAllElements();

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
        view.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentShown(ComponentEvent e)
            {
                super.componentShown(e);
                view.resumeGame();
                view.update(gameTable, null);
            }
            @Override
            public void componentHidden(ComponentEvent e)
            {
                super.componentHidden(e);
                view.pauseGame();
            }
        });
        gameTable.addObserver(view);
        view.setVisible(true);

        startGame();
    }

    public void startGame() { gameTable.startGame(); }

    public void quitGame(){ view.stopTimer(); }

//    private ViewPlayer[] getViewPlayers(GameChoiceController.GameMode gameMode, ViewPlayer player) {
//        UnoGameRules rules;
//        switch (gameMode)
//        {
//            case MEME_RULES -> rules = new MemeRules();
//            case SEVENO_RULES -> rules = new SevenoRules();
//            default -> rules = new ClassicRules();
//        }
//        ViewPlayer[] viewPlayers = new ViewPlayer[]{
//                player,
//               new ViewPlayer(new AIPlayer("Ai 1")),
//               new ViewPlayer(new AIPlayer("Ai 2")),
//               new ViewPlayer(new AIPlayer("Ai 3")),
//        };
//        gameTable = new UnoGameTable(Stream.of(viewPlayers).map(ViewPlayer::getPlayer).toArray(Player[]::new), rules);
//        viewPlayers[0].getPlayer().getHand().removeAllElements();
//        return viewPlayers;
//    }

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
        if (view.getUnoPosition().contains(mouseClickPosition) && view.getPlayers()[0].hasOne())  //can shout uno only if has one card
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
        boolean canDraw = !currentViewPlayer.getPlayer().hasDrew(),
                mustDraw = gameTable.getCurrentPlayerPLayableCards().size() == 0;

        if (canDraw || mustDraw)
            drawOutCard(currentViewPlayer);
    }

    private void playCardBranch(ViewCard card)
    {
        if (!gameTable.getCurrentPlayerPLayableCards().contains(card.getCard()))
        {
            view.cardNotPLayableEffects();
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
        new Thread(() -> {
            if (flipCardAnimation == null) return;
            flipCardAnimation.Join();
            view.drawCardAnimation(currentViewPlayer, drawnCard).Join();
            gameTable.drawCard(currentViewPlayer.getPlayer());
            hasFinishedDrawing = true;
        },"drawing card").start();
    }

    //private boolean playerMustDraw()  { return gameTable.getCurrentPlayerPLayableCards().size() == 0; }
    //private boolean playerCanDraw(Player currentPlayer) { return !currentPlayer.hasDrew();}
}
