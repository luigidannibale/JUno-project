package View.Pages;

import Model.Cards.Card;
import Model.Cards.CardColor;
import Model.Player.AIPlayer;
import Model.Player.HumanPlayer;
import Model.Player.Player;
import Model.UnoGameTable;
import Utilities.AudioManager;
import Utilities.Config;
import Utilities.Utils;
import View.Animations.Animation;
import View.Animations.FlippingAnimation;
import View.Animations.MovingAnimation;
import View.Animations.RotatingAnimation;
import View.Elements.ViewAnimableCard;
import View.Elements.ViewCard;
import View.Elements.GraphicQuality;
import View.Elements.ViewRotatableImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.stream.Collectors;

public class GamePanel extends JPanel implements Observer {
    public enum State{
        PLAYER_TURN,
        AI_TURN,
        GAME_PAUSED;
    }
    private static final String imagePath = "resources/images/MainFrame/GamePanel/";
    private final Color green = new Color(14, 209, 69);
    private final Color playerBackground = new Color(101, 132, 247, 160);
    private final Color currentPlayerBackground = new Color(255, 209, 26, 160);
    private final Font fontNames;

    private int maxCardsWidth = 1350;
    private int maxCardsHeight = 810;

    Thread aiThread;
    Thread gameThread;
    boolean gameRunning = true;

    private final int centerX;
    private final int centerY;

    private Player[] players;
    private HashMap<Player, ArrayList<ViewAnimableCard>> playerHands;
    private Player currentPlayer;                   //da sistemare?
    private Card lastCard;
    private ViewCard deck;
    private int deckSize;
    private ViewCard discard;
    private State currentState;
    private MovingAnimation movingAnimation;
    private ArrayList<Animation> animations;
    private FlippingAnimation flipAnimation;
    private RotatingAnimation rotatingAnimation;
    private Rectangle skipTurnPosition;
    private Rectangle unoPosition;

    public GamePanel()
    {

        setOpaque(true);
        setDoubleBuffered(true);
        //debug
        setBackground(Color.GREEN);
        InitializeComponents();
        ViewCard.height = (int) (180 * Config.scalingPercentage);
        ViewCard.width = (int) (120 * Config.scalingPercentage);
        fontNames = new Font("Digital-7", Font.PLAIN, (int) (25 * Config.scalingPercentage));

        var screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        centerX = screenSize.width / 2;
        centerY = screenSize.height / 2;

        maxCardsWidth = (int) (maxCardsWidth * Config.scalingPercentage);
        maxCardsHeight = (int) (maxCardsHeight * Config.scalingPercentage);

        rotatingAnimation = new RotatingAnimation(imagePath, centerX, centerY);
        animations.add(rotatingAnimation);

        repaintView();

    }
    private void repaintView()
    {
        gameThread = new Thread(() -> { while(gameRunning) repaint(); },"gameViewPeriodicRepaint");
        gameThread.start();
    }

    /**
     * When mouse enters a card this one goes up and the others go down
     * @param e
     */
    public void animateCardsOnHovering(MouseEvent e)
    {
        Point mouseClickPosition = e.getPoint();
        for (ViewAnimableCard card : playerHands.get(players[0]))
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
                            null,
                            "Chose a card color",
                            2,
                            JOptionPane.QUESTION_MESSAGE,
                            new ImageIcon(imagePath + "wild.png"),
                            colors, colors[0]);
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

