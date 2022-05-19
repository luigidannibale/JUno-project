import Controller.UnoGameTable;
import Model.Cards.Card;
import Model.Deck;
import Model.Enumerations.CardValue;
import Model.Player.AIPlayer;
import Model.Player.HumanPlayer;
import Model.Player.Player;
import Model.UnoBasicGame;

import java.util.Random;

public class Juno {
    public static void main(String[] args){
        UnoGameTable table;

        //provaDeck();

        Player gigi = new HumanPlayer("gigione");
        Player d = new AIPlayer("danielone");
        Player w = new AIPlayer("waluigi");
        Player wa = new AIPlayer("wario");

        //UnoGame uno = new tipoDiUno(new Player[] {mario,filippo,marco,gianfranco});
        //UnoGameTable uno = new UnoGameTable(new UnoBasicGame(new Player[] {gigi, d, w, wa}));
        //uno.startGame();

        table = new UnoGameTable(new UnoBasicGame(new Player[]{gigi,d,w,wa}));

    }


    public static void provaDeck(){
        Deck deck = new Deck();
        System.out.print("I'm shuffling deck");
        for(int casualNumberOfTimes = 1; casualNumberOfTimes<(new Random()).nextInt(4,7); casualNumberOfTimes++)
        {
            System.out.print(".");
            deck.shuffle();
        }
        System.out.println();
        int i = 0;
        int wild = 0;
        int drawWild = 0;
        int draw = 0;
        int reverse = 0;
        int skip = 0;
        int normal = 0;
        for (Card c : deck.getDeck()){

            System.out.print(i + " " + c + " ");
            i++;

            System.out.println();
            if (c.getValue() == CardValue.WILD) wild ++;
            if (c.getValue() == CardValue.WILD_DRAW) drawWild ++;
            if (c.getValue() == CardValue.DRAW) draw ++;
            if (c.getValue() == CardValue.SKIP) skip ++;
            if (c.getValue() == CardValue.REVERSE) reverse ++;
            if (c.getClass() == Card.class) normal ++;
        }

        System.out.println("wild:"+wild);
        System.out.println("draw wild:"+drawWild);
        System.out.println("draw:"+draw);
        System.out.println("skip:"+skip);
        System.out.println("reverse:"+reverse);
        System.out.println("normal:"+normal);
    }
}
