package View.Pages;

import Model.Cards.Card;
import Model.Cards.CardColor;
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
import View.Elements.CardImage;
import View.Elements.GraphicQuality;

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
    private final Font fontNames;

    private int maxCardsWidth = 1350;
    private int maxCardsHeight = 810;

    Thread aiThread;
    Thread gameThread;
    boolean gameRunning = true;

    private final int centerX;
    private final int centerY;

    private Player[] players;
    private HashMap<Player, ArrayList<CardImage>> playerHands;
    private Player currentPlayer;                   //da sistemare?
    private Card lastCard;
    private CardImage deck;
    private int deckSize;
    private CardImage discard;
    private State currentState;

    private PlayAnimation playAnimation;
    private ArrayList<Animation> animations;
    private FlipAnimation flipAnimation;
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
        CardImage.height = (int) (180 * Config.scalingPercentage);
        CardImage.width = (int) (120 * Config.scalingPercentage);
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
        int x = e.getX(),
            y = e.getY();
        for (CardImage card : playerHands.get(players[0])) card.setOffsetY(card.isInMouse(x, y) ? -30 : 0);
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

    public void pauseGame()
    {
        currentState = State.PAUSED;
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

    private void InitializeComponents()
    {
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

        if (players != null)
        {
            drawHorizontalHand(players[0], g2, getHeight() - CardImage.height);
            drawHorizontalHand(players[2], g2, 0);
            drawVerticalHand(players[1], g2, getWidth() - CardImage.height);
            drawVerticalHand(players[3], g2, 0);

            g2.drawImage(discard.getCardImage(), centerX + 25, centerY - CardImage.height / 2, CardImage.width, CardImage.height, null);
            discard.setPosition(centerX + 25, centerY - CardImage.height / 2, CardImage.width);

            //centerX = getWidth() / 2;
            //centerY = getHeight() / 2;

            if (deckSize > 1) {
                int x = centerX - 25 - CardImage.width;
                int y = centerY - CardImage.height / 2;
                g2.drawString(String.valueOf(deckSize), x, y - 10);
                g2.drawImage(deck.getCardImage(), x, y, CardImage.width, CardImage.height, null);
                deck.setPosition(x, y, CardImage.width);
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
        UnoGameTable model = (UnoGameTable) o;
        players = model.getPlayers();
        lastCard = model.getLastCard();
        System.out.println("LAST CARD " + lastCard);
        deckSize = model.getDeck().size();
        createCards();
        currentPlayer = model.currentPlayer();


        if (currentPlayer.isIncepped()) {
            currentPlayer.setIncepped(false);
            model.passTurn();
        }

        currentState = currentPlayer instanceof HumanPlayer ? State.PLAYER_TURN : State.OTHERS_TURN;

        if (rotatingAnimation.isAlive())
        {
            rotatingAnimation.changeTurn(model.clockwiseTurn());
            rotatingAnimation.imageColor(discard.getCard().getColor());
        }

        if (currentState == State.OTHERS_TURN || (aiThread != null && !aiThread.isAlive()))
            asyncAITurn(model);
    }

    public void createCards()
    {
        int[] rotations = new int[]{0, 270, 180, 90};
        for (Player player : players)
        {
            assert (player.getHand().size() > 0):"Gamepanel->createcards size <= 0";
            int rotation = Arrays.stream(players).toList().indexOf(player);
            playerHands.put(player, player.getHand().stream().map(c -> new CardImage(c, rotations[rotation])).collect(Collectors.toCollection(ArrayList::new)));
        }
        discard = new CardImage(lastCard);
    }

    public void asyncAITurn(UnoGameTable model)
    {
        aiThread = new Thread(() -> {
            try {
                AIPlayer ai = (AIPlayer) currentPlayer;

                Thread.sleep(1500);
                if ((ai.getValidCards(discard.getCard()).size() == 0))
                {
                    if (!ai.hasDrew()){
                        drawCardAnimation(new CardImage()).Join();
                        model.drawCard();
                    }
                    else model.passTurn();
                }
                else
                {
                    Card playedCard = ai.getValidCards(discard.getCard()).get(0);
                    CardImage relatedImage = playerHands.get(ai).stream().filter(ci -> ci.getCard().equals(playedCard)).toList().get(0);
                    playCardAnimation(relatedImage).Join();
                    model.playCard(ai.getValidCards(discard.getCard()).get(0));
                    model.cardActionPerformance(model.getOptions().build());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw e;
            }
        });
        aiThread.start();
    }

    public Animation playCardAnimation(CardImage card)
    {
        if (animationRunning(playAnimation)) return null;
        playAnimation = new PlayAnimation(card.getPosition().getX(), card.getPosition().getY(), discard.getPosition().getX(), discard.getPosition().getY(), card);
        animations.add(playAnimation);
        card.setDrawImage(null);
        return playAnimation;
    }
    public Animation drawCardAnimation(CardImage drawnCard){
        if (animationRunning(playAnimation)) return null;
        Rectangle lastCardPosition = playerHands.get(currentPlayer).get(playerHands.get(currentPlayer).size() - 1).getPosition();
        playAnimation = new PlayAnimation(deck.getPosition().getX(), deck.getPosition().getY(), lastCardPosition.getX(), lastCardPosition.getY(), drawnCard);
        animations.add(playAnimation);
        return playAnimation;
    }

    public Animation flipCardAnimation(CardImage drawnCard){
        if (animationRunning(flipAnimation)) return null;
        flipAnimation = new FlipAnimation(drawnCard, deck.getPosition());
        animations.add(flipAnimation);
        return flipAnimation;
    }

    public boolean animationRunning(Animation anim){return anim != null && anim.isAlive();}

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
            if (player.hasOne() && !player.hasSaidOne()){
                g2.drawString("UNO!", x, y);
                width = g2.getFontMetrics().stringWidth("UNO!");
                unoPosition.setRect(x, y - height, width, height);
            }
            if (player.hasDrew()){
                x += width + 20;
                g2.drawString("Skip turn", x, y);
                width = g2.getFontMetrics().stringWidth("Skip Turn");
                skipTurnPosition.setRect(x, y - height, width, height);
            }
        }
    }

    //controller usage
    public void stopTimer(){
        animations.forEach(Animation::Stop);
        gameRunning = false;
    }


    // GETTER
    public State getCurrentState(){return currentState;}
    public Player getCurrentPlayer() {return currentPlayer;}
    public CardImage getDeck() {return deck;}
    public HashMap<Player, ArrayList<CardImage>> getPlayerHands() {return playerHands;}
    public Rectangle getSkipTurnPosition() {return skipTurnPosition;}
    public Rectangle getUnoPosition() {return unoPosition;}
}

