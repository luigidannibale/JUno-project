package model;

import model.Cards.Card;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class UnoGameTable {
    private Deck deck;
    //volendo si può evitare di fare lo stack per gli scarti
    //ma se il deck finisce prima della fine del game
    //basta riprendere lo stack degli scarti e assegnarlo al deck
    private Stack<Card> discards;
    private Player[] players;
    
    private int turn;

    public UnoGameTable(Player[] players){
        deck = new Deck();
        discards = new Stack<>();
        this.players = players;

        turn = 0;
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

        discards.push(deck.draw());

        //debug
        System.out.println(discards.peek());
        for (Player p : players) {
            System.out.println(p.name + " " + p.getHand() + "\n-playable: " + p.getPlayableCards(discards.peek()));;
        }

        //if discard discard.peek == draw -> il primo pesca due carte
        //if discard discard.peek == reverse -> il primo gioca e poi cambia giro
        //if discard discard.peek == skip -> il primo viene skippato
        //if discard discard.peek == wild -> il primo sceglie il colore
        //if discard discard.peek == wild_four -> carta rimessa nel deck e se ne prende un'altra
    }

    public void playTurn(){
        List<Card> playableCards = players[turn].getPlayableCards(discards.peek());

        if (playableCards.size() == 0) {
            Card drawed = deck.draw();
            if (!drawed.isPlayable(discards.peek())) skipTurn();
        }
    }

    public void reverse(){
        Collections.reverse(Arrays.asList(players));
        turn = Math.abs(turn - players.length);     // |indice - num giocatori|
    }

    public void skipTurn(){
        turn ++;
    }
}
