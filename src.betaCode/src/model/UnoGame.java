package model;

import model.Cards.*;
import model.Enumerations.*;
import model.Player.Player;

import java.util.Arrays;
import java.util.Observable;
import java.util.Stack;
import java.util.stream.IntStream;

public abstract class UnoGame extends Observable {

    protected TurnManager turnManager;
    protected Deck deck;
    protected Stack<Card> discards;
    protected Player[] players;
    protected boolean win;

    public void startGame(int cardsForEachPlayer)
    {
        win = false;
        deck.shuffle();
        IntStream.range(0, cardsForEachPlayer).forEach(i -> Arrays.stream(players).forEach(p -> p.drawCard(deck.draw())));
        //if discard discard.peek == wild_four -> carta rimessa nel deck e se ne prende un'altra
        while(deck.peek().getValue() == CardValue.WILD_DRAW){ deck.shuffle(); }
        discards.push(deck.draw());
        turnManager = new TurnManager(discards.peek());
        cardActionPerformance(discards.peek());
    }



    public void cardActionPerformance(Card card)
    {

        switch (card.getValue()){
            case REVERSE -> ((ReverseCard) card).reverse(turnManager,players); //if discard discard.peek == reverse -> il primo gioca e poi cambia giro
            case DRAW -> {//if discard discard.peek == draw -> il primo pesca due carte
                players[turnManager.getPlayer()].drawCards(deck.draw(2));
                ((DrawCard) card).skipTurn(turnManager);
            }
            case SKIP -> ((SkipCard) card).skipTurn(turnManager); //if discard discard.peek == skip -> il primo viene skippato
            case WILD -> {//if discard discard.peek == wild -> il primo sceglie il colore
                CardColor c = ((WildCard) card).changeColor();
                turnManager.updateCard(new Card(c,CardValue.WILD));
            }
            case WILD_DRAW -> {
                players[turnManager.getPlayer()].drawCards(deck.draw(4));
                ((DrawCard) card).skipTurn(turnManager);
                CardColor c = ((WildCard) card).changeColor();
                turnManager.updateCard(new Card(c,CardValue.WILD));
            }
            default -> {}
        }

        //Valid alternative
        /* tornerà...
        if (turnManager.getCard() instanceof WildAction && turnManager.getCard().getColor() == CardColor.WILD) {
            CardColor c = ((WildAction) turnManager.getCard()).changeColor();
            turnManager.updateCard(new Card(c,CardValue.WILD));
        }
        if(turnManager.getCard() instanceof DrawCard)
            ((DrawCard) turnManager.getCard()).drawCards(turnManager,players,deck);
        else if(turnManager.getCard() instanceof ReverseCard)
            ((ReverseCard) turnManager.getCard()).reverse(turnManager, players);
        if(turnManager.getCard() instanceof SkipAction)
            ((SkipAction) turnManager.getCard()).skipTurn(turnManager);
    */
    }
}

