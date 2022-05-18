package Model.Cards;

import Model.Deck;
import Model.Enumerations.*;
import Model.Interfaces.*;
import Model.Player.Player;
import Model.TurnManager;

public class DrawCard extends Card implements WildAction, SkipAction {

    public DrawCard(CardColor color)
    {
        super(color, color == CardColor.WILD ? CardValue.WILD_DRAW : CardValue.DRAW);
    }

    public void drawCards(TurnManager turnManager, Player[] players, Deck deck) {
        players[turnManager.next()].drawCards(deck.draw(color == CardColor.WILD ? 4 : 2));
    }

}
