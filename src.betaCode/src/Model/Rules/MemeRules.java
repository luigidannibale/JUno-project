package Model.Rules;

import Model.Cards.*;
import Model.Deck;
import Model.Enumerations.CardColor;
import Model.Enumerations.CardValue;
import Model.Interfaces.SkipAction;
import Model.Interfaces.WildAction;
import Model.Player.Player;
import Model.TurnManager;

import java.util.HashMap;
import java.util.List;

public class MemeRules extends UnoGameRules{

    public MemeRules(){
        cardsDistribution = Deck.classicRules;
        cardsDistribution.putAll(new HashMap<>(){{
            put(CardValue.SKIP,4);
            put(CardValue.DRAW,4);
            put(CardValue.REVERSE,4);
            put(CardValue.WILD,4);
            put(CardValue.WILD_DRAW,8);
        }});
        stackableCards = true;
        numberOfPlayableCards = 5;
        numberOfCardsPerPlayer = 11;
    }

    @Override
    public List<Card> getPlayableCards(List<Card> playerPlayableHand, Card discardsPick) {
        if (discardsPick instanceof SkipAction || discardsPick instanceof WildAction )
            playerPlayableHand = playerPlayableHand.stream().filter(card -> card.getValue()==discardsPick.getValue()).toList();
        return playerPlayableHand;
    }

    @Override
    public void cardActionPerformance(TurnManager turnManager, Player[] players, Deck deck)
    {   /*
        Card lastCard = turnManager.getLastCardPlayed();
        if (lastCard instanceof WildAction && lastCard.getColor() == CardColor.WILD &&
                players[turnManager.next()].getHand().stream().anyMatch(card -> card.getValue()==CardValue.WILD_DRAW)) {
            //serve per forza lo switch
            CardColor c = ((WildAction) lastCard).changeColor();
            turnManager.updateLastCardPlayed(CardBuilder.createFlowCard(c, CardValue.WILD));
        }
        if(lastCard instanceof DrawCard)
            players[turnManager.next()].drawCards(deck.draw(((DrawCard) lastCard).getNumberOfCardsToDraw()));
        else if(lastCard instanceof ReverseCard)
            ((ReverseCard) lastCard).reverse(turnManager, players);
        if(lastCard instanceof SkipAction)
            ((SkipAction) lastCard).skipTurn(turnManager);

        switch (lastCard.getValue()){
            case REVERSE -> ((ReverseCard) lastCard).reverse(turnManager,players); //if discard discard.peek == reverse -> il primo gioca e poi cambia giro
            case DRAW -> {//if discard discard.peek == draw -> il primo pesca due carte
                players[turnManager.getPlayer()].drawCards(deck.draw(2));
                ((DrawCard) lastCard).skipTurn(turnManager);
            }
            case SKIP -> ((SkipCard) lastCard).skipTurn(turnManager); //if discard discard.peek == skip -> il primo viene skippato
            case WILD -> {//if discard discard.peek == wild -> il primo sceglie il colore
                CardColor c = ((WildCard) lastCard).changeColor();
                turnManager.updateLastCardPlayed(CardValue.WILD,c);
            }
            case WILD_DRAW -> {
                players[turnManager.getPlayer()].drawCards(deck.draw(4));
                ((DrawCard) lastCard).skipTurn(turnManager);
                CardColor c = ((DrawCard) lastCard).changeColor();
                turnManager.updateLastCardPlayed(CardValue.WILD,c);
            }
            default -> {}
        }*/
    }
}