        Arrays.stream(players).filter(p -> currentPlayer != p).forEach(p -> playerHashMap.put(p.getName(), p));
        int choice = -1;
        while (choice == -1)
            choice = JOptionPane.showOptionDialog(
                null,
                null,
                "Chose the player to swap hand with",
                2,
                JOptionPane.QUESTION_MESSAGE,
                null,
                playerHashMap.keySet().toArray(),playerHashMap.keySet().toArray()[0] );
        return (Player) playerHashMap.values().toArray()[choice];
    }


    private void InitializeComponents()
    {
        deck = new ViewCard();
        playerHands = new HashMap<>();
        animations = new ArrayList<>();
        skipTurnPosition = new Rectangle();
        unoPosition = new Rectangle();
    }

    ///debug
    int count = 0;
    int dps;
    double media;
    ///debug
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        long start = System.nanoTime();

        g.setColor(green);
        g.fillRect(0,0, getWidth(), getHeight());

        Graphics2D g2 = (Graphics2D) g;
        if (Config.graphicQuality == GraphicQuality.HIGH) Utils.applyQualityRenderingHints(g2);

        if (players != null)
        {
            drawHorizontalHand(players[0], g2, getHeight() - ViewCard.height);
            drawHorizontalHand(players[2], g2, 0);
            drawVerticalHand(players[1], g2, getWidth() - ViewCard.height);
            drawVerticalHand(players[3], g2, 0);

            g2.drawImage(discard.getCardImage(), centerX + 25, centerY - ViewCard.height / 2, ViewCard.width, ViewCard.height, null);
            discard.setPosition(centerX + 25, centerY - ViewCard.height / 2, ViewCard.width);

            //centerX = getWidth() / 2;
            //centerY = getHeight() / 2;

            if (deckSize > 1) {
                int x = centerX - 25 - ViewCard.width;
                int y = centerY - ViewCard.height / 2;
                g2.drawString(String.valueOf(deckSize), x, y - 10);
                g2.drawImage(deck.getCardImage(), x, y, ViewCard.width, ViewCard.height, null);
                deck.setPosition(x, y, ViewCard.width);
            }

            //rotatingAnimation.paint(g2);

            Iterator<Animation> iter = animations.iterator();
            while(iter.hasNext()){
                Animation animation = iter.next();
                if (animation.isAlive()) animation.paint(g2);
                else iter.remove();
            }
        }

        ///debug
        long end = System.nanoTime();
        media += (end - start) / 1000000000.0;
        count += 1;
        if (count == 60){
            media /= 60;
            //System.out.println("Draw time: " + media + "sec");
            count = 0;
            dps = (int) (1 / media);
        }
        g2.drawString(String.valueOf(dps), 20, 20);
        ///debug
        g2.dispose();
    }

    //trying to generalize
    @Override
    public void update(Observable o, Object arg)
    {
        //potremmo passare un infomazione come arg per dire quale animazione startare nel caso di pesca carte ai
        UnoGameTable gameTable = (UnoGameTable) o;
        players = gameTable.getPlayers();
        lastCard = gameTable.getLastCard();
        deckSize = gameTable.getDeck().size();
        if (Arrays.stream(players).filter(p -> p.getHand().size() == 0).count() == 1) gameRunning = false;
        createCards();
        currentPlayer = gameTable.currentPlayer();

        if (currentPlayer.isBlocked()) {
            currentPlayer.setBlocked(false);
            gameTable.passTurn();
            return;
        }

        if (currentState != State.GAME_PAUSED) currentState = getState();

        if (rotatingAnimation.isAlive())
        {
            rotatingAnimation.changeTurn(gameTable.clockwiseTurn());
            rotatingAnimation.imageColor(discard.getCard().getColor());
        }

        if (currentState == State.AI_TURN) {
            asyncAITurn(gameTable);
            players[0].setDrew(false);
        }

    }

    public void createCards()
    {
        try{
            int[] rotations = new int[]{0, 270, 180, 90};
            for (Player player : players)
            {
                //assert (player.getHand().size() > 0):"Gamepanel->createcards size <= 0";
                int rotation = Arrays.stream(players).toList().indexOf(player);
                if (player instanceof HumanPlayer)
                    playerHands.put(player, player.getHand().stream().map(c -> new ViewAnimableCard(c, rotations[rotation])).collect(Collectors.toCollection(ArrayList::new)));
                else
                    playerHands.put(player, player.getHand().stream().map(c -> new ViewAnimableCard(c, rotations[rotation])).collect(Collectors.toCollection(ArrayList::new)));
                //qui devono essere solo rotatable
            }
            discard = new ViewCard(lastCard);
        }
        catch (ConcurrentModificationException cme){
            System.out.println("Si modificano concurremente");
        }
    }

    public void asyncAITurn(UnoGameTable gameTable)
    {
        aiThread = new Thread(() -> {
            try {
                AIPlayer ai = (AIPlayer) currentPlayer;
                Thread.sleep(1500);
                if (gameTable.getCurrentPlayerPLayableCards().size() == 0) {
                    if (!ai.hasDrew()) {
                        drawCardAnimation(ai, new ViewCard()).Join();
                        gameTable.drawCard(ai);
                    }
                    else gameTable.passTurn();
                }
                else
                {
                    Card playedCard = ai.chooseBestCards(discard.getCard()).get(0);
                    ViewCard relatedImage = playerHands.get(ai).stream().filter(ci -> ci.getCard().equals(playedCard)).toList().get(0);
                    playCardAnimation(relatedImage).Join();

                    gameTable.playCard(gameTable.getCurrentPlayerPLayableCards().get(0));
                    gameTable.cardActionPerformance(gameTable.getOptions().build());
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw e;
            }
        });
        aiThread.setName(currentPlayer.getName());
        aiThread.start();
    }

    public Animation playCardAnimation(ViewCard card)
    {
        if (animationRunning(movingAnimation)) return null;
        movingAnimation = new MovingAnimation(card.getPosition().getX(), card.getPosition().getY(), discard.getPosition().getX(), discard.getPosition().getY(), card);
        animations.add(movingAnimation);
        card.setPaintedImage(null);
        AudioManager.getInstance().setEffects(AudioManager.Effects.PLAY);
        return movingAnimation;
    }

    public Animation drawCardAnimation(Player currentPlayer, ViewCard drawnCard)
    {
        if (animationRunning(movingAnimation)) return null;

        Rectangle lastCardPosition = playerHands.get(currentPlayer).get(playerHands.get(currentPlayer).size() - 1).getPosition();
        movingAnimation = new MovingAnimation(deck.getPosition().getX(), deck.getPosition().getY(), lastCardPosition.getX(), lastCardPosition.getY(), drawnCard);
        animations.add(movingAnimation);
        return movingAnimation;
    }

    public Animation flipCardAnimation(ViewCard drawnCard)
    {
        if (animationRunning(flipAnimation)) return null;
        flipAnimation = new FlippingAnimation(drawnCard, deck.getPosition());
        animations.add(flipAnimation);
        return flipAnimation;
    }

    public boolean animationRunning(Animation anim){return anim != null && anim.isAlive();}

    private void drawHorizontalHand(Player player, Graphics2D g2, int y)
    {
        int cardsSpace = Math.min(player.getHand().size() * ViewCard.width, maxCardsWidth);
        int startX = (getWidth() - cardsSpace) / 2;
        int cardsWidth = cardsSpace / player.getHand().size();

        int width = ViewCard.width;
        int height = ViewCard.height;
        drawNames(player, maxCardsWidth - 100, y == 0 ? height + 50 : y - 50, g2);
        if (y == 0){
            startX += width;
            y += height;
            width = -width;
            height = -height;
        }

        for (ViewAnimableCard card : playerHands.get(player)){
            g2.drawImage(card.getPaintedImage(), startX, y + (int) card.getShiftHeight(), width, height, null);
            card.setPosition(startX, y, cardsWidth);
            startX += cardsWidth;
        }
    }

    private  void drawVerticalHand(Player player, Graphics2D g2, int x)
    {
        int cardsSpace = Math.min(player.getHand().size() * ViewCard.width, maxCardsHeight);
        int startY = (getHeight() - cardsSpace) / 2;
        int cardsWidth = cardsSpace / player.getHand().size();

        drawNames(player, x == 0 ? ViewCard.height + 50 : x - 100, maxCardsHeight - 100, g2);

        for (ViewCard card : playerHands.get(player)){
            g2.drawImage(card.getPaintedImage(), x, startY, ViewCard.height, ViewCard.width, null);
            card.setPosition(x, startY, cardsWidth, true);
            startY += cardsWidth;
        }
    }

    private void drawNames(Player player, int x, int y, Graphics g2)
    {
        g2.setFont(fontNames);
        g2.setColor(player.equals(currentPlayer) ? currentPlayerBackground : playerBackground);
        int width = g2.getFontMetrics().stringWidth(player.getName());
        int height = g2.getFontMetrics().getHeight();
        g2.fillRoundRect(x-5, y - height + 5, width + 10, height, 20, 20);
        g2.setColor(Color.black);
        g2.drawString(player.getName(), x, y);

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

    //controller usage

    public void pauseGame()
    {
        currentState = State.GAME_PAUSED;
        if (aiThread != null)
            if (aiThread.isAlive())
            {
                try{
                    aiThread.wait();
                }
                catch (Exception e){
                    System.out.println("non waitano");
                }
            }

    }

    public void stopTimer()
    {

        animations.forEach(Animation::Stop);
        gameRunning = false;
    }

    // GETTER
    public State getState()
    { return currentPlayer instanceof HumanPlayer ? State.PLAYER_TURN : State.AI_TURN; }

    public State getCurrentState(){return currentState;}
    public void setCurrentState(State gameMode){currentState = gameMode;}
    public Player getCurrentPlayer() {return currentPlayer;}
    public Player[] getPlayers() {return players;}
    public ViewCard getDeck() {return deck;}
    public HashMap<Player, ArrayList<ViewAnimableCard>> getPlayerHands() {return playerHands;}
    public Rectangle getSkipTurnPosition() {return skipTurnPosition;}
    public Rectangle getUnoPosition() {return unoPosition;}
}

