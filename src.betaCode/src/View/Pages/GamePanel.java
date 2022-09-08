package View.Pages;

import Model.Cards.Card;
import Model.Cards.CardColor;
import Model.Player.AIPlayer;
import Model.Player.HumanPlayer;
import Model.Player.Player;
import Model.Rules.ActionPerformResult;
import Model.UnoGameTable;
import Utilities.AudioManager;
import Utilities.Config;
import Utilities.Utils;
import View.Animations.*;
import View.Elements.GraphicQuality;
import View.Elements.ViewAnimableCard;
import View.Elements.ViewCard;
import View.Elements.ViewPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class GamePanel extends JPanel implements Observer
{
    public enum State
    {
        PLAYER_TURN,
        AI_TURN,
        GAME_PAUSED,
        WIN,
        ACTUAL_WIN
    }
    private static final String imagePath = MainFrame.IMAGE_PATH+"GamePanel/";

    private final Color green = new Color(14, 209, 69);
    private final Color playerBackground = new Color(101, 132, 247, 160);
    private final Color currentPlayerBackground = new Color(255, 209, 26, 160);
    private final Font fontNames;
    private final Font titleFont;
    private final Font ladderFont;

    private int maxCardsWidth = 1350;
    private int maxCardsHeight = 810;

    private Thread aiThread;
    private boolean aiIsAlive = false;
    private Thread gameThread;
    private boolean gameRunning = true;

    private final int centerX;
    private final int centerY;

    private ViewPlayer[] viewPlayers;
    private Player[] players;
    private ViewPlayer currentViewPlayer;
    private Card lastCard;
    private ViewCard deck;
    private int deckSize;
    private ViewCard discard;
    private State currentState;
    private MovingAnimation movingAnimation;
    private ArrayList<Animation> animations;
    private FlippingAnimation flipAnimation;
    private final RotatingAnimation rotatingAnimation;
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

    public GamePanel(ViewPlayer[] viewPlayers)
    {
        this.viewPlayers = viewPlayers;
        setOpaque(true);
        setDoubleBuffered(true);
        //debug
        setBackground(Color.GREEN);
        InitializeComponents();
        ViewCard.height = (int) (180 * Config.scalingPercentage);
        ViewCard.width = (int) (120 * Config.scalingPercentage);
        fontNames = new Font("Digital-7", Font.PLAIN, (int) (25 * Config.scalingPercentage));
        titleFont = new Font("Digital-7", Font.BOLD, (int) (60 * Config.scalingPercentage));
        ladderFont = new Font("Digital-7", Font.BOLD, (int) (40 * Config.scalingPercentage));

        var screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        centerX = screenSize.width / 2;
        centerY = screenSize.height / 2;

        ladderX = centerX - ladderWidth / 2;
        ladderY = centerY - ladderHeight / 2;

        maxCardsWidth = (int) (maxCardsWidth * Config.scalingPercentage);
        maxCardsHeight = (int) (maxCardsHeight * Config.scalingPercentage);

        rotatingAnimation = new RotatingAnimation(imagePath, centerX, centerY);
        animations.add(rotatingAnimation);

        repaintView();

    }
    private void repaintView()
    {
        gameThread = new Thread(() ->
        {
            while(gameRunning) repaint();
        },"gameViewPeriodicRepaint");
        gameThread.start();
    }

    /**
     * When mouse enters a card this one goes up and the others go down
     * @param e
     */
    public void animateCardsOnHovering(MouseEvent e)
    {
        Point mouseClickPosition = e.getPoint();
        for (ViewAnimableCard card : viewPlayers[0].getImagesHand())
            card.setShiftHeight(card.contains(mouseClickPosition) ? -30 : 0);
    }

    /**
     * Show up a dialog in which the user must choose a color among RED, YELLOW, BLUE, GREEN and returns it as a CardColor
     * used to change color in wild actions
     * @return
     */
    public CardColor choseColorByUser()
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
        return CardColor.valueOf(colors[choice]);
    }

    /**
     * Show up a dialog in which the user must choose a player among the others players name
     * @return
     */
    public Player chosePlayerToSwap()
    {
        //String[] playersNames = new String[players.length];
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


    private void InitializeComponents()
    {
        deck = new ViewCard();
        animations = new ArrayList<>();
        skipTurnPosition = new Rectangle();
        unoPosition = new Rectangle();
        continuePosition = new Rectangle();
    }

//    ///debug
//    int count = 0;
//    int dps;
//    double media;
//    ///debug

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);


        ///Debug
        //long start = System.nanoTime();

        g.setColor(green);
        g.fillRect(0,0, getWidth(), getHeight());

        Graphics2D g2 = (Graphics2D) g;
        if (Config.graphicQuality == GraphicQuality.HIGH) Utils.applyQualityRenderingHints(g2);

        drawHorizontalHand(viewPlayers[0], g2, getHeight() - ViewCard.height);
        drawHorizontalHand(viewPlayers[2], g2, 0);
        drawVerticalHand(viewPlayers[1], g2, getWidth() - ViewCard.height);
        drawVerticalHand(viewPlayers[3], g2, 0);

        g2.drawImage(discard.getCardImage(), centerX + 25, centerY - ViewCard.height / 2, ViewCard.width, ViewCard.height, null);
        discard.setPosition(centerX + 25, centerY - ViewCard.height / 2, ViewCard.width);

        if (deckSize > 1)
        {
            int x = centerX - 25 - ViewCard.width;
            int y = (centerY - ViewCard.height / 2)-10;
            g2.setFont(fontNames);
            g2.setColor(Color.green);
            int width = g2.getFontMetrics().stringWidth(String.valueOf(deckSize));
            int height = g2.getFontMetrics().getHeight();
            g2.fillRoundRect(x-5, y - height + 5, width + 10, height, 20, 20);
            g2.setColor(Color.BLACK);
            g2.drawString(String.valueOf(deckSize), x, y);
            g2.drawImage(deck.getCardImage(), x, y+10, ViewCard.width, ViewCard.height, null);
            deck.setPosition(x, y, ViewCard.width);
        }

        Iterator<Animation> iter = animations.iterator();
        while(iter.hasNext()){
            Animation animation = iter.next();
            if (animation.isAlive()) animation.paint(g2);
            else iter.remove();
        }

        if (currentState == State.WIN || currentState == State.ACTUAL_WIN) drawLadder(g2);

