package View;

import Model.Player.Player;
import Model.UnoGame;
import Utilities.Utils;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GamePanel extends JPanel implements Observer {

    private static final String imagePath = "resources/images/MainFrame/GamePanel/";

    private final int maxCardsWidth = 1350;
    private final int maxCardsHeight = 800;

    private UnoGame model;

    private Player[] players;
    private HashMap<Player, ArrayList<CardImage>> playerHands;
    private CardImage deck;

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
                System.out.println(x + ":" + y);

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
                    repaint();
                }
            }
        });
        InitializeComponents();
    }

    private void InitializeComponents(){
        deck = new CardImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color verde = new Color(14, 209, 69);
        g.setColor(verde);
        g.fillRect(0,0, getWidth(), getHeight());

        Graphics2D g2 = (Graphics2D) g;
        Utils.applyQualityRenderingHints(g2);

        if (players != null){
            drawHorizontalHand(players[0], g2, getHeight() - CardImage.height, false);
            drawHorizontalHand(players[2], g2, 0, true);
            drawVerticalHand(players[1], g2, getWidth() - CardImage.height, true);
            drawVerticalHand(players[3], g2, 0, true);

            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;

            var discard = model.getLastCard();
            g2.drawImage(new CardImage(discard.getColor(), discard.getValue()).getImage(), centerX + 25, centerY - CardImage.height / 2, CardImage.width, CardImage.height, null);

            if (model.getDeck().size() > 1) {
                int x = centerX - 25 - CardImage.width;
                int y = centerY - CardImage.height / 2;
                g2.drawImage(deck.getBackCard(), x, y, CardImage.width, CardImage.height, null);
                deck.setPosition(x, y, CardImage.width);
            }

            g2.setColor(Color.black);
            g2.drawLine(centerX - 5, centerY, centerX + 5, centerY);
        }

        g2.dispose();
    }

    //trying to generalize
    public void drawHand(Player player, int maxSpace, int fullSpace, Graphics2D g2, int y){
        int cardsSpace = Math.min(player.getHand().size() * CardImage.width, maxSpace);
        int startX = (fullSpace - cardsSpace) / 2;
        int cardsWidth = cardsSpace / player.getHand().size();
    }

    Function<CardImage, Image> getCard = CardImage::getImage;
    Function<CardImage, Image> getBackCard = CardImage::getBackCard;

    private void drawHorizontalHand(Player player, Graphics2D g2, int y, boolean covered){
        int cardsSpace = Math.min(player.getHand().size() * CardImage.width, maxCardsWidth);
        int startX = (getWidth() - cardsSpace) / 2;
        int cardsWidth = cardsSpace / player.getHand().size();

        Function<CardImage, Image> drawCard = covered ? getBackCard : getCard;

        int width = CardImage.width;
        int height = CardImage.height;
        if (y == 0){
            startX += width;
            y += height;
            width = -width;
            height = -height;
        }

        for (CardImage card : playerHands.get(player)){
            g2.drawImage(drawCard.apply(card), startX, y + card.getOffsetY(), width, height, null);
            card.setPosition(startX, y, cardsWidth);
            startX += cardsWidth;
        }

        drawNames(player.getName(), startX, y == 0 ? height + 30 : y - 30, g2);
    }

    private  void drawVerticalHand(Player player, Graphics2D g2, int x, boolean covered){
        int cardsSpace = Math.min(player.getHand().size() * CardImage.width, maxCardsHeight);
        int startY = (getHeight() - cardsSpace) / 2;
        int cardsWidth = cardsSpace / player.getHand().size();

        drawNames(player.getName(), x == 0 ? CardImage.width + 30 : x - 30, startY, g2);
        Function<CardImage, Image> drawCard = covered ? getBackCard : getCard;
        int rotationRequired = x == 0 ? 90 : 270;

        for (CardImage card : playerHands.get(player)){
            g2.drawImage(Utils.rotateImage(drawCard.apply(card), rotationRequired), x, startY, CardImage.height, CardImage.width, null);
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
        Arrays.stream(players).filter(p -> p.getHand().size() > 0).forEach(p -> {
            ArrayList<CardImage> hand = p.getHand().stream().map(c -> new CardImage(c.getColor(), c.getValue())).collect(Collectors.toCollection(ArrayList::new));
            playerHands.put(p, hand);
        });
        repaint();
    }
}

