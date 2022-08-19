package Model.Rules;

import Model.Cards.*;
import Model.Deck;
import Model.Player.Player;
import Model.TurnManager;
import Model.UnoGameTable;

import java.util.List;

public class ClassicRules extends UnoGameRules{

    public ClassicRules()
    {
        cardsDistribution = Deck.classicRules;
        stackableCards = false;
        numberOfPlayableCards = 1;
        numberOfCardsPerPlayer = 7;
    }

    @Override
    public List<Card> getPlayableCards(List<Card> playerPlayableHand, Card discardsPick)
    {
        //if there is at least one not wild card and one wild card all the wild cards are not playables
        if(playerPlayableHand.stream().anyMatch(card -> card.getColor()!= CardColor.WILD) && playerPlayableHand.stream().anyMatch(card -> card.getColor()==CardColor.WILD))
            playerPlayableHand = playerPlayableHand.stream().filter(card -> card.getColor()!=CardColor.WILD).toList();
        return playerPlayableHand;
    }

    @Override
    public void cardActionPerformance(TurnManager turnManager, Player[] players, Deck deck)
    {
        Card lastCard = turnManager.getLastCardPlayed();

        if (lastCard instanceof WildAction && lastCard.getColor() == CardColor.WILD)
        {
            //color deve essere preso come scelta dell'utente
            CardColor color = CardColor.RED;
            ((WildAction) lastCard).changeColor(turnManager, color);
        }
        if(lastCard instanceof DrawCard)
            players[turnManager.next()].drawCards(deck.draw(((DrawCard) lastCard).getNumberOfCardsToDraw()));
        else if(lastCard instanceof ReverseCard)
            ((ReverseCard) lastCard).performReverseAction(turnManager, players);
        if(lastCard instanceof SkipAction)
            ((SkipAction) lastCard).performSkipAction(turnManager);

        turnManager.passTurn();
    }
    @Override
    public void cardActionPerformance(UnoGameTable gameTable)
    {
        TurnManager turnManager = gameTable.getTurnManager();
        Player[] players = gameTable.getPlayers();
        Deck deck = gameTable.getDeck();

        Card lastCard = turnManager.getLastCardPlayed();

        switch (lastCard.getValue())
        {
            case REVERSE :
            {//if discard discard.peek == reverse -> il primo gioca e poi cambia giro
                ((ReverseCard) lastCard).performReverseAction(turnManager,players);
                break;
            }
            case WILD_DRAW:
            case DRAW:
            {//if discard discard.peek == draw -> il primo pesca due carte
                players[turnManager.getPlayer()].drawCards(deck.draw(((DrawCard)lastCard).getNumberOfCardsToDraw()));
                ((DrawCard) lastCard).performSkipAction(turnManager);
                break;
            }
            case SKIP :
            {//if discard discard.peek == skip -> il primo viene skippato
                ((SkipCard) lastCard).performSkipAction(turnManager);
                break;
            }
            /*i wild li gestisco a parte, uso lo switch perchè devo definire una regola specifica e non che prenda tutte
             le ereditarietà, si puo gestire il wild draw in altra maniera a che serve il cardvalue wild draw*/
        }
        turnManager.passTurn();
    }
    public void cardActionPerformance(UnoGameTable gameTable,CardColor color)
    {
        TurnManager turnManager = gameTable.getTurnManager();
        Player[] players = gameTable.getPlayers();
        Deck deck = gameTable.getDeck();

        Card lastCard = turnManager.getLastCardPlayed();
        if (lastCard instanceof WildAction && lastCard.getColor() == CardColor.WILD)
            ((WildAction) lastCard).changeColor(turnManager, color);
        cardActionPerformance(gameTable);
    }

}
