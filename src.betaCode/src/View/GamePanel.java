package View;

import Model.Cards.Card;
import Model.Player.AIPlayer;
import Model.Player.HumanPlayer;
import Model.Player.Player;
import Model.UnoGameTable;
import Utilities.ConfigDeprecated;
import Utilities.Config;
import Utilities.Utils;
import View.Animations.Animation;
import View.Animations.FlipAnimation;
import View.Animations.PlayAnimation;
import View.Animations.RotatingAnimation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.stream.Collectors;

public class GamePanel extends JPanel implements Observer {
    public enum State{
        PLAYER_TURN,
        OTHERS_TURN,
        PAUSED
    }

    private static final String imagePath = "resources/images/MainFrame/GamePanel/";
    private final Color green = new Color(14, 209, 69);
    private final Color playerBackground = new Color(101, 132, 247, 160);
    private final Color currentPlayerBackground = new Color(255, 209, 26, 160);
    private final Font fontNames = new Font("Digital-7", Font.PLAIN, 25);

    private final int maxCardsWidth;
    private final int maxCardsHeight;


    private final UnoGameTable model;
    //private final Timer repaintTimer;
    Thread gameThread;
    boolean gameRunning = true;

    private int centerX;
    private int centerY;


    private Player[] players;
    private HashMap<Player, ArrayList<CardImage>> playerHands;
    private Player currentPlayer;                   //da sistemare?
    private CardImage deck;
    private CardImage discard;
    private State currentState;

    private ArrayList<Animation> animations;
    private FlipAnimation flipAnimation;
    private PlayAnimation playAnimation;
    private RotatingAnimation rotatingAnimation;

    private Rectangle skipTurnPosition;
    private Rectangle unoPosition;

    int ticksPerSecond;

