package model;

import model.Cards.Card;

import java.util.Stack;

public class UnoGameTable {
    private Deck deck;
    //volendo si può evitare di fare lo stack per gli scarti
    //ma se il deck finisce prima della fine del game
    //basta riprendere lo stack degli scarti e assegnarlo al deck
    private Stack<Card> discards;
    private Player[] players;

    private int turno;

    public UnoGameTable(Player[] players){
        deck = new Deck();
        discards = new Stack<>();
        this.players = players;

        turno = 0;
    }

    public void startGame(){
        deck.shuffle();

        //sarebbe da scegliere chi inizia?

        //distribuzione carte
        for (int i = 0; i < 7; i++){
            for (Player p : players) {
                p.drawCard(deck.draw());
            }
        }

        //debug
        for (Player p : players) {
            System.out.println(p.name + " " + p.getHand());;
        }

        discards.push(deck.draw());
        //if discard discard.peek == draw -> il primo pesca due carte
        //if discard discard.peek == reverse -> il primo gioca e poi cambia giro
        //if discard discard.peek == skip -> il primo viene skippato
        //if discard discard.peek == wild -> il primo sceglie il colore
        //if discard discard.peek == wild_four -> carta rimessa nel deck e se ne prende un'altra
    }

    public void playTurn(){

    }
}
