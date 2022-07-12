package Model;

import Model.Cards.Card;
import Model.Cards.CardBuilder;
import Model.Cards.DrawCard;
import Model.Cards.ReverseCard;
import Model.Enumerations.CardColor;
import Model.Enumerations.CardValue;
import Model.Interfaces.SkipAction;
import Model.Interfaces.WildAction;
import Model.Player.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Stack;
import java.util.stream.IntStream;

public abstract class UnoGame extends Observable {
    protected UnoGameRules ruleManager;
    protected TurnManager turnManager;
    protected Deck deck;
    protected Stack<Card> discards;
    protected Player[] players;
    protected boolean win;

    protected UnoGame(Player[] players, UnoGameRules ruleManager)
    {
        this.ruleManager = ruleManager;
        deck = new Deck(ruleManager.getCardsDistribution());
        discards = new Stack<>();
        this.players = players;
    }


    public void startGame()
    {
        win = false;
        deck.shuffle();
        //Distributing cards to each player
        IntStream.range(0, ruleManager.numberOfCardsPerPlayer).forEach(i -> Arrays.stream(players).forEach(p -> p.drawCard(deck.draw())));

        //While the first card put on the ground is a wild four it's re-put in the deck, the deck is shuffled, and it is put the first card of the deck on the ground
        while(deck.peek().getValue() == CardValue.WILD_DRAW){ deck.shuffle(); }

        discards.push(deck.draw());
        turnManager = new TurnManager(discards.peek());
        cardActionPerformance(discards.peek());
    }

    public List<Card> getPLayableCards() { return players[turnManager.getPlayer()].getPlayableCards(turnManager.getCard()); }

    public void cardActionPerformance(Card card)
    {
        if (turnManager.getCard() instanceof WildAction && turnManager.getCard().getColor() == CardColor.WILD) {
            CardColor c = ((WildAction) turnManager.getCard()).changeColor();
            turnManager.updateCard(CardBuilder.createFlowCard(c,CardValue.WILD));
        }
        if(turnManager.getCard() instanceof DrawCard)
            ((DrawCard) turnManager.getCard()).drawCards(turnManager,players,deck);
        else if(turnManager.getCard() instanceof ReverseCard)
            ((ReverseCard) turnManager.getCard()).reverse(turnManager, players);
        if(turnManager.getCard() instanceof SkipAction)
            ((SkipAction) turnManager.getCard()).skipTurn(turnManager);
        /*
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
                CardColor c = ((DrawCard) card).changeColor();
                turnManager.updateCard(new Card(c,CardValue.WILD));
            }
            default -> {}
        }*/
    }
}

