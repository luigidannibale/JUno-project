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
            player = next();
            card = new Card(c,v);
        }
        static public void skipTurn(Card c){
            player++;
            player = next();
            card = c;
        }
        static public void skipTurn(){
            player++;
            player = next();
        }
        static public int next(){
            return player == 4 ? 0 : player;
        }
    }
    private Deck deck;
    private Stack<Card> discards;
    private Player[] players;

    boolean win = false;

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
        //debug
        deck.deck.removeIf(c -> c.getColor() == CardColor.WILD);

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
            System.out.println(p.getName() + " " + p.getHand() + "\n-playable: " + p.getPlayableCards(discards.peek()));
            System.out.println();
        }

        while (!win){
            var player = players[TurnManager.player];
            var lastCard = TurnManager.card;
            System.out.println("Deck: " + deck.size() + " Discards: " + discards.size());
            System.out.println("Player: " + player.getName());
            System.out.println("lastCard: " + lastCard);

            System.out.println("Hand: " + player.getHand());
            var playable = player.getPlayableCards(lastCard);
            System.out.println("Playable: " + playable);
            if (playable.size() == 0){
                var drawed = deck.draw();
                System.out.println("Drawed: " + drawed);
                if (drawed.isPlayable(lastCard))
                {
                    discards.push(drawed);
                    System.out.println("Played: " + drawed);
                }
                else{
                    player.drawCard(drawed);
                    System.out.println("New Hand: " + player.getHand());
                }
            }
            else{
                var played = playable.get(0);
                player.playCard(played);
                discards.push(played);
                System.out.println("Played: " + played);
            }

            if (player.getHand().size() == 0) win = true;

            TurnManager.card = discards.peek();
            TurnManager.skipTurn();

            if (TurnManager.card.getValue() == CardValue.SKIP){
                System.out.println("Skipped: " + players[TurnManager.player].getName());
                TurnManager.skipTurn();
            }
            if(TurnManager.card.getValue() == CardValue.DRAW){
                var drawed = deck.draw(2);
                System.out.println("Next player " + players[TurnManager.player].getName() + " drawed 2: " + drawed);
                System.out.println("Skipped");
                players[TurnManager.player].drawCards(drawed);
                TurnManager.skipTurn();
            }
            if(TurnManager.card.getValue() == CardValue.REVERSE){
                reverse();
                System.out.println("Reversed direction");
                //TurnManager.skipTurn();
            }

            if (deck.size() == 0){
                System.out.println("Deck re-shuffle");
                deck.re_shuffle(discards);
            }

            System.out.println("");
            System.out.println("");
        }
        System.out.println("Winner was:" + players[TurnManager.player].getName());
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
        TurnManager.player = Math.abs(TurnManager.player - players.length - 1);
    }


}
