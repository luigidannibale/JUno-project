package Model;

import Model.Cards.Card;
import Model.Cards.CardValue;
import Model.Player.Player;
import Model.Rules.ActionPerformResult;
import Model.Rules.Options;
import Model.Rules.UnoGameRules;

import java.util.*;
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

        Arrays.stream(players).forEach(Player::resetPlayer);
    }


    public ActionPerformResult startGame()
    {
        win = false;
        deck.shuffle();
        //Distributing cards to each player
        IntStream.range(0, ruleManager.getNumberOfCardsPerPlayer()).forEach(i -> Arrays.stream(players).forEach(p -> p.drawCard(deck.draw())));

        //While the first card put on the ground is a wild four it's re-put in the deck, the deck is shuffled, and it is put the first card of the deck on the ground
        //while(deck.peek().getValue() == CardValue.WILD_DRAW){ deck.shuffle(); }
        while(deck.peek().getValue() != CardValue.SEVEN){ deck.shuffle(); }

        discards.push(deck.draw());
        turnManager = new TurnManager(discards.peek());

        //ruleManager.cardActionPerformance(getOptions().build());
        updateObservers();

        return performFirstCard(getOptions().currentPlayer(turnManager.getPlayer()).nextPlayer(turnManager.getPlayer()).build());
        //if (ruleManager.isStackableCards())
    }

    public ActionPerformResult performFirstCard(Options parameters)
    {
        ActionPerformResult actionPerformResult = ruleManager.startGame(parameters);
        updateObservers();
        return actionPerformResult;
    }

    public List<Card> getCurrentPlayerPLayableCards()
    {
        if (!currentPlayer().hasDrew())
            return ruleManager.getPlayableCards(currentPlayer().getValidCards(turnManager.getLastCardPlayed()),turnManager.getLastCardPlayed());
        else
        {
            Card drawnCard = currentPlayer().getHand().peek();
            return ruleManager.getPlayableCards(drawnCard.isValid(turnManager.getLastCardPlayed()) ? List.of(drawnCard) : new ArrayList<>(),turnManager.getLastCardPlayed());
        }
    }

    public void drawCard(Player currentPlayer)
    {
        if (deck.size() == 0)  deck.re_shuffle(discards);
        Card drewCard = deck.draw();
        currentPlayer.drawCard(drewCard);
        currentPlayer.setDrew(true);
        //--test start
        System.out.println(currentPlayer);
        System.out.println("DRAWED: " + drewCard);
        System.out.println("HAND: " + currentPlayer.getHand());
        System.out.println("PLAYABLE: " + getCurrentPlayerPLayableCards());
        //--test end
        updateObservers();
    }

    public void playCard(Card card)
    {
        //--test start
        System.out.println(currentPlayer());
        System.out.println("PLAYED: " + card);
        System.out.println("HAND: " + currentPlayer().getHand());
        System.out.println("PLAYABLE: " + getCurrentPlayerPLayableCards());
        //--test end
        currentPlayer().playCard(card);
        currentPlayer().setDrew(false);
        discards.push(card);
        turnManager.updateLastCardPlayed(card);
        //updateObservers();
    }
    public ActionPerformResult cardActionPerformance(Options parameters)
    {
        ActionPerformResult actionPerformResult = ruleManager.cardActionPerformance(parameters);
        if (actionPerformResult == ActionPerformResult.SUCCESSFUL) updateObservers();
        return actionPerformResult;
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
        currentPlayer().setPlayed(false);
        turnManager.passTurn();
        updateObservers();
    }

    public void checkWin(){
        if (currentPlayer().getHand().size() == 0){

        }
    }

    private void updateObservers()
    {
        setChanged();
        notifyObservers();
    }

    public boolean antiClockwiseTurn(){ return turnManager.antiClockwiseTurn();}
    public Card peekNextCard(){ return deck.peek(); }
    public Options.OptionsBuilder getOptions(){
        return new Options.OptionsBuilder(turnManager, players, deck);
    }
    public Player currentPlayer() { return players[currentPlayerIndex()]; }
    public int currentPlayerIndex() { return turnManager.getPlayer(); }
    public Player[] getPlayers() { return players; }
    public Deck getDeck() { return deck; }
    public Card getLastCard(){ return turnManager.getLastCardPlayed(); }
}


