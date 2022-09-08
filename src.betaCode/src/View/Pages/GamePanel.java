package View.Pages;

import Model.Cards.Card;
import Model.Cards.Color;
import Model.Player.AIPlayer;
import Model.Player.HumanPlayer;
import Model.Player.Player;
import Model.Rules.ActionPerformResult;
import Model.UnoGameTable;
import Controller.Utilities.AudioManager;
import Controller.Utilities.Config;
import View.Utils;
import View.Animations.*;
import View.Elements.GraphicQuality;
import View.Elements.ViewAnimableCard;
import View.Elements.ViewCard;
import View.Elements.ViewPlayer;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GamePanel extends JPanel implements Observer
{
    public enum State
    {
        PLAYER_TURN,
        AI_TURN,
        GAME_PAUSED,
        WIN,
        MATCH_WIN
    }

    private static final String imagePath = MainFrame.IMAGE_PATH+"GamePanel/";

    //COLORS
    private final java.awt.Color green = new java.awt.Color(14, 209, 69);
    private final java.awt.Color playerBackground = new java.awt.Color(101, 132, 247, 160);
    private final java.awt.Color currentPlayerBackground = new java.awt.Color(255, 209, 26, 160);

    //FONTS
    private final Font fontNames;
    private final Font titleFont;
    private final Font ladderFont;

    //PAINT VARIABLE
    private final int centerX;
    private final int centerY;
    private final int deckX;
    private final int deckY;
    private final int discardX;
    private final int discardY;
    private int maxCardsWidth = 1350;
    private int maxCardsHeight = 810;

    //AI PLAYING THREAD
    private Thread aiThread;
    private boolean aiRunning = false;

    //GAME REPAINT THREAD
    private Thread gameThread;
    private boolean gameRunning = true;

    //MODEL VARIABLES
    private ViewPlayer[] viewPlayers;
    private Player[] players;
    private ViewPlayer currentViewPlayer;
    private Card lastCard;
    private ViewCard deck;
    private int deckSize;
    private ViewCard discard;
    private State currentState;

    //ANIMATIONS
    private MovingAnimation movingAnimation;
    private ArrayList<Animation> animations;
    private FlippingAnimation flipAnimation;
    private final RotatingAnimation rotatingAnimation;

    //CLICK POSITION
    private Rectangle skipTurnPosition;
    private Rectangle unoPosition;
    private Rectangle continuePosition;

    //LADDER
    private List<Player> ladder;
    final AlphaComposite transparent = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
    int ladderWidth = (int) (550 * Config.scalingPercentage);
    int ladderHeight = (int) (700 * Config.scalingPercentage);
    int ladderX;
    int ladderY;
    int ladderThickness = 5;
    BasicStroke a = new BasicStroke(ladderThickness);
    String continueString = "CONTINUE";

    /**
     * Creates the {@link GamePanel} with the {@link ViewPlayer}s of the game.
     * Constants variables needed for the paint method are calculated here and then the repaint thread is called
     * @param viewPlayers
     */
    public GamePanel(ViewPlayer[] viewPlayers)
    {
        this.viewPlayers = viewPlayers;
        setOpaque(true);
        setDoubleBuffered(true);
        //debug
        setBackground(java.awt.Color.GREEN);
        InitializeComponents();

        ViewCard.height = (int) (180 * Config.scalingPercentage);
        ViewCard.width = (int) (120 * Config.scalingPercentage);
        fontNames = new Font("Digital-7", Font.PLAIN, (int) (25 * Config.scalingPercentage));
        titleFont = new Font("Digital-7", Font.BOLD, (int) (60 * Config.scalingPercentage));
        ladderFont = new Font("Digital-7", Font.BOLD, (int) (40 * Config.scalingPercentage));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        centerX = screenSize.width / 2;
        centerY = screenSize.height / 2;

        ladderX = centerX - ladderWidth / 2;
        ladderY = centerY - ladderHeight / 2;

        discardX = centerX + 25;
        discardY = centerY - ViewCard.height / 2;
        discard.setPosition(discardX, discardY, ViewCard.width);

        deckX = centerX - 25 - ViewCard.width;
        deckY = discardY;
        deck.setPosition(deckX, deckY, ViewCard.width);

        maxCardsWidth = (int) (maxCardsWidth * Config.scalingPercentage);
        maxCardsHeight = (int) (maxCardsHeight * Config.scalingPercentage);

        rotatingAnimation = new RotatingAnimation(imagePath, centerX, centerY);
        animations.add(rotatingAnimation);

        repaintView();
    }

    /**
     * Initialize the main components of the panel
     */
    private void InitializeComponents()
    {
        deck = new ViewCard();
        discard = new ViewCard();
        animations = new ArrayList<>();
        skipTurnPosition = new Rectangle();
        unoPosition = new Rectangle();
        continuePosition = new Rectangle();
    }

    /**
     * Thread called to always repaint the view, so that every update and animation is constantly painted
     */
    private void repaintView()
    {
        gameThread = new Thread(() ->{ while(gameRunning) repaint(); },"gameViewPeriodicRepaint");
        gameThread.start();
    }

    /**
     * Lifts the cards of the {@link HumanPlayer} if hovered
     * @param e
     */
    public void animateCardsOnHovering(MouseEvent e)
    {
        Point mouseClickPosition = e.getPoint();
        for (ViewAnimableCard card : viewPlayers[0].getImagesHand())
            card.setShiftHeight(card.contains(mouseClickPosition) ? -30 : 0);
    }

    /**
     * Method called when the user plays a Wild Card, to provide a color chosen by the user.
     * Cannot be closed without choosing a color
     * @return the color chosen
     */
    public Color choseColorByUser()
    {
        String[] colors = new String[]{"RED", "YELLOW", "BLUE", "GREEN"};
        int choice = -1;
        while (choice == -1)
            choice = JOptionPane.showOptionDialog(
                            null,
                            "Choose a color to continue",
                            "Card color choice",
                            2,
                            JOptionPane.QUESTION_MESSAGE,
                            new ImageIcon(imagePath + "wild.png"),
                            colors, colors[0]);
        AudioManager.getInstance().setEffect(AudioManager.Effect.valueOf(colors[choice]));
        return Color.valueOf(colors[choice]);
    }

    /**
     * Method called when the user plays a seven card in seveno game mode, to provide a player to swap cards with.
     * Cannot be closed without choosing a player
     * @return the player chosen
     */
    public Player chosePlayerToSwap()
    {
        HashMap<String,Player> playerHashMap = new HashMap<>();
        Arrays.stream(players).filter(p -> currentViewPlayer.getPlayer() != p).forEach(p -> playerHashMap.put(p.getName(), p));
        int choice = -1;
        while (choice == -1)
            choice = JOptionPane.showOptionDialog(
                null,
                "Who do you want to swap hand with ? ",
                "Player choice",
                2,
                JOptionPane.QUESTION_MESSAGE,
                null,
                playerHashMap.keySet().toArray(),playerHashMap.keySet().toArray()[0] );
        return (Player) playerHashMap.values().toArray()[choice];
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        //PAINTS THE BACKGROUND
        g.setColor(green);
        g.fillRect(0,0, getWidth(), getHeight());

        Graphics2D g2 = (Graphics2D) g;
        if (Config.graphicQuality == GraphicQuality.HIGH) Utils.applyQualityRenderingHints(g2);

        //PAINTS THE HANDS
        drawHorizontalHand(viewPlayers[0], g2, getHeight() - ViewCard.height);
        drawHorizontalHand(viewPlayers[2], g2, 0);
        drawVerticalHand(viewPlayers[1], g2, getWidth() - ViewCard.height);
        drawVerticalHand(viewPlayers[3], g2, 0);

        //PAINTS DISCARDS
        g2.drawImage(discard.getCardImage(), discardX, discardY, ViewCard.width, ViewCard.height, null);

        //PAINTS DECK
        if (deckSize > 1)
        {
            g2.setFont(fontNames);
            g2.setColor(java.awt.Color.green);
            int width = g2.getFontMetrics().stringWidth(String.valueOf(deckSize));
            int height = g2.getFontMetrics().getHeight();
            g2.fillRoundRect(deckX - 5, deckY - height, width + 10, height, 20, 20);
            g2.setColor(java.awt.Color.BLACK);
            g2.drawString(String.valueOf(deckSize), deckX, deckY - 5);
            g2.drawImage(deck.getCardImage(), deckX, deckY, ViewCard.width, ViewCard.height, null);
        }

        //PAINTS ANIMATIONS
        Iterator<Animation> iter = animations.iterator();
        while(iter.hasNext()){
            Animation animation = iter.next();
            if (animation.isAlive()) animation.paint(g2);
            else iter.remove();
        }

        //PAINTS WIN LADDER
        if (currentState == State.WIN || currentState == State.MATCH_WIN) drawLadder(g2);

        g2.dispose();
    }

    @Override
    public void update(Observable o, Object arg)
    {
        UnoGameTable gameTable = (UnoGameTable) o;
        lastCard = gameTable.getLastCard();
        createCards();
        currentViewPlayer = viewPlayers[gameTable.currentPlayerIndex()];

        //CHECK SOMEONE HAS WON
        if(gameTable.hasWin())
        {
            boolean win = gameTable.checkWin(currentViewPlayer.getPlayer());
            ladder = Arrays.stream(viewPlayers).map(ViewPlayer::getPlayer).sorted(Comparator.comparing(Player::getPoints).reversed()).toList();
            currentState = win ? State.MATCH_WIN : State.WIN;
            AudioManager.getInstance().setEffect(AudioManager.Effect.WIN);
            return;
        }
        players = gameTable.getPlayers();
        deckSize = gameTable.getDeck().size();

        //CHECK SOMEONE DIDNT SAY ONE BUT HE IS SAVED
        int prePreviousPlayer = gameTable.getTurnManager().previous(gameTable.getTurnManager().previous());
        if(viewPlayers[prePreviousPlayer].getPlayer() instanceof AIPlayer && gameTable.isExposable(prePreviousPlayer)) players[prePreviousPlayer].shoutUno();

        //CHECK CURRENT PLAYER IS BLOCKED
        if (currentViewPlayer.getPlayer().isBlocked())
        {
            currentViewPlayer.getPlayer().setBlocked(false);
            gameTable.passTurn();
            return;
        }
        //CHANGE TURN ANIMATION
        if (rotatingAnimation.isAlive())
        {
            rotatingAnimation.changeTurn(gameTable.antiClockwiseTurn());
            rotatingAnimation.imageColor(discard.getCard().getColor());
        }
        //IF GAME NOT PAUSED UPDATE STATE
        if (currentState != State.GAME_PAUSED) currentState = calculateState();
        //ARTIFICIAL INTELLIGENCE TURN
        if (currentState == State.AI_TURN && !aiRunning) asyncAITurn(gameTable);
    }

    /**
     * Method used to create the graphic representation of the players' hand and to set the image of the discards
     */
    public void createCards()
    {
        try
        {
            int[] rotations = new int[]{0, 270, 180, 90};
            IntStream.range(0, viewPlayers.length).forEach(i -> {
                ViewPlayer viewPlayer = viewPlayers[i];
                int rotation = rotations[i];
                viewPlayer.setImagesHand(viewPlayer.getPlayer().getHand().stream().map(c -> new ViewAnimableCard(c, rotation)).collect(Collectors.toCollection(ArrayList::new)));
            });
            discard.setCard(lastCard);
        }
        catch (ConcurrentModificationException ignored){ }
    }

    /**
     * Sleeps a random time between the given min and max milliseconds.
     * @param min
     * @param max
     */
    private void randomSleep(int min, int max)
    {
        try { Thread.sleep(new Random().nextInt(min, max)); }
        catch (InterruptedException ignored) {}
    }

    /**
     * Async method to let the {@link AIPlayer}s play without blocking the main window.
     * It does everything about the game turn, from shouting uno to playing
     * @param gameTable needed to call methods in the model
     */
    public void asyncAITurn(UnoGameTable gameTable)
    {
        aiRunning = true;
        aiThread = new Thread(() ->
        {
            AIPlayer ai = (AIPlayer) currentViewPlayer.getPlayer();
            randomSleep(1400, 1800);

            //one player is exposable and the ai choose to expose him or not
            if (gameTable.isExposable(gameTable.getTurnManager().previous()) && ai.choiceFactor())
            {
                Player player = players[gameTable.getTurnManager().previous()];
                exposedAnimation().Join();
                gameTable.expose(player);
                randomSleep(400, 600);
            }
            //the ai has one card and didnt say one
            if (!ai.hasSaidOne() && ai.hasOne()){ shoutUnoAnimation(ai); }

            //CHECK PLAYABLE CARDS
            List<Card> playableCards = gameTable.getCurrentPlayerPLayableCards();
            aiRunning = false;
            if (playableCards.size() == 0) //DOESN'T HAVE PLAYABLE CARDS
            {
                if (!ai.hasDrew() && !ai.hasPlayed())
                {
                    drawCardAnimation(currentViewPlayer, new ViewCard()).Join();
                    gameTable.drawCard(ai);
                }
                else gameTable.passTurn();
            }
            else  //HAS PLAYABLE CARDS
            {
                Card playedCard = playableCards.get(0);
                ViewCard relatedImage = currentViewPlayer.getImagesHand().stream().filter(ci -> ci.getCard().equals(playedCard)).toList().get(0);
                playCardAnimation(relatedImage).Join();

                ActionPerformResult res = gameTable.playCard(gameTable.getCurrentPlayerPLayableCards().get(0));
                if (res != ActionPerformResult.PLAYER_WON) gameTable.cardActionPerformance(gameTable.getOptions().build());
            }
        });
        aiThread.setName(currentViewPlayer.getPlayer().getName());
        aiThread.start();
    }

    /**
     * Method called to display the play card {@link Animation}, given the {@link ViewCard}.
     * The {@link ViewCard} in the hand of the player becomes invisible, to give the feeling of instant playing, while the animation uses another {@link ViewCard}
     * If the animation is already running, it does nothing
     * @param card the card to play
     * @return the running animation that can be waited
     */
    public Animation playCardAnimation(ViewCard card)
    {
        if (animationRunning(movingAnimation)) return null;

        movingAnimation = new MovingAnimation(card.getPosition().getX(), card.getPosition().getY(), discard.getPosition().getX(), discard.getPosition().getY(), card);
        animations.add(movingAnimation);
        card.setPaintedImage(null);
        AudioManager.getInstance().setEffect(AudioManager.Effect.PLAY);
        try{ AudioManager.getInstance().setEffect(AudioManager.Effect.valueOf(card.getCard().getValue().name())); }  catch (Exception ignored){}

        //IF PLAYER IS PLAYING THE NEXT TO LAST CARD, THEN SHOUT UNO
        if (currentViewPlayer.getPlayer().getHand().size() == 2) shoutUnoAnimation(currentViewPlayer.getPlayer());
        return movingAnimation;
    }

    /**
     * Method called to display the draw card animation, given the current {@link ViewPlayer} and the {@link ViewCard}.
     * If the animation is already running, it does nothing
     * @param currentViewPlayer the player that played the card
     * @param drawnCard the card drawed
     * @return the running animation that can be waited
     */
    public Animation drawCardAnimation(ViewPlayer currentViewPlayer, ViewCard drawnCard)
    {
        if (animationRunning(movingAnimation)) return null;

        Rectangle lastCardPosition = currentViewPlayer.getImagesHand().get(currentViewPlayer.getImagesHand().size() - 1).getPosition();
        AudioManager.getInstance().setEffect(AudioManager.Effect.DRAW_CARD);
        movingAnimation = new MovingAnimation(deck.getPosition().getX(), deck.getPosition().getY(), lastCardPosition.getX(), lastCardPosition.getY(), drawnCard);

        animations.add(movingAnimation);
        return movingAnimation;
    }

    /**
     * Method called to display the flip card animation when the user is drawing, given the {@link ViewCard}.
     * If the animation is already running, it does nothing
     * @param drawnCard the card drawed
     * @return the running animation that can be waited
     */
    public Animation flipCardAnimation(ViewCard drawnCard)
    {
        if (animationRunning(flipAnimation)) return null;
        flipAnimation = new FlippingAnimation(drawnCard, deck.getPosition());
        animations.add(flipAnimation);
        AudioManager.getInstance().setEffect(AudioManager.Effect.FLIP);
        return flipAnimation;
    }

    /**
     * Method called to display the shout uno animation, given the {@link Player} that has one card.
     * If the Player is an {@link AIPlayer}, it may not say uno
     * @param player
     */
    public void shoutUnoAnimation(Player player)
    {
        if ((player instanceof HumanPlayer && player.hasSaidOne()) || (player instanceof AIPlayer a && a.chooseToSayUno()))
        {//player said one
            AudioManager.getInstance().setEffect(AudioManager.Effect.UNO);
            animations.add(new TextAnimation(imagePath + "uno.gif", centerX, centerY));
        }
    }

    /**
     * Method called to display the expose player animation, when a player has one card and didnt said uno.
     * @return the running animation that can be waited
     */
    public Animation exposedAnimation()
    {
        AudioManager.getInstance().setEffect(AudioManager.Effect.ERROR);
        Animation animation = new TextAnimation(imagePath + "exposed.gif", centerX, centerY);
        animations.add(animation);
        return animation;
    }

    /**
     * Checks if the given animation is alive, i.e. is running
     * @param anim
     * @return true if alive, false otherwise
     */
    public boolean animationRunning(Animation anim){ return anim != null && anim.isAlive(); }

    /**
     * Draws the Win ladder, displaying the player in descending order of the points
     * @param g2
     */
    private void drawLadder(Graphics2D g2)
    {
        Composite noTransparent = g2.getComposite();

        //BACKGROUND
        g2.setColor(java.awt.Color.BLACK);
        g2.setComposite(transparent);
        g2.fillRect(ladderX, ladderY, ladderWidth, ladderHeight);

        //BORDER
        g2.setColor(java.awt.Color.WHITE);
        g2.setComposite(noTransparent);
        g2.setStroke(a);
        g2.drawRect(ladderX, ladderY, ladderWidth, ladderHeight);

        //TITLE
        g2.setFont(titleFont);
        int titleWidth = g2.getFontMetrics().stringWidth("RESULTS");
        int titleHeight = g2.getFontMetrics().getHeight();
        int titleX = centerX - titleWidth / 2;
        int titleY = ladderY + ladderHeight / 10;
        g2.setColor(java.awt.Color.YELLOW);
        g2.drawString("RESULTS", titleX, titleY);

        //NAMES
        g2.setFont(ladderFont);
        g2.setColor(java.awt.Color.WHITE);
        int namesX = ladderX + ladderWidth / 10;
        int namesY = titleY + titleHeight + ladderHeight / 15;
        int incrementY = g2.getFontMetrics().getHeight() + ladderHeight / 10;
        int pointsX = namesX + ladderWidth / 2;
        g2.setColor(java.awt.Color.GREEN);
        for (Player p : ladder){
            String points = String.valueOf(p.getPoints());
            g2.drawString(p.getName(), namesX, namesY);
            g2.drawString(points, pointsX, namesY);
            g2.drawLine(namesX - 10, namesY + 5, pointsX + g2.getFontMetrics().stringWidth(points) + 10, namesY + 5);
            g2.setColor(java.awt.Color.WHITE);
            namesY += incrementY;
        }

        //EXIT-CONTINUE
        if (currentState == State.MATCH_WIN) continueString = "EXIT";
        g2.setColor(playerBackground);
        int continueWidth = g2.getFontMetrics().stringWidth(continueString);
        int continueHeight = g2.getFontMetrics().getHeight();
        int continueX = ladderX + ladderWidth - continueWidth - ladderWidth / 10;
        int continueY = ladderY + ladderHeight - ladderHeight / 15;
        g2.drawString(continueString, continueX, continueY);
        continuePosition.setRect(continueX, continueY - continueHeight, continueWidth, continueHeight);
    }

    /**
     * Calculates the width needed by each {@link ViewPlayer}'s card to draw.
     * If the cards to draw are 0, then catch the exception and returns the given cards space
     * @param player
     * @param cardsSpace the total width of the cards
     * @return the draw width for each card
     */
    private int getCardsWidth(ViewPlayer player, int cardsSpace)
    {
        int cardsWidth;
        try{ cardsWidth = cardsSpace / player.getPlayer().getHand().size(); }
        catch (ArithmeticException e){ cardsWidth = cardsSpace; } //IF PLAYER HAS NO CARDS
        return cardsWidth;
    }

    /**
     *
     * @param player
     * @param g2
     * @param y
     */
    private void drawHorizontalHand(ViewPlayer player, Graphics2D g2, int y)
    {
        int cardsSpace = Math.min(player.getPlayer().getHand().size() * ViewCard.width, maxCardsWidth);
        int startX = (getWidth() - cardsSpace) / 2;
        int cardsWidth = getCardsWidth(player, cardsSpace);

        int width = ViewCard.width;
        int height = ViewCard.height;
        int namesY = y - 50;
        if (y == 0){
            namesY = height + 50;
            startX += width;
            y += height;
            width = -width;
            height = -height;
        }
        drawNames(player, maxCardsWidth - 100, namesY, g2, 65, 40);

        for (ViewAnimableCard card :player.getImagesHand())
        {
            g2.drawImage(card.getPaintedImage(), startX, y + (int) card.getShiftHeight(), width, height, null);
            card.setPosition(startX, y, cardsWidth);
            startX += cardsWidth;
        }
    }

    /**
     *
     * @param player
     * @param g2
     * @param x
     */
    private  void drawVerticalHand(ViewPlayer player, Graphics2D g2, int x)
    {
        int cardsSpace = Math.min(player.getPlayer().getHand().size() * ViewCard.width, maxCardsHeight);
        int startY = (getHeight() - cardsSpace) / 2;
        int cardsWidth = getCardsWidth(player,cardsSpace);

        drawNames(player, x == 0 ? ViewCard.height + 50 : x - 100, maxCardsHeight - 100, g2, 5, 80);

        for (ViewCard card : player.getImagesHand()){
            g2.drawImage(card.getPaintedImage(), x, startY, ViewCard.height, ViewCard.width, null);
            card.setPosition(x, startY, cardsWidth, true);
            startY += cardsWidth;
        }
    }

    /**
     * Given the x and y it draws the name of the given {@link ViewPlayer} with a small colorful background, based on the player status.
     * Then it draws the profile picture at the given imageX and imageY.
     * It saves the position of players' name on screen so that they can be clickable in order to be exposed.
     * If the player is the user it draws the strings to shout uno and to skip turn, based on the necessity, and saves their position.
     * @param viewPlayer the player to draw
     * @param x the x on screen
     * @param y the y on screen
     * @param g2 graphic object
     * @param imageX the x of the profile picture
     * @param imageY the x of the profile picture
     */
    private void drawNames(ViewPlayer viewPlayer, int x, int y, Graphics2D g2, int imageX, int imageY)
    {
        Player player = viewPlayer.getPlayer();
        g2.setFont(fontNames);
        g2.setColor(player.hasOne() && player.hasSaidOne() ? java.awt.Color.RED : player.equals(currentViewPlayer.getPlayer()) ? currentPlayerBackground : playerBackground);
        int width = g2.getFontMetrics().stringWidth(player.getName());
        int height = g2.getFontMetrics().getHeight();
        g2.fillRoundRect(x-5, y - height + 5, width + 10, height, 20, 20);
        g2.setColor(java.awt.Color.BLACK);
        g2.drawString(player.getName(), x, y);
        viewPlayer.setNamePosition(x-5, y - height + 5, width + 10, height);

        viewPlayer.getProfilePicture().paintImage(g2, x - imageX, y - imageY);

        //CLICKABLE SKIP TURN - UNO
        if (player instanceof HumanPlayer){
            y += height;
            if (player.hasOne() && !player.hasSaidOne()){
                g2.drawString("UNO!", x, y);
                width = g2.getFontMetrics().stringWidth("UNO!");
                unoPosition.setRect(x, y - height, width, height);
            }
            if (player.hasDrew() && currentState == State.PLAYER_TURN){
                x += width + 20;
                g2.drawString("Skip turn", x, y);
                width = g2.getFontMetrics().stringWidth("Skip Turn");
                skipTurnPosition.setRect(x, y - height, width, height);
            }
        }
    }

    //CONTROLLER USAGE

    /**
     * Resumes the paused game. If the user changed the deck color the cards are updated
     */
    public void resumeGame()
    {
        createCards();
        currentState = calculateState();
    }

    /**
     * Pause the game
     */
    public void pauseGame() {
        currentState = State.GAME_PAUSED;
    }

    /**
     * Sets to flag to reapint the view to false and stop all running animations
     */
    public void stopTimer()
    {
        animations.forEach(Animation::Stop);
        gameRunning = false;
    }

    //GETTERS
    public State calculateState()  { return currentViewPlayer.getPlayer() instanceof HumanPlayer ? State.PLAYER_TURN : State.AI_TURN; }
    public State getCurrentState(){return currentState;}
    public Player[] getPlayers() {return players;}
    public ViewCard getDeck() {return deck;}
    public ViewPlayer[] getViewPlayers() { return viewPlayers; }
    public Rectangle getSkipTurnPosition() {return skipTurnPosition;}
    public Rectangle getUnoPosition() {return unoPosition;}
    public Rectangle getContinuePosition() {return continuePosition;}
}

