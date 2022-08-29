package Model;

import Model.Cards.Card;
import Model.Cards.CardValue;
import Model.Player.Player;
import Model.Rules.ActionPerformResult;
import Model.Rules.Options;
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

    public List<Card> getCurrentPlayerPLayableCards()
    { return ruleManager.getPlayableCards(currentPlayer().getValidCards(turnManager.getLastCardPlayed()),turnManager.getLastCardPlayed()); }

    public void drawCard()
    {
        drawCard(currentPlayer());
        /*
        Card drewCard = deck.draw();
        if (drewCard == null)
        {
            deck.re_shuffle(discards);
            drewCard = deck.draw();
        }
        currentPlayer().drawCard(drewCard);
        currentPlayer().setDrew(true);
        //--test start
        System.out.println(currentPlayer());
        System.out.println("DRAWED: " + drewCard);
        System.out.println("HAND: " + currentPlayer().getHand());
        System.out.println("PLAYABLE: " + currentPlayer().getValidCards(turnManager.getLastCardPlayed()));
        //--test end
        updateObservers();

         */
    }

    public void drawCard(Player currentPlayer)
    {
        Card drewCard = deck.draw();
        if (drewCard == null)
        {
            deck.re_shuffle(discards);
            drewCard = deck.draw();
        }
        currentPlayer.drawCard(drewCard);
        currentPlayer.setDrew(true);
        //--test start
        System.out.println(currentPlayer);
        System.out.println("DRAWED: " + drewCard);
        System.out.println("HAND: " + currentPlayer.getHand());
        System.out.println("PLAYABLE: " + currentPlayer.getValidCards(turnManager.getLastCardPlayed()));
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
        currentPlayer().playCard(card);
        currentPlayer().setDrew(false);
        discards.push(card);
        turnManager.updateLastCardPlayed(card);

//        Options parameters = new Options.OptionsBuilder(turnManager, players, deck).build();
        //ruleManager.cardActionPerformance(getOptions().build());
        //updateObservers();
    }
    public ActionPerformResult cardActionPerformance(Options parameters)
    {
        var a = ruleManager.cardActionPerformance(parameters);
        if (a == ActionPerformResult.SUCCESSFUL) updateObservers();
        return a;
    }

    public void passTurn()
    {
        //--test start
        System.out.println(currentPlayer());
        System.out.println("PASSED TURN");
        System.out.println("HAND: " + currentPlayer().getHand());
        System.out.println("PLAYABLE: " + currentPlayer().getValidCards(turnManager.getLastCardPlayed()));
        //--test end
        currentPlayer().setDrew(false);
        turnManager.passTurn();
        updateObservers();
    }

    public boolean clockwiseTurn(){ return turnManager.clockwiseTurn();}

    private void updateObservers()
    {
        setChanged();
        notifyObservers();
    }

    public Card peekNextCard(){ return deck.peek(); }

    public TurnManager getTurnManager(){ return turnManager; }

    public Options.OptionsBuilder getOptions(){
        return new Options.OptionsBuilder(turnManager, players, deck);
    }

    public Player currentPlayer() { return players[turnManager.getPlayer()]; }

    public List<Card> getPLayableCards()
    { return ruleManager.getPlayableCards(currentPlayer().getValidCards(turnManager.getLastCardPlayed()),turnManager.getLastCardPlayed()); }

    public Player[] getPlayers() { return players; }

    public Deck getDeck() { return deck; }
    public Card getLastCard(){ return turnManager.getLastCardPlayed(); }
}