    public GamePanel(UnoGameTable model)
    {
        this.model = model;          //server per prendere dati
        setOpaque(true);
        setDoubleBuffered(true);
        //debug
        setBackground(Color.GREEN);
        InitializeComponents();
        CardImage.height = (int) (180 * Config.scalingPercentage);
        CardImage.width = (int) (120 * Config.scalingPercentage);

        //mouse listener per cliccare le carte
        //andra messo nel controller
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (currentState == State.PLAYER_TURN) {
                    int x = e.getX();
                    int y = e.getY();

                    if (!currentPlayer.HasDrew() && deck.isInMouse(x, y)) {
                        currentPlayer.setHasDrew(true);

                        CardImage drawnCard = new CardImage(model.peekNextCard());
                        flipAnimation = new FlipAnimation(drawnCard, deck.getPosition());
                        animations.add(flipAnimation);
                        Thread thread = new Thread(() -> {
                            while (flipAnimation.isRunning()){}
                            drawCardAnimation(drawnCard);
                            //if (currentPlayer.getValidCards(discard.getCard()).size() == 0 && currentPlayer.HasDrew()) model.passTurn();
                            });
                        thread.start();
                    }

                    //var iterator = playerHands.get(players[0]).listIterator();
                    for (CardImage card : playerHands.get(currentPlayer)) { //le carte dell'umano
                        if (!card.isInMouse(x, y)) continue;

                        if (model.getPLayableCards().contains(card.getCard())){
                            playCardAnimation(card);
                        }
                    }

                    if (currentPlayer.HasDrew() && skipTurnPosition.contains(x , y)) model.passTurn();

                    if (currentPlayer.HasOne() && unoPosition.contains(x, y)) currentPlayer.setSaidOne(true);
                }
            }
        });

        //mouse motion listener per alzare le carte con hovering
        //va messo nel controller
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                for (CardImage card : playerHands.get(players[0])){ //le carte dell'umano
                    if (card.isInMouse(x, y)) card.setOffsetY(-30);
                    else card.setOffsetY(0);
                    //repaint();
                }
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    currentState = State.PAUSED;
                }
                super.keyPressed(e);
            }
        });

        var screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        centerX = screenSize.width / 2;
        centerY = screenSize.height / 2;

        maxCardsHeight = screenSize.height * 74 / 100;
        maxCardsWidth = screenSize.width * 70 / 100;

        rotatingAnimation = new RotatingAnimation(imagePath, centerX, centerY);
        animations.add(rotatingAnimation);


        //metodo che continua ad repaintare la view
        //può essere cambiato
        //repaintTimer = new Timer(1, e -> repaint());
        //repaintTimer.start();

        gameThread = new Thread(() -> {
            while(gameRunning) {
                repaint();
            }
        });
        gameThread.setName("GAME");  //debug
        gameThread.start();
    }

    private void InitializeComponents(){
        deck = new CardImage();
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
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        long start = System.nanoTime();

        g.setColor(green);
        g.fillRect(0,0, getWidth(), getHeight());

        Graphics2D g2 = (Graphics2D) g;
        if (Config.graphicQuality == GraphicQuality.HIGH) Utils.applyQualityRenderingHints(g2);

        if (players != null){
            drawHorizontalHand(players[0], g2, getHeight() - CardImage.height);
            drawHorizontalHand(players[2], g2, 0);
            drawVerticalHand(players[1], g2, getWidth() - CardImage.height);
            drawVerticalHand(players[3], g2, 0);

            g2.drawImage(discard.getCardImage(), centerX + 25, centerY - CardImage.height / 2, CardImage.width, CardImage.height, null);
            discard.setPosition(centerX + 25, centerY - CardImage.height / 2, CardImage.width);

            //centerX = getWidth() / 2;
            //centerY = getHeight() / 2;

            if (model.getDeck().size() > 1) {
                int x = centerX - 25 - CardImage.width;
                int y = centerY - CardImage.height / 2;
                g2.drawString(String.valueOf(model.getDeck().size()), x, y - 10);
                g2.drawImage(deck.getCardImage(), x, y, CardImage.width, CardImage.height, null);
                deck.setPosition(x, y, CardImage.width);
            }

            //rotatingAnimation.paint(g2);

            Iterator<Animation> iter = animations.iterator();
            while(iter.hasNext()){
                Animation animation = iter.next();
                if (animation.isRunning()) animation.paint(g2);
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
    public void drawHand(Player player, int maxSpace, int fullSpace, Graphics2D g2, int y){
        int cardsSpace = Math.min(player.getHand().size() * CardImage.width, maxSpace);
        int startX = (fullSpace - cardsSpace) / 2;
        int cardsWidth = cardsSpace / player.getHand().size();
    }

    private void drawHorizontalHand(Player player, Graphics2D g2, int y){
        int cardsSpace = Math.min(player.getHand().size() * CardImage.width, maxCardsWidth);
        int startX = (getWidth() - cardsSpace) / 2;
        int cardsWidth = cardsSpace / player.getHand().size();

        int width = CardImage.width;
        int height = CardImage.height;
        drawNames(player, maxCardsWidth - 100, y == 0 ? height + 50 : y - 50, g2);
        if (y == 0){
            startX += width;
            y += height;
            width = -width;
            height = -height;
        }

        for (CardImage card : playerHands.get(player)){
            g2.drawImage(card.getDrawImage(), startX, y + card.getOffsetY(), width, height, null);
            card.setPosition(startX, y, cardsWidth);
            startX += cardsWidth;
        }
    }

    private  void drawVerticalHand(Player player, Graphics2D g2, int x){
        int cardsSpace = Math.min(player.getHand().size() * CardImage.width, maxCardsHeight);
        int startY = (getHeight() - cardsSpace) / 2;
        int cardsWidth = cardsSpace / player.getHand().size();

        drawNames(player, x == 0 ? CardImage.height + 50 : x - 100, maxCardsHeight - 100, g2);

        for (CardImage card : playerHands.get(player)){
            g2.drawImage(card.getDrawImage(), x, startY, CardImage.height, CardImage.width, null);
            card.setPosition(x, startY, cardsWidth, true);
            startY += cardsWidth;
        }
    }

    private void drawNames(Player player, int x, int y, Graphics g2){
        g2.setFont(fontNames);
        g2.setColor(player.equals(currentPlayer) ? currentPlayerBackground : playerBackground);
        int width = g2.getFontMetrics().stringWidth(player.getName());
        int height = g2.getFontMetrics().getHeight();
        g2.fillRoundRect(x-5, y - height + 5, width + 10, height, 20, 20);
        g2.setColor(Color.black);
        g2.drawString(player.getName(), x, y);

        if (player instanceof HumanPlayer){
            y += height;
            if (player.HasOne() && !player.HasSaidOne()){
                g2.drawString("UNO!", x, y);
                width = g2.getFontMetrics().stringWidth("UNO!");
                unoPosition.setRect(x, y - height, width, height);
            }
            if (player.HasDrew()){
                x += width + 20;
                g2.drawString("Skip turn", x, y);
                width = g2.getFontMetrics().stringWidth("Skip Turn");
                skipTurnPosition.setRect(x, y - height, width, height);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        //potremmo passare un infomazione come arg per dire quale animazione startare nel caso di pesca carte ai
        UnoGameTable model = (UnoGameTable) o;
        this.players = model.getPlayers();
        //playerHands = new HashMap<>();
        createCards();
        currentPlayer = model.currentPlayer();

        currentState = currentPlayer instanceof HumanPlayer ? State.PLAYER_TURN : State.OTHERS_TURN;
        System.out.println(currentState + " " + currentPlayer);

        if (rotatingAnimation.isRunning())
        {
            rotatingAnimation.changeTurn(model.clockwiseTurn());
            rotatingAnimation.imageColor(discard.getCard().getColor());
        }

        if (currentState == State.OTHERS_TURN){
            asyncAITurn();
        }

        /* debug
        Set<Thread> threads = Thread.getAllStackTraces().keySet();
        System.out.printf("%-15s \t %-15s \t %-15s \t %s\n", "Name", "State", "Priority", "isDaemon");
        for (Thread t : threads) {
            System.out.printf("%-15s \t %-15s \t %-15d \t %s\n", t.getName(), t.getState(), t.getPriority(), t.isDaemon());
        }
         */
    }

    public void createCards(){
        int[] rotations = new int[]{0, 270, 180, 90};
        int i = 0;
        /*
        Arrays.stream(players).filter(p -> p.getHand().size() > 0).forEach(p -> {
            ArrayList<CardImage> hand = p.getHand().stream().map(c -> new CardImage(c.getColor(), c.getValue())).collect(Collectors.toCollection(ArrayList::new));
            playerHands.put(p, hand);
        });

         */
        for (Player player : players){
            if (player.getHand().size() > 0){
                int finalI = i;
                playerHands.put(player, player.getHand().stream().map(c -> new CardImage(c, rotations[finalI])).collect(Collectors.toCollection(ArrayList::new)));
            }
            i += 1;
        }

        discard = new CardImage(model.getLastCard());
    }

    public void asyncAITurn(){
        new Thread(() -> {
            try {
                AIPlayer ai = (AIPlayer) currentPlayer;

                Thread.sleep(1500);
                if ((ai.getValidCards(discard.getCard()).size() == 0)) {
                    if (!ai.HasDrew()) drawCardAnimation(new CardImage());
                    else model.passTurn();
                }
                else{
                    Card playedCard = ai.getValidCards(discard.getCard()).get(0);
                    CardImage relatedImage = playerHands.get(ai).stream().filter(ci -> ci.getCard().equals(playedCard)).toList().get(0);
                    playCardAnimation(relatedImage);
                    //model.playCard(ai.getValidCards(discard.getCard()).get(0));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void playCardAnimation(CardImage card){
        if (playAnimation != null && playAnimation.isRunning()) return;
        Card played = card.getCard();
        playAnimation = new PlayAnimation(card.getPosition().getX(), card.getPosition().getY(), discard.getPosition().getX(), discard.getPosition().getY(), card);
        animations.add(playAnimation);
        card.setDrawImage(null);
        Thread thread = new Thread(() -> {
            while (playAnimation.isRunning()){}
            model.playCard(played);
        });
        thread.start();
    }

    private void drawCardAnimation(CardImage drawnCard){
        Rectangle lastCardPosition = playerHands.get(currentPlayer).get(playerHands.get(currentPlayer).size() - 1).getPosition();
        playAnimation = new PlayAnimation(deck.getPosition().getX(), deck.getPosition().getY(), lastCardPosition.getX(), lastCardPosition.getY(), drawnCard);
        animations.add(playAnimation);
        while (playAnimation.isRunning()){}
        model.drawCard();
    }

    //controller usage
    public void stopTimer(){
        //repaintTimer.stop();
        animations.forEach(Animation::stop);
        rotatingAnimation.stop();
        gameRunning = false;
    }
}

