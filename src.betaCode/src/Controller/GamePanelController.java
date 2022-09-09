package Controller;

import Model.Players.Player;
import Model.Rules.*;
import Model.UnoGameTable;
import Controller.Utilities.AudioManager;
import View.Animations.Animation;
import View.Elements.ViewPlayer;
import View.Elements.ViewRotatableCard;
import View.Pages.GamePanel;
import View.Pages.StartingMenuPanel;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Class used to get the inputs in the {@link GamePanel} and to update the {@link UnoGameTable} with the changes made by the user.
 * It manages the player's turn and the {@link Animation}s to be called in the view
 */
public class GamePanelController extends Controller<GamePanel>
{
    private UnoGameTable gameTable;

    /*goes true when a card is drawn out*/
    private boolean hasFinishedDrawing = false;

    private Animation exposeAnimation;

    /**
     * Creates a new {@link GamePanelController} with its associated view ({@link GamePanel}), instantiating a new {@link UnoGameTable} with the given {@link Controller.GameChoiceController.GameMode} and {@link ViewPlayer}s
     * If the {@link GamePanel} is hidden, then the game is paused, and it resumes when it gets shown again.
     * @param gameMode
     * @param players
     */
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

        view.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseReleased(MouseEvent e)
            {
                viewPlayerClick(e);
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

    /**
     * Starts a new game and manages the first card on the table
     */
    public void startGame()
    {
        ActionPerformResult result = gameTable.startGame();
        if (result == ActionPerformResult.NO_COLOR_PROVIDED)
            new Thread(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(1200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                gameTable.performFirstCard(gameTable.getOptions().currentPlayer(0).nextPlayer(0).color(view.choseColorByUser()).build());
            }).start();
    }

    /**
     * Stops all the animations and paintings in the {@link GamePanel}
     */
    public void quitGame(){
        AudioManager.getInstance().stopEffect();
        view.stopTimer();
    }

    /**
     * Checks if the user has to play his turn
     * @return true if it's the user's turn, false otherwise
     */
    private boolean playersTurn() { return view.getCurrentState() == GamePanel.State.PLAYER_TURN; }

    /**
     * Based on the click coordinates on the view, it checks if user: <br>
     * - has to say UNO <br>
     * - is exposing another player <br>
     * - has skipped turn <br>
     * - has drawn a card
     * @param e
     */
    private void viewPlayerClick(MouseEvent e)
    {
        Point mouseClickPosition = e.getPoint();
        ViewPlayer humanPlayer = view.getViewPlayers()[0];

        if (view.getUnoPosition().contains(mouseClickPosition) && humanPlayer.getPlayer().hasOne() && !humanPlayer.getPlayer().hasSaidOne()) //clicked on uno, can shout uno only if has one card
            shoutUnoBranch(view.getPlayers()[0]);

        exposeBranch(view.getViewPlayers(), mouseClickPosition);

        continueBranch(mouseClickPosition);

        if (!playersTurn()) return; // not players turn

        if (view.getDeck().contains(mouseClickPosition)) //clicked on deck
            drawCardBranch(humanPlayer);

        //see if clicked on a card
        humanPlayer.getImagesHand().stream().filter(card -> card.contains(mouseClickPosition)).forEach(this::playCardBranch);

        if (view.getSkipTurnPosition().contains(mouseClickPosition) )//clicked on skip, if has not drew yet player can't skip
            skipTurnBranch(humanPlayer.getPlayer());
    }

    private void skipTurnBranch(Player currentPlayer) { if(currentPlayer.hasDrew() && hasFinishedDrawing) gameTable.passTurn(); }

    private void shoutUnoBranch(Player currentPlayer)
    {
        currentPlayer.shoutUno();
        view.shoutUnoAnimation(currentPlayer);
    }

    private void exposeBranch(ViewPlayer[] viewPlayers, Point mouseClickPosition)
    {
        if (view.animationRunning(exposeAnimation)) return;

        for (int i = 0; i < viewPlayers.length; i++) {
            ViewPlayer p = viewPlayers[i];
            if (p.getNamePosition().contains(mouseClickPosition) && gameTable.isExposable(i))
            {
                new Thread(() -> {
                    gameTable.expose(p.getPlayer());
                    exposeAnimation = view.exposedAnimation();
                }).start();

            }
        }
    }

    private void continueBranch(Point p)
    {
        if (view.getContinuePosition().contains(p))
        {
            if (view.getCurrentState() == GamePanel.State.WIN)
                gameTable.startGame();
            else if(view.getCurrentState() == GamePanel.State.MATCH_WIN)
                MainFrameController.getInstance().quitGame();
        }
    }

    private void drawCardBranch(ViewPlayer currentViewPlayer)
    { if (!currentViewPlayer.getPlayer().hasDrew()) drawOutCard(currentViewPlayer); }

    private void playCardBranch(ViewRotatableCard card)
    {
        if (!gameTable.getCurrentPlayerPLayableCards().contains(card.getCard()))
        {
            AudioManager.getInstance().setEffect(AudioManager.Effect.NOT_VALID);
            return;
        }

        Animation anim = view.playCardAnimation(card);
        new Thread( () -> {
            if (anim != null){
                anim.selfJoin();
                ActionPerformResult res = gameTable.playCard(card.getCard());
                if (res != ActionPerformResult.PLAYER_WON) tryCardActionPerformance(gameTable.getOptions());
            }
        },"playing card").start();
    }

    private void tryCardActionPerformance(Options.OptionsBuilder parameters)
    {
        ActionPerformResult a = gameTable.cardActionPerformance(parameters.build());
        switch (a)
        {
            case NO_COLOR_PROVIDED -> parameters.color(view.choseColorByUser());
            case NO_PLAYER_PROVIDED -> parameters.playerToSwapCards(view.chosePlayerToSwap());
            case SUCCESSFUL, PLAYER_WON -> { return; }
        }
        tryCardActionPerformance(parameters);
    }

    private void drawOutCard(ViewPlayer currentViewPlayer)
    {
        currentViewPlayer.getPlayer().setDrew(true);
        ViewRotatableCard drawnCard = new ViewRotatableCard(gameTable.peekNextCard());
        Animation flipCardAnimation = view.flipCardAnimation(drawnCard);
        new Thread(() -> {
            if (flipCardAnimation == null) return;
            flipCardAnimation.selfJoin();
            view.drawCardAnimation(currentViewPlayer, drawnCard).selfJoin();
            gameTable.drawCard(currentViewPlayer.getPlayer());
            hasFinishedDrawing = true;
        },"drawing card").start();
    }
}
