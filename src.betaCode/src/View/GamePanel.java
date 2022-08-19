package View;

import Model.Cards.Card;
import Model.Player.AIPlayer;
import Model.Player.HumanPlayer;
import Model.Player.Player;
import Model.UnoGameTable;
import Utilities.Config;
import Utilities.Utils;
import View.Animations.Animation;
import View.Animations.FlipAnimation;
import View.Animations.PlayAnimation;
import View.Animations.RotatingAnimation;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GamePanel extends JPanel implements Observer {
    public enum State{
        PLAYER_TURN,
        OTHERS_TURN
    }

    private static final String imagePath = "resources/images/MainFrame/GamePanel/";
    private final Color green = new Color(14, 209, 69);
    private final Color darkGreen = new Color(230, 172, 0, 250);
    private final Font fontNames = new Font("Digital-7", Font.PLAIN, 25);

    private final int maxCardsWidth = 1350;
    private final int maxCardsHeight = 800;


    private final UnoGameTable model;
    //private final Timer repaintTimer;

    private int centerX;
    private int centerY;


    //private final Timer repaintTimer;


    private Player[] players;
    private HashMap<Player, ArrayList<CardImage>> playerHands;
    private CardImage deck;
    private CardImage discard;
    private State currentState;
    private boolean hasDrawed;

    private ArrayList<Animation> animations;
    private FlipAnimation flipAnimation;
    private PlayAnimation playAnimation;
    private RotatingAnimation rotatingAnimation;

    int ticksPerSecond;

    public GamePanel(UnoGameTable model){
        this.model = model;          //server per prendere dati
        setOpaque(true);
        setDoubleBuffered(true);
        //debug
        setBackground(Color.GREEN);
        InitializeComponents();

        //mouse listener per cliccare le carte
        //andra messo nel controller
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (currentState == State.PLAYER_TURN) {
                    int x = e.getX();
                    int y = e.getY();

                    if (model.getPLayableCards().size() == 0 && !hasDrawed && deck.isInMouse(x, y)) {
                        hasDrawed = true;
                        flipAnimation = new FlipAnimation(new CardImage(model.peekNextCard()), deck.getPosition());
                        animations.add(flipAnimation);
                        Thread thread = new Thread(() -> {
                            while (flipAnimation.isRunning()){}
                            model.drawCard();
                            if (players[0].getValidCards(discard.getCard()).size() == 0 && hasDrawed) model.passTurn();
                            });
                        thread.start();
                    }

                    var iterator = playerHands.get(players[0]).listIterator();
                    for (CardImage card : playerHands.get(players[0])) { //le carte dell'umano
                        if (!card.isInMouse(x, y)) continue;

                        if (model.getPLayableCards().contains(card.getCard())){
                            hasDrawed = false;
                            playCardAnimation(card);
                        }
                    }
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

        //metodo che continua ad repaintare la view
        //può essere cambiato
        /*
        repaintTimer = new Timer(5, new ActionListener() {
            private Instant lastTick;
            private int ticks = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lastTick == null) {
                    lastTick = Instant.now();
                }
                if (Duration.between(lastTick, Instant.now()).toMillis() >= 1000) {
                    ticksPerSecond = ticks;
                    lastTick = Instant.now();
                    ticks = 0;
                }
                //System.out.println(ticksPerSecond);
                ticks++;
                repaint();
            }
        });
        repaintTimer.start();
         */
        new Thread(() -> {
            while(true) {
                repaint();
            }
        }).start();

        centerX = 960;
        centerY = 540;

        rotatingAnimation = new RotatingAnimation(imagePath, centerX, centerY);
        animations.add(rotatingAnimation);
    }

    private void InitializeComponents(){
        deck = new CardImage();
        playerHands = new HashMap<>();
        animations = new ArrayList<>();
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
        if (Config.highGraphics) Utils.applyQualityRenderingHints(g2);

        if (players != null){
            drawHorizontalHand(players[0], g2, getHeight() - CardImage.height, getCard);
            drawHorizontalHand(players[2], g2, 0, getBackCard);
            drawVerticalHand(players[1], g2, getWidth() - CardImage.height, CardImage.backLeft);
            drawVerticalHand(players[3], g2, 0, CardImage.backRight);

            g2.drawImage(discard.getImage(), centerX + 25, centerY - CardImage.height / 2, CardImage.width, CardImage.height, null);
            discard.setPosition(centerX + 25, centerY - CardImage.height / 2, CardImage.width);

            centerX = getWidth() / 2;
            centerY = getHeight() / 2;

            if (model.getDeck().size() > 1) {
                int x = centerX - 25 - CardImage.width;
                int y = centerY - CardImage.height / 2;
                g2.drawString(String.valueOf(model.getDeck().size()), x, y - 10);
                g2.drawImage(deck.getBackCard(), x, y, CardImage.width, CardImage.height, null);
                deck.setPosition(x, y, CardImage.width);
            }

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

    Function<CardImage, BufferedImage> getCard = CardImage::getImage;
    Function<CardImage, BufferedImage> getBackCard = CardImage::getBackCard;

    private void drawHorizontalHand(Player player, Graphics2D g2, int y, Function<CardImage, BufferedImage> getCard){
        int cardsSpace = Math.min(player.getHand().size() * CardImage.width, maxCardsWidth);
        int startX = (getWidth() - cardsSpace) / 2;
        int cardsWidth = cardsSpace / player.getHand().size();

        int width = CardImage.width;
        int height = CardImage.height;
        drawNames(player.getName(), maxCardsWidth - 100, y == 0 ? height + 50 : y - 50, g2);
        if (y == 0){
            startX += width;
            y += height;
            width = -width;
            height = -height;
        }

        for (CardImage card : playerHands.get(player)){
            g2.drawImage(getCard.apply(card), startX, y + card.getOffsetY(), width, height, null);
            card.setPosition(startX, y, cardsWidth);
            startX += cardsWidth;
        }
    }

    private  void drawVerticalHand(Player player, Graphics2D g2, int x, BufferedImage image){
        int cardsSpace = Math.min(player.getHand().size() * CardImage.width, maxCardsHeight);
        int startY = (getHeight() - cardsSpace) / 2;
        int cardsWidth = cardsSpace / player.getHand().size();

        drawNames(player.getName(), x == 0 ? CardImage.height + 50 : x - 100, maxCardsHeight - 100, g2);

        for (CardImage card : playerHands.get(player)){
            g2.drawImage(image, x, startY, CardImage.height, CardImage.width, null);
            card.setPosition(x, startY, cardsWidth, true);
            startY += cardsWidth;
        }
    }

    private void drawNames(String name, int x, int y, Graphics g2){
        g2.setFont(fontNames);
        g2.setColor(darkGreen);
        int width = g2.getFontMetrics().stringWidth(name);
        int height = g2.getFontMetrics().getHeight();
        g2.fillRoundRect(x-5, y - height + 5, width + 10, height, 20, 20);
        g2.setColor(Color.black);
        g2.drawString(name, x, y);
    }

    @Override
    public void update(Observable o, Object arg) {
        UnoGameTable model = (UnoGameTable) o;
        this.players = model.getPlayers();
        //playerHands = new HashMap<>();
        createCards();

        currentState = model.currentPlayer() instanceof HumanPlayer ? State.PLAYER_TURN : State.OTHERS_TURN;
        System.out.println(currentState + " " + model.currentPlayer());

        rotatingAnimation.changeTurn(model.clockwiseTurn());

        if (currentState == State.OTHERS_TURN){
            asyncAITurn();
        }
    }

    public void createCards(){
        int[] rotations = new int[]{0, 270, 0, 90};
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

        var lastCard = model.getLastCard();
        discard = new CardImage(lastCard);
    }

    public void asyncAITurn(){
        new Thread(() -> {
            try {
                AIPlayer ai = (AIPlayer) model.currentPlayer();

                Thread.sleep(1500);
                if ((ai.getValidCards(discard.getCard()).size() == 0)) {
                    if (!hasDrawed) {
                        hasDrawed = true;
                        model.drawCard();
                    } else{
                        hasDrawed = false;
                        model.passTurn();
                    }
                }
                else{
                    hasDrawed = false;
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
        playAnimation = new PlayAnimation(card.getPosition().x, card.getPosition().y, discard.getPosition().x, discard.getPosition().y, card);
        animations.add(playAnimation);
        Thread thread = new Thread(() -> {
            while (playAnimation.isRunning()){}
            model.playCard(card.getCard());
        });
        thread.start();
    }

    //controller usage
    public void stopTimer(){
        //repaintTimer.stop();
    }
}

