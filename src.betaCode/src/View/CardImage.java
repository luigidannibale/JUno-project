package View;

import Model.Cards.Enumerations.CardColor;
import Model.Cards.Enumerations.CardValue;
import Utilities.Utils;

import javax.swing.*;
import java.awt.*;

public class CardImage extends JComponent {
    public static final Image backCard = Utils.getImage("resources/images/Back_card.png");
    private static final String pathDeck = "resources/images/White_deck/";
    private final Image img;

    private boolean covered = false;

    public static final int width = 120;
    public static final int height = 180;

    //o ruotiamo tutte le immagini o ruotiamo l'intero panel
    //che non sarebbe male
    private int rotation;

    private int x;
    private int y;
    private Rectangle position;

    private CardColor color;
    private CardValue value;

    /*
    public CardImage(){
        img = backCard;
        setPreferredSize(new Dimension(width, height));
        setSize(new Dimension(width, height));
    }

     */

    public CardImage(){
        img = null;
        //setPreferredSize(new Dimension(width, height));
    }

    public CardImage(CardColor color, CardValue value) {
        this.color = color;
        this.value = value;
        int num = 0;
        if (color != CardColor.WILD) num = color.VALUE * 14 + value.ordinal() + 1;
        else num = value == CardValue.WILD ? 14 : 14 * 5;
        String numero = String.format("%02d", num) + ".png";
        //System.out.println(pathDeck + numero);

        img = Utils.getImage(pathDeck + numero);
        //width = img.getWidth(null) * 50 / 100;
        //height = img.getHeight(null) * 50 / 100;

        /*
        setPreferredSize(new Dimension(width, height));
        setSize(new Dimension(width, height));
        addMouseListener(new MouseAdapter() {
            //usato mouseReleased perchè a volte il clicked non prendeva
            @Override
            public void mouseReleased(MouseEvent e) {
                covered = !covered;
                //repaint();
            }
        });
         */
    }

    public Image getImage() {
        return img;
    }

    public Image getBackCard(){return backCard;}

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void setPosition(int x, int y){
        setPosition(x, y, false);
    }

    public void setPosition(int x, int y, boolean rotated){
        this.x = x;
        this.y = y;
        position = rotated ? new Rectangle(x, y, height, width) : new Rectangle(x, y, width, height);
    }

    public boolean hasBeenClicked(int x, int y){
        return position.contains(x, y);
    }

    @Override
    public String toString() {
        return value + " " + color;
    }

    /*
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            Utils.applyQualityRenderingHints(g2);
            g2.drawImage(covered ? img : backCard, 0, 0, width, height, null);
        }

         */
}
