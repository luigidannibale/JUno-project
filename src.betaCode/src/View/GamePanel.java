package View;

import Controller.MainFrameController;
import Model.Player.Player;
import Model.UnoGame;
import Utilities.Utils;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GamePanel extends JPanel implements Observer {

    private static final String imagePath = "resources/images/MainFrame/GamePanel/";
    private final Color verde = new Color(14, 209, 69);

    private final int maxCardsWidth = 1350;
    private final int maxCardsHeight = 800;

    private UnoGame model;

    private Player[] players;
    private HashMap<Player, ArrayList<CardImage>> playerHands;
    private CardImage deck;
    private CardImage discard;

    int ticksPerSecond;

    public GamePanel(UnoGame model){
        this.model = model;          //server per prendere dati
        setOpaque(true);
        setDoubleBuffered(true);
        //debug
        setBackground(Color.GREEN);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                if (deck.isInMouse(x, y)){
                    model.drawCard();
                }

                for (CardImage card : playerHands.get(players[0])){ //le carte dell'umano
                    if (card.isInMouse(x, y)) System.out.println(card);
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                for (CardImage card : playerHands.get(players[0])){ //le carte dell'umano
                    if (card.isInMouse(x, y)) card.setOffsetY(-20);
                    else card.setOffsetY(0);
                    //repaint();
                }
            }
        });



        Timer timer = new Timer(5, new ActionListener() {
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
        timer.start();

        InitializeComponents();
    }

    private void InitializeComponents(){
        deck = new CardImage();
    }

    int count = 0;
    double media;
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        long start = System.nanoTime();

        g.setColor(verde);
        g.fillRect(0,0, getWidth(), getHeight());

        Graphics2D g2 = (Graphics2D) g;
        Utils.applyQualityRenderingHints(g2);

        if (players != null){
            drawHorizontalHand(players[0], g2, getHeight() - CardImage.height, getCard);
            drawHorizontalHand(players[2], g2, 0, getBackCard);
            drawVerticalHand(players[1], g2, getWidth() - CardImage.height);
            drawVerticalHand(players[3], g2, 0);

            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;

            g2.drawImage(discard.getImage(), centerX + 25, centerY - CardImage.height / 2, CardImage.width, CardImage.height, null);

            if (model.getDeck().size() > 1) {
                int x = centerX - 25 - CardImage.width;
                int y = centerY - CardImage.height / 2;
                g2.drawString(String.valueOf(model.getDeck().size()), x, y - 10);
                g2.drawImage(deck.getBackCard(), x, y, CardImage.width, CardImage.height, null);
                deck.setPosition(x, y, CardImage.width);
            }
        }
        g2.dispose();

        long end = System.nanoTime();
        media += (end - start) / 1000000000.0;
        count += 1;
        if (count == 10){
            media /= 10;
            System.out.println("Draw time: " + media + "sec");
            count = 0;
        }

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

    private  void drawVerticalHand(Player player, Graphics2D g2, int x){
        int cardsSpace = Math.min(player.getHand().size() * CardImage.width, maxCardsHeight);
        int startY = (getHeight() - cardsSpace) / 2;
        int cardsWidth = cardsSpace / player.getHand().size();

        drawNames(player.getName(), x == 0 ? CardImage.height + 50 : x - 100, maxCardsHeight - 100, g2);

        for (CardImage card : playerHands.get(player)){
            g2.drawImage(card.getImage(), x, startY, CardImage.height, CardImage.width, null);
            card.setPosition(x, startY, cardsWidth, true);
            startY += cardsWidth;
        }
    }

    private void drawNames(String name, int x, int y, Graphics g2){
        g2.setFont(new Font("Digital-7", Font.PLAIN, 25));
        g2.setColor(Color.black);
        g2.drawString(name, x, y);
    }

    @Override
    public void update(Observable o, Object arg) {
        UnoGame model = (UnoGame) o;
        this.players = model.getPlayers();
        playerHands = new HashMap<>();
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
                playerHands.put(player, player.getHand().stream().map(c -> new CardImage(c.getColor(), c.getValue(), rotations[finalI], DeckColor.WHITE.VALUE+"/")).collect(Collectors.toCollection(ArrayList::new)));
            }
            i += 1;
        }
        var lastCard = model.getLastCard();
        discard = new CardImage(lastCard.getColor(), lastCard.getValue());
        //repaint();
    }
}

