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

        AudioManager.getInstance().setEffects(AudioManager.Effects.valueOf(colors[choice]));
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

        if (currentState == State.WIN)
        {

            return;
        }

        g.setColor(green);
        g.fillRect(0,0, getWidth(), getHeight());

        Graphics2D g2 = (Graphics2D) g;
        if (Config.graphicQuality == GraphicQuality.HIGH) Utils.applyQualityRenderingHints(g2);

        if (players != null)
        {
            drawHorizontalHand(viewPlayers[0], g2, getHeight() - ViewCard.height);
            drawHorizontalHand(viewPlayers[2], g2, 0);
            drawVerticalHand(viewPlayers[1], g2, getWidth() - ViewCard.height);
            drawVerticalHand(viewPlayers[3], g2, 0);

            g2.drawImage(discard.getCardImage(), centerX + 25, centerY - ViewCard.height / 2, ViewCard.width, ViewCard.height, null);
            discard.setPosition(centerX + 25, centerY - ViewCard.height / 2, ViewCard.width);

            //centerX = getWidth() / 2;
            //centerY = getHeight() / 2;

            if (deckSize > 1)
            {
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

    @Override
    public void update(Observable o, Object arg)
    {
        UnoGameTable gameTable = (UnoGameTable) o;
        players = gameTable.getPlayers();
        lastCard = gameTable.getLastCard();
        deckSize = gameTable.getDeck().size();
        if (Arrays.stream(players).filter(p -> p.getHand().size() == 0).count() == 1) gameRunning = false;
        createCards();

        //currentViewPlayer = Arrays.stream(viewPlayers).filter(c -> c.getPlayer().equals(gameTable.currentPlayer())).toList().get(0);
        //if (gameTable.currentPlayerIndex() == -1) return;
        int prePreviousPlayer = gameTable.getTurnManager().previous(gameTable.getTurnManager().previous());
        if(gameTable.isExposable(prePreviousPlayer) )
            players[prePreviousPlayer].shoutUno();

        currentViewPlayer = viewPlayers[gameTable.currentPlayerIndex()];

        if (currentViewPlayer.getPlayer().isBlocked())
        {
            currentViewPlayer.getPlayer().setBlocked(false);
            gameTable.passTurn();
            return;
        }

        if (currentState != State.GAME_PAUSED) currentState = getState();

        if (rotatingAnimation.isAlive()) {
            rotatingAnimation.changeTurn(gameTable.antiClockwiseTurn());
            rotatingAnimation.imageColor(discard.getCard().getColor());
        }

        if (currentState == State.AI_TURN) {
            asyncAITurn(gameTable);
        }
    }

    public void createCards()
    {
        try{
            int[] rotations = new int[]{0, 270, 180, 90};

            for (ViewPlayer viewPlayer : viewPlayers){
                int rotation = Arrays.stream(viewPlayers).toList().indexOf(viewPlayer);
                viewPlayer.setImagesHand(viewPlayer.getPlayer().getHand().stream().map(c -> new ViewAnimableCard(c, rotations[rotation])).collect(Collectors.toCollection(ArrayList::new)));
            }
            discard = new ViewCard(lastCard);
        }
        catch (ConcurrentModificationException cme){ }
    }

    public void asyncAITurn(UnoGameTable gameTable)
    {
        aiThread = new Thread(() ->
        {
            try
            {
                AIPlayer ai = (AIPlayer) currentViewPlayer.getPlayer();

                Thread.sleep(new Random().nextInt(1300, 2500));
                if (gameTable.isExposable(gameTable.getTurnManager().previous()) && new Random().nextInt(1,4)>1)
                {
                    Player player = players[gameTable.getTurnManager().previous()];
                    exposedAnimation(player).Join();
                    gameTable.expose(player);
                }

                List<Card> playableCards = gameTable.getCurrentPlayerPLayableCards();

                if (playableCards.size() == 0)
                {
                    if (!ai.hasDrew())
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

                    gameTable.playCard(gameTable.getCurrentPlayerPLayableCards().get(0));
                    gameTable.cardActionPerformance(gameTable.getOptions().build());
                }
            }
            catch (InterruptedException e) { System.out.println("interrupted exception"); e.printStackTrace(); }
            catch (Exception e) { System.out.println(e.getClass());e.printStackTrace(); }
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
        AudioManager.getInstance().setEffects(AudioManager.Effects.PLAY);
        try{ AudioManager.getInstance().setEffects(AudioManager.Effects.valueOf(card.getCard().getValue().name())); }  catch (Exception ignored){}
        if (currentViewPlayer.getPlayer().getHand().size() == 2) shoutUnoAnimation(currentViewPlayer.getPlayer());
        return movingAnimation;
    }

    public Animation drawCardAnimation(ViewPlayer currentViewPlayer, ViewCard drawnCard)
    {
        if (animationRunning(movingAnimation)) return null;

        Rectangle lastCardPosition = currentViewPlayer.getImagesHand().get(currentViewPlayer.getImagesHand().size() - 1).getPosition();
        AudioManager.getInstance().setEffects(AudioManager.Effects.DRAW_CARD);
        movingAnimation = new MovingAnimation(deck.getPosition().getX(), deck.getPosition().getY(), lastCardPosition.getX(), lastCardPosition.getY(), drawnCard);

        animations.add(movingAnimation);
        return movingAnimation;
    }

    public Animation flipCardAnimation(ViewCard drawnCard)
    {
        if (animationRunning(flipAnimation)) return null;
        flipAnimation = new FlippingAnimation(drawnCard, deck.getPosition());
        animations.add(flipAnimation);
        AudioManager.getInstance().setEffects(AudioManager.Effects.FLIP);
        return flipAnimation;
    }

    public void shoutUnoAnimation(Player player)
    {
        if ((player instanceof HumanPlayer && player.hasSaidOne()) || (player instanceof AIPlayer && new Random().nextInt(1, 5) > 1))
        {
            player.shoutUno();
            AudioManager.getInstance().setEffects(AudioManager.Effects.UNO);
            animations.add(new TextAnimation(imagePath + "uno.gif", centerX, centerY));
        }
        else
            System.out.println("NON HA DETTO UNO");
    }

    public Animation exposedAnimation(Player player)
    {
        AudioManager.getInstance().setEffects(AudioManager.Effects.ERROR);
        var a = new TextAnimation(imagePath + "exposed.gif", centerX, centerY);
        animations.add(a);
        return a;
    }

    public boolean animationRunning(Animation anim){return anim != null && anim.isAlive();}

    private void drawHorizontalHand(ViewPlayer player, Graphics2D g2, int y)
    {
        int cardsSpace = Math.min(player.getPlayer().getHand().size() * ViewCard.width, maxCardsWidth);
        int startX = (getWidth() - cardsSpace) / 2;
        int cardsWidth = cardsSpace / player.getPlayer().getHand().size();

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

    private  void drawVerticalHand(ViewPlayer player, Graphics2D g2, int x)
    {
        int cardsSpace = Math.min(player.getPlayer().getHand().size() * ViewCard.width, maxCardsHeight);
        int startY = (getHeight() - cardsSpace) / 2;
        int cardsWidth = cardsSpace / player.getPlayer().getHand().size();

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

    public void endGame()
    {
        for (ViewPlayer p: viewPlayers)
            p.reset();
    }


    //controller usage

    public void resumeGame()
    {
        createCards();
        currentState = getState();
    }

    public void pauseGame()
    { currentState = State.GAME_PAUSED; }

    public void stopTimer()
    {
        animations.forEach(Animation::Stop);
        gameRunning = false;
    }


    public State getState()
    { return currentViewPlayer.getPlayer() instanceof HumanPlayer ? State.PLAYER_TURN : State.AI_TURN; }

    public State getCurrentState(){return currentState;}
    public void setCurrentState(State gameMode){currentState = gameMode;}
    //public Player getCurrentPlayer() {return currentViewPlayer.getPlayer();}
    public Player[] getPlayers() {return players;}
    public ViewCard getDeck() {return deck;}
    //public HashMap<Player, ArrayList<ViewAnimableCard>> getPlayerHands() {return playerHands;}
    public ViewPlayer[] getViewPlayers() { return viewPlayers; }
    public ViewPlayer getCurrentViewPlayer() { return currentViewPlayer; }
    public Rectangle getSkipTurnPosition() {return skipTurnPosition;}
    public Rectangle getUnoPosition() {return unoPosition;}
    public void cardNotPLayableEffects() { AudioManager.getInstance().setEffects(AudioManager.Effects.NOT_VALID); }
}

