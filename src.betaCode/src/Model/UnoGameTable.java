package Model;

import Model.Cards.Card;
import Model.Cards.Value;
import Model.Players.Player;
import Model.Rules.ActionPerformResult;
import Model.Rules.Options;
import Model.Rules.UnoGameRules;

import java.util.*;
import java.util.stream.IntStream;

/**
 * The UnoGameTable represents the table of a Uno game match. <br/>
 * Provides all the methods to play Uno full-matches in different game-modes. <br/>
 * @author D'annibale Luigi, Venturini Daniele
 */
public class UnoGameTable extends Observable
{
    private UnoGameRules ruleManager;
    private TurnManager turnManager;
    private DeckManager deckManager;
    private Player[] players;
    private boolean win;

    /**
     * Creates a new {@link UnoGameTable}, with the associated {@link UnoGameRules}, and resets each {@link Player} of the given array.
     * @param players
     * @param ruleManager
     */
    public UnoGameTable(Player[] players, UnoGameRules ruleManager)
    {
        this.ruleManager = ruleManager;
        this.players = players;
        Arrays.stream(players).forEach(Player::resetPlayer);
    }

    /**
     * Starts the game : <br/>
     * <ol>
     *     <li>shuffles the deck</li>
     *     <li>distributes the cards to each {@link Player}</li>
     *     <li>puts a {@link Card} on the discards</li>
     *     <li>performs the action associated to that {@link Card}</li>
     * </ol>
     * @return the {@link ActionPerformResult} of the first card
     */
    public ActionPerformResult startGame()
    {
        win = false;
        deckManager = new DeckManager(ruleManager.getCardsDistribution());
        deckManager.shuffle();
        Arrays.stream(players).forEach(p -> p.swapHand(new Stack<>()));
        //Distributing cards to each player
        IntStream.range(0, ruleManager.getNumberOfCardsPerPlayer()).forEach(i -> Arrays.stream(players).forEach(p -> p.drawCard(deckManager.draw())));
        //Can't start with wild draw 4
        while(deckManager.peekDeck().getValue() == Value.WILD_DRAW){ deckManager.shuffle(); }
        deckManager.pushDiscards(deckManager.draw());
        turnManager = new TurnManager(deckManager.peekDiscards());
        updateObservers();
        return performFirstCard(getOptions().currentPlayer(turnManager.getPlayer()).nextPlayer(turnManager.getPlayer()).build());
    }

    /**
     * Performs the action associated with the first {@link Card} put on the discards after shuffling the deck
     * @param parameters
     * @return the {@link ActionPerformResult} of the first card
     */
    public ActionPerformResult performFirstCard(Options parameters)
    {
        ActionPerformResult actionPerformResult = ruleManager.performFirstCardAction(parameters);
        updateObservers();
        return actionPerformResult;
    }

    /**
     *
     * @return the playable cards of the current {@link Player}
     */
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

    /**
     * The current {@link Player} draws one {@link Card}
     * @param currentPlayer
     */
    public void drawCard(Player currentPlayer)
    {
        Card drewCard = deckManager.draw();
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

    /**
     * The {@link Player} draws two card for not saying UNO
     * @param playerToExpose
     */
    public void expose(Player playerToExpose)
    {
        ArrayList<Card> drewCard = deckManager.draw(2);
        playerToExpose.drawCards(drewCard);
        updateObservers();
    }

    /**
     * Plays a {@link Card}
     * @param card
     * @return the {@link ActionPerformResult} of the performance of the action associated with the {@link Card}
     */
    public ActionPerformResult playCard(Card card)
    {
        //--test start
        System.out.println(currentPlayer());
        System.out.println("PLAYED: " + card);
        System.out.println("HAND: " + currentPlayer().getHand());
        System.out.println("PLAYABLE: " + getCurrentPlayerPLayableCards());
        //--test end
        currentPlayer().playCard(card);

        if (checkGameWin(currentPlayer()))
        {
            System.out.println("HAI VINTO " + currentPlayer());
            //current player ha vinto
            win = true;
            updateObservers();
            return ActionPerformResult.PLAYER_WON;
        }

        deckManager.pushDiscards(card);
        turnManager.updateLastCardPlayed(card);
        return ActionPerformResult.SUCCESSFUL;
    }

    /**
     * Performs the associated with the last {@link Card} played.
     * @param parameters
     * @return the {@link ActionPerformResult} of the performance of the action associated with the last {@link Card} played.
     */
    public ActionPerformResult cardActionPerformance(Options parameters)
    {
        ActionPerformResult actionPerformResult = ruleManager.cardActionPerformance(parameters);
        if (actionPerformResult == ActionPerformResult.SUCCESSFUL) updateObservers();
        return actionPerformResult;
    }

    /**
     * Passes the turn to the next player
     */
    public void passTurn()
    {
        //--test start
        System.out.println(currentPlayer());
        System.out.println("PASSED TURN");
        System.out.println("HAND: " + currentPlayer().getHand());
        System.out.println("PLAYABLE: " + currentPlayer().getValidCards(turnManager.getLastCardPlayed()));
        //--test end
        ruleManager.passTurn(turnManager,currentPlayer());
        updateObservers();
    }

    /**
     * @param player
     * @return true if the {@link Player} associated with the specified index is exposable (he didn't say Uno and he had to), false otherwise
     */
    public boolean isExposable(int player)
    {
        Player playerToExpose =players[player],
               playerAfterHim = players[turnManager.next(player)];
        return playerToExpose.hasOne() && !playerToExpose.hasSaidOne() && !playerAfterHim.hasDrew() && !playerAfterHim.hasPlayed();
    }

    /**
     * Notifies all the observers that the table has changed
     */
    private void updateObservers()
    {
        setChanged();
        notifyObservers();
    }

    /**
     *
     * @return true if the turn goes anticlockwise, false otherwise
     */
    public boolean antiClockwiseTurn(){ return turnManager.antiClockwiseTurn();}
    public Card peekNextCard(){ return deckManager.peekDeck(); }
    public Options.OptionsBuilder getOptions()  { return new Options.OptionsBuilder(turnManager, players, deckManager); }
    public Player currentPlayer() { return players[currentPlayerIndex()]; }
    public int currentPlayerIndex() { return turnManager.getPlayer(); }
    public Player[] getPlayers() { return players; }
    public DeckManager getDeck() { return deckManager; }
    public Card getLastCard(){ return turnManager.getLastCardPlayed(); }
    public TurnManager getTurnManager(){return turnManager;}
    public boolean hasWin(){ return win; }
    public boolean checkWin(Player player){ return ruleManager.checkWin(players, player); }
    public boolean checkGameWin(Player player){ return ruleManager.checkGameWin(player); }
}
