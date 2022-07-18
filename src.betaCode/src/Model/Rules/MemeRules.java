package Model.Rules;

import Model.Cards.Card;
import Model.Cards.CardBuilder;
import Model.Cards.DrawCard;
import Model.Cards.ReverseCard;
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
        {
            switch (discardsPick.getValue())
            {
                case WILD_DRAW -> {}
                case SKIP -> {}
                case DRAW -> {playerPlayableHand.forEach(card -> card.getValue()==CardValue.DRAW?:playerPlayableHand.remove(card));}
                default ->{}
            }

        }
        return playerPlayableHand;
    }

    @Override
    public void cardActionPerformance(TurnManager turnManager, Card card, Player[] players, Deck deck)
    {
        if (turnManager.getLastCardPlayed() instanceof WildAction && turnManager.getLastCardPlayed().getColor() == CardColor.WILD) {
            CardColor c = ((WildAction) turnManager.getLastCardPlayed()).changeColor();
            turnManager.updateLastCardPlayed(CardBuilder.createFlowCard(c, CardValue.WILD));
        }
        if(turnManager.getLastCardPlayed() instanceof DrawCard)
            players[turnManager.next()].drawCards(deck.draw(((DrawCard) turnManager.getLastCardPlayed()).getNumberOfCardsToDraw()));
        else if(turnManager.getLastCardPlayed() instanceof ReverseCard)
            ((ReverseCard) turnManager.getLastCardPlayed()).reverse(turnManager, players);
        if(turnManager.getLastCardPlayed() instanceof SkipAction)
            ((SkipAction) turnManager.getLastCardPlayed()).skipTurn(turnManager);
    };
}
