package model;

import model.Cards.Card;
import model.Enumerations.CardColor;
import model.Enumerations.CardValue;
import model.Player.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.IntStream;

public class UnoGameTable {
    private static class TurnManager{
        static int player = 0;
        static Card card;

        static public void skipTurn(CardValue v, CardColor c){
            player++;
            if (player > 4) player = player - 5;
            card = new Card(c,v);
        }
        static public void skipTurn(Card c){
            player++;
            if (player > 4) player = player - 5;
            card = c;
        }
        static public void skipTurn(){
            player++;
            if (player > 4) player = player - 5;
        }
    }
    private Deck deck;
    private Stack<Card> discards;
    private Player[] players;


    public UnoGameTable(Player[] players)
    {
        deck = new Deck();
        discards = new Stack<>();
        this.players = players;
    }

    public void startGame()
    {
        deck.shuffle();

        //sarebbe da scegliere chi inizia?

        //distribuzione carte
        IntStream.range(0, 7).forEach(i -> Arrays.stream(players).forEach(p -> p.drawCard(deck.draw())));

        //if discard discard.peek == draw -> il primo pesca due carte
        //if discard discard.peek == reverse -> il primo gioca e poi cambia giro
        //if discard discard.peek == skip -> il primo viene skippato
        //if discard discard.peek == wild -> il primo sceglie il colore
        //if discard discard.peek == wild_four -> carta rimessa nel deck e se ne prende un'altra
        discards.push(deck.draw());
        TurnManager.card = discards.peek();

        //debug
        System.out.println(discards.peek());
        for (Player p : players) {
            System.out.println(p.getName() + " " + p.getHand());// + "\n-playable: " +
            p.getPlayableCards(discards.peek());
            System.out.println();
        }


    }

    public void playTurn(){
        List<Card> playableCards = players[TurnManager.player].getPlayableCards(TurnManager.card);
        if (playableCards.size() == 0) {
            Card drawed = deck.draw();
            if (!drawed.isPlayable(discards.peek())) TurnManager.skipTurn();
        }
    }

    public void reverse(){
        Collections.reverse(Arrays.asList(players));
        TurnManager.player = Math.abs(TurnManager.player - players.length);
    }


}