//        ///debug
//        long end = System.nanoTime();
//        media += (end - start) / 1000000000.0;
//        count += 1;
//        if (count == 60)
//        {
//            media /= 60;
//            count = 0;
//            dps = (int) (1 / media);
//        }
//        g2.drawString(String.valueOf(dps), 20, 20);
//        ///debug
        g2.dispose();
    }

    @Override
    public void update(Observable o, Object arg)
    {
        UnoGameTable gameTable = (UnoGameTable) o;
        lastCard = gameTable.getLastCard();
        createCards();
        currentViewPlayer = viewPlayers[gameTable.currentPlayerIndex()];

        if(gameTable.hasWin())
        {
            System.out.println("CHECK WIN CON " + currentViewPlayer.getPlayer());
            boolean win = gameTable.checkWin(currentViewPlayer.getPlayer());
            ladder = Arrays.stream(viewPlayers).map(ViewPlayer::getPlayer).sorted(Comparator.comparing(Player::getPoints).reversed()).toList();
            currentState = win ? State.ACTUAL_WIN : State.WIN;
            AudioManager.getInstance().setEffect(AudioManager.Effect.WIN);
            return;
        }

        players = gameTable.getPlayers();
        deckSize = gameTable.getDeck().size();

        int prePreviousPlayer = gameTable.getTurnManager().previous(gameTable.getTurnManager().previous());
        if(viewPlayers[prePreviousPlayer].getPlayer() instanceof AIPlayer && gameTable.isExposable(prePreviousPlayer) )
            players[prePreviousPlayer].shoutUno(); //no one exposed him for this turn, he is safe
        if (currentViewPlayer.getPlayer().isBlocked())
        {//he has been blocked
            currentViewPlayer.getPlayer().setBlocked(false);
            gameTable.passTurn();
            return;
        }
        if (rotatingAnimation.isAlive())
        {//set correct turn flow (color and verse)
            rotatingAnimation.changeTurn(gameTable.antiClockwiseTurn());
            rotatingAnimation.imageColor(discard.getCard().getColor());
        }
        if (currentState != State.GAME_PAUSED)
            currentState = calculateState(); //game had been paused now update the sate
        if (currentState == State.AI_TURN && !aiIsAlive)
            asyncAITurn(gameTable); //artificial intelligence turn
    }

    public void createCards()
    {
        try
        {
            int[] rotations = new int[]{0, 270, 180, 90};

            for (ViewPlayer viewPlayer : viewPlayers){
                int rotation = Arrays.stream(viewPlayers).toList().indexOf(viewPlayer);
                viewPlayer.setImagesHand(viewPlayer.getPlayer().getHand().stream().map(c -> new ViewAnimableCard(c, rotations[rotation])).collect(Collectors.toCollection(ArrayList::new)));
            }
            discard = new ViewCard(lastCard);
        }
        catch (ConcurrentModificationException cme){ }
    }

    private void randomSleep(int min, int max)
    {
        try { Thread.sleep(new Random().nextInt(min, max)); }
        catch (InterruptedException e) {}
    }

    public void asyncAITurn(UnoGameTable gameTable)
    {
        aiIsAlive = true;
        aiThread = new Thread(() ->
        {
            AIPlayer ai = (AIPlayer) currentViewPlayer.getPlayer();
            randomSleep(1300, 1500);
            if (gameTable.isExposable(gameTable.getTurnManager().previous()) && ai.choiceFactor())
            {//one player is exposable and the ai choices to expose him
                Player player = players[gameTable.getTurnManager().previous()];
                exposedAnimation().Join();
                gameTable.expose(player);
                randomSleep(300, 500);
            }
            if (!ai.hasSaidOne() && ai.hasOne()){ shoutUnoAnimation(ai); }
            List<Card> playableCards = gameTable.getCurrentPlayerPLayableCards();
            aiIsAlive = false;
            if (playableCards.size() == 0)
            {
                if (!ai.hasDrew() && !ai.hasPlayed())
                {
                    drawCardAnimation(currentViewPlayer, new ViewCard()).Join();
                    gameTable.drawCard(ai);
                }
                else gameTable.passTurn();
            }
            else
            {
                Card playedCard = playableCards.get(0);
                ViewCard relatedImage = currentViewPlayer.getImagesHand().stream().filter(ci -> ci.getCard().equals(playedCard)).toList().get(0);
                playCardAnimation(relatedImage).Join();

                ActionPerformResult res = gameTable.playCard(gameTable.getCurrentPlayerPLayableCards().get(0));
                if (res != ActionPerformResult.WIN) gameTable.cardActionPerformance(gameTable.getOptions().build());
            }
        });
        aiThread.setName(currentViewPlayer.getPlayer().getName());
        aiThread.start();
    }

    public Animation playCardAnimation(ViewCard card)
    {
        if (animationRunning(movingAnimation)) return null;
        movingAnimation = new MovingAnimation(card.getPosition().getX(), card.getPosition().getY(), discard.getPosition().getX(), discard.getPosition().getY(), card);
        animations.add(movingAnimation);
        card.setPaintedImage(null);
        AudioManager.getInstance().setEffect(AudioManager.Effect.PLAY);
        try{ AudioManager.getInstance().setEffect(AudioManager.Effect.valueOf(card.getCard().getValue().name())); }  catch (Exception ignored){}
        if (currentViewPlayer.getPlayer().getHand().size() == 2) shoutUnoAnimation(currentViewPlayer.getPlayer());
        return movingAnimation;
    }

    public Animation drawCardAnimation(ViewPlayer currentViewPlayer, ViewCard drawnCard)
    {
        if (animationRunning(movingAnimation)) return null;

        Rectangle lastCardPosition = currentViewPlayer.getImagesHand().get(currentViewPlayer.getImagesHand().size() - 1).getPosition();
        AudioManager.getInstance().setEffect(AudioManager.Effect.DRAW_CARD);
        movingAnimation = new MovingAnimation(deck.getPosition().getX(), deck.getPosition().getY(), lastCardPosition.getX(), lastCardPosition.getY(), drawnCard);

        animations.add(movingAnimation);
        return movingAnimation;
    }

    public Animation flipCardAnimation(ViewCard drawnCard)
    {
        if (animationRunning(flipAnimation)) return null;
        flipAnimation = new FlippingAnimation(drawnCard, deck.getPosition());
        animations.add(flipAnimation);
        AudioManager.getInstance().setEffect(AudioManager.Effect.FLIP);
        return flipAnimation;
    }

    public void shoutUnoAnimation(Player player)
    {
        if ((player instanceof HumanPlayer && player.hasSaidOne()) || (player instanceof AIPlayer a && a.chooseToSayUno()))
        {//player said one
            AudioManager.getInstance().setEffect(AudioManager.Effect.UNO);
            animations.add(new TextAnimation(imagePath + "uno.gif", centerX, centerY));
        }
    }

    public Animation exposedAnimation()
    {
        AudioManager.getInstance().setEffect(AudioManager.Effect.ERROR);
        Animation animation = new TextAnimation(imagePath + "exposed.gif", centerX, centerY);
        animations.add(animation);
        return animation;
    }

    public boolean animationRunning(Animation anim){return anim != null && anim.isAlive();}

    private void drawLadder(Graphics2D g2)
    {
        Composite noTransparent = g2.getComposite();

        g2.setColor(Color.BLACK);
        g2.setComposite(transparent);
        g2.fillRect(ladderX, ladderY, ladderWidth, ladderHeight);

        g2.setColor(Color.WHITE);
        g2.setComposite(noTransparent);
        g2.setStroke(a);
        g2.drawRect(ladderX, ladderY, ladderWidth, ladderHeight);

        g2.setFont(titleFont);
        int titleWidth = g2.getFontMetrics().stringWidth("RESULTS");
        int titleHeight = g2.getFontMetrics().getHeight();
        int titleX = centerX - titleWidth / 2;
        int titleY = ladderY + ladderHeight / 10;
        g2.setColor(Color.YELLOW);
        g2.drawString("RESULTS", titleX, titleY);

        g2.setFont(ladderFont);
        g2.setColor(Color.WHITE);
        int namesX = ladderX + ladderWidth / 10;
        int namesY = titleY + titleHeight + ladderHeight / 15;
        int incrementY = g2.getFontMetrics().getHeight() + ladderHeight / 10;
        int pointsX = namesX + ladderWidth / 2;
        g2.setColor(Color.GREEN);
        for (Player p : ladder){
            String points = String.valueOf(p.getPoints());
            g2.drawString(p.getName(), namesX, namesY);
            g2.drawString(points, pointsX, namesY);
            g2.drawLine(namesX - 10, namesY + 5, pointsX + g2.getFontMetrics().stringWidth(points) + 10, namesY + 5);
            g2.setColor(Color.WHITE);
            namesY += incrementY;
        }

        if (currentState == State.ACTUAL_WIN) continueString = "EXIT";
        g2.setColor(playerBackground);
        int continueWidth = g2.getFontMetrics().stringWidth(continueString);
        int continueHeight = g2.getFontMetrics().getHeight();
        int continueX = ladderX + ladderWidth - continueWidth - ladderWidth / 10;
        int continueY = ladderY + ladderHeight - ladderHeight / 15;
        g2.drawString(continueString, continueX, continueY);
        continuePosition.setRect(continueX, continueY - continueHeight, continueWidth, continueHeight);
    }

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

    private int getCardsWidth(ViewPlayer player, int cardsSpace)
    {
        int cardsWidth;
        try
        {
            cardsWidth = cardsSpace / player.getPlayer().getHand().size();
        }
        catch (ArithmeticException e)
        {
            cardsWidth = cardsSpace;
        }
        return cardsWidth;
    }

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

    private void drawNames(ViewPlayer viewPlayer, int x, int y, Graphics2D g2, int imageX, int imageY)
    {
        Player player = viewPlayer.getPlayer();
        g2.setFont(fontNames);
        g2.setColor(player.hasSaidOne() ? Color.RED : player.equals(currentViewPlayer.getPlayer()) ? currentPlayerBackground : playerBackground);
        int width = g2.getFontMetrics().stringWidth(player.getName());
        int height = g2.getFontMetrics().getHeight();
        g2.fillRoundRect(x-5, y - height + 5, width + 10, height, 20, 20);
        g2.setColor(Color.BLACK);
        g2.drawString(player.getName(), x, y);
        viewPlayer.setNamePosition(x-5, y - height + 5, width + 10, height);

        viewPlayer.getProfilePicture().paintImage(g2, x - imageX, y - imageY);

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

    public void endGame() { Arrays.stream(viewPlayers).forEach(ViewPlayer::reset); }


    //controller usage

    public void resumeGame()
    {
        createCards();
        currentState = calculateState();
    }

    public void pauseGame() { currentState = State.GAME_PAUSED; }

    public void stopTimer()
    {
        animations.forEach(Animation::Stop);
        gameRunning = false;
    }

    public State calculateState()  { return currentViewPlayer.getPlayer() instanceof HumanPlayer ? State.PLAYER_TURN : State.AI_TURN; }

    public State getCurrentState(){return currentState;}

    public Player[] getPlayers() {return players;}

    public ViewCard getDeck() {return deck;}

    public ViewPlayer[] getViewPlayers() { return viewPlayers; }

    public Rectangle getSkipTurnPosition() {return skipTurnPosition;}

    public Rectangle getUnoPosition() {return unoPosition;}

    public Rectangle getContinuePosition() {return continuePosition;}

    public void cardNotPLayableEffects() { AudioManager.getInstance().setEffect(AudioManager.Effect.NOT_VALID); }
}

