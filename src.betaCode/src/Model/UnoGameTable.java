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
public class UnoGameTable extends Observable
{
    protected UnoGameRules ruleManager;
    protected TurnManager turnManager;
    protected DeckManager deckManager;
    protected Player[] players;

    //win must be a method
    protected boolean win;

    public UnoGameTable(Player[] players, UnoGameRules ruleManager)
    {
        this.ruleManager = ruleManager;
        this.players = players;
        Arrays.stream(players).forEach(Player::resetPlayer);
    }


    public ActionPerformResult startGame()
    {
        win = false;
        deckManager = new DeckManager(ruleManager.getCardsDistribution());
        deckManager.shuffle();

        Arrays.stream(players).forEach(p -> p.swapHand(new Stack<>()));
        //Distributing cards to each player
        IntStream.range(0, ruleManager.getNumberOfCardsPerPlayer()).forEach(i -> Arrays.stream(players).forEach(p -> p.drawCard(deckManager.draw())));
        //Can't start with wild draw 4
        while(deckManager.peek().getValue() == CardValue.WILD_DRAW){ deckManager.shuffle(); }

        deckManager.pushDiscards(deckManager.draw());

        turnManager = new TurnManager(deckManager.peekDiscards());

        updateObservers();
        return performFirstCard(getOptions().currentPlayer(turnManager.getPlayer()).nextPlayer(turnManager.getPlayer()).build());
    }

    public ActionPerformResult performFirstCard(Options parameters)
    {
        ActionPerformResult actionPerformResult = ruleManager.performFirstCardAction(parameters);
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
        //if (deck.size() == 0)  deck.re_shuffle(discards);
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

    public void expose(Player playerToExpose)
    {
        //if (deck.size() == 0)  deck.re_shuffle(discards);
        ArrayList<Card> drewCard = deckManager.draw(2);
        playerToExpose.drawCards(drewCard);
        updateObservers();
    }

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
            System.out.println("HAIVINTO");
            //current player ha vinto
            win = true;
            updateObservers();
            return ActionPerformResult.WIN;
        }

        deckManager.pushDiscards(card);
        turnManager.updateLastCardPlayed(card);
        return ActionPerformResult.SUCCESSFUL;
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
        ruleManager.passTurn(turnManager,currentPlayer());
        updateObservers();
    }

    public boolean isExposable(int player)
    {
        Player playerToExpose =players[player],
               playerAfterHim = players[turnManager.next(player)];
        return playerToExpose.hasOne() && !playerToExpose.hasSaidOne() && !playerAfterHim.hasDrew() && !playerAfterHim.hasPlayed();
    }

    private void updateObservers()
    {
        setChanged();
        notifyObservers();
    }

    public boolean antiClockwiseTurn(){ return turnManager.antiClockwiseTurn();}
    public Card peekNextCard(){ return deckManager.peek(); }
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



//    public void playCard(Card card)
//    {
//        //--test start
//        System.out.println(currentPlayer());
//        System.out.println("PLAYED: " + card);
//        System.out.println("HAND: " + currentPlayer().getHand());
//        System.out.println("PLAYABLE: " + getCurrentPlayerPLayableCards());
//        //--test end
//        currentPlayer().playCard(card);
//        currentPlayer().setDrew(false);
//
//        if (checkWin())
//        {
//            //current player ha vinto
//            return;
//        }
//
//        discards.push(card);
//        turnManager.updateLastCardPlayed(card);
//
//        //updateObservers();
//
//    }
    /*
    public ActionPerformResult playCard(Card card, Options parameters)
    {
        Player current = currentPlayer();
        current.playCard(card);
        turnManager.updateLastCardPlayed(card);

        if (checkGameWin(current))
        {
            System.out.println("HAIVINTO");
            //current player ha vinto
            win = true;
            updateObservers();
            return ActionPerformResult.WIN;
        }

        ActionPerformResult actionPerformResult = ruleManager.cardActionPerformance(parameters);

        if (actionPerformResult != ActionPerformResult.SUCCESSFUL){
            current.drawCard(card);
            return actionPerformResult;
        }
        //--test start
        System.out.println(current);
        System.out.println("PLAYED: " + card);
        System.out.println("HAND: " + current.getHand());
        System.out.println("PLAYABLE: " + getCurrentPlayerPLayableCards());
        //--test end

        current.setDrew(false);
        deckManager.pushDiscards(card);
        updateObservers();

        return ActionPerformResult.SUCCESSFUL;
    }

    private ActionPerformResult cardActionPerformance(Options parameters)
    {
        ActionPerformResult actionPerformResult = ruleManager.cardActionPerformance(parameters);
        if (actionPerformResult == ActionPerformResult.SUCCESSFUL) updateObservers();
        return actionPerformResult;
    }

     */

