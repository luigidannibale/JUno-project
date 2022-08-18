package Model;

import Model.Cards.Card;
import Model.Cards.CardValue;
import Model.Player.Player;
import Model.Rules.UnoGameRules;

import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Stack;
import java.util.stream.IntStream;

/**
 *
 * @author D'annibale Luigi, Venturini Daniele
 */
public class UnoGameTable extends Observable {
    protected UnoGameRules ruleManager;
    protected TurnManager turnManager;
    protected Deck deck;
    protected Stack<Card> discards;
    protected Player[] players;

    //win must be a method
    protected boolean win;

    public UnoGameTable(Player[] players, UnoGameRules ruleManager)
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
        IntStream.range(0, ruleManager.getNumberOfCardsPerPlayer()).forEach(i -> Arrays.stream(players).forEach(p -> p.drawCard(deck.draw())));

        //While the first card put on the ground is a wild four it's re-put in the deck, the deck is shuffled, and it is put the first card of the deck on the ground
        while(deck.peek().getValue() == CardValue.WILD_DRAW){ deck.shuffle(); }

        discards.push(deck.draw());
        turnManager = new TurnManager(discards.peek());
        //ruleManager.cardActionPerformance(discards.peek());
        updateObservers();
    }
    public Player currentPlayer()
    { return players[turnManager.getPlayer()]; }
    public List<Card> getPLayableCards()
    { return ruleManager.getPlayableCards(players[turnManager.getPlayer()].getValidCards(turnManager.getLastCardPlayed()),turnManager.getLastCardPlayed()); }

    public Player[] getPlayers() { return players; }

    public Deck getDeck() { return deck; }
    public Card getLastCard(){ return discards.peek(); }
    public void drawCard(){
        Card drawedCard = deck.draw();
        if (drawedCard == null)
        {
            deck.re_shuffle(discards);
            drawedCard = deck.draw();
        }
        players[turnManager.getPlayer()].drawCard(drawedCard);
        //--test start
        System.out.println(currentPlayer());
        System.out.println("DRAWED: " + drawedCard);
        System.out.println("HAND: " + currentPlayer().getHand());
        System.out.println("PLAYABLE: " + currentPlayer().getValidCards(turnManager.getLastCardPlayed()));
        //--test end
        updateObservers();
    }

    public void playCard(Card card)
    {
        //--test start
        System.out.println(currentPlayer());
        System.out.println("PLAYED: " + card);
        System.out.println("HAND: " + currentPlayer().getHand());
        System.out.println("PLAYABLE: " + currentPlayer().getValidCards(turnManager.getLastCardPlayed()));
        //--test end
        players[turnManager.getPlayer()].playCard(card);
        discards.push(card);
        turnManager.updateLastCardPlayed(card);
        ruleManager.cardActionPerformance(turnManager, players, deck);
        updateObservers();
    }

    public Card peekNextCard(){ return deck.peek(); }

    public void passTurn()
    {
        //--test start
        System.out.println(currentPlayer());
        System.out.println("PASSED TURN");
        System.out.println("HAND: " + currentPlayer().getHand());
        System.out.println("PLAYABLE: " + currentPlayer().getValidCards(turnManager.getLastCardPlayed()));
        //--test end
        turnManager.passTurn();
        updateObservers();
    }
    private void updateObservers()
    {
        setChanged();
        notifyObservers();
    }

    public TurnManager getTurnManager(){ return turnManager; }
}
//nousecode
    /*
    public void cardActionPerformance(Card card)
    {
        if (turnManager.getLastCardPlayed() instanceof WildAction && turnManager.getLastCardPlayed().getColor() == CardColor.WILD) {
            CardColor c = ((WildAction) turnManager.getLastCardPlayed()).changeColor();
            turnManager.updateLastCardPlayed(CardBuilder.createFlowCard(c,CardValue.WILD));
        }
        if(turnManager.getLastCardPlayed() instanceof DrawCard)
            ((DrawCard) turnManager.getLastCardPlayed()).drawCards(turnManager,players,deck);

        else if(turnManager.getLastCardPlayed() instanceof ReverseCard)
            ((ReverseCard) turnManager.getLastCardPlayed()).reverse(turnManager, players);
        if(turnManager.getLastCardPlayed() instanceof SkipAction)
            ((SkipAction) turnManager.getLastCardPlayed()).skipTurn(turnManager);
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
        }
    }*/

