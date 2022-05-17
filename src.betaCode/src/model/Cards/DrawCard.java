package model.Cards;

import model.Deck;
import model.Enumerations.*;
import model.Interfaces.*;
import model.Player.Player;
import model.TurnManager;

public class DrawCard extends Card implements WildAction, SkipAction {

    public DrawCard(CardColor color)
    {
        super(color, color == CardColor.WILD ? CardValue.WILD_DRAW : CardValue.DRAW);
    }

    public void drawCards(TurnManager turnManager, Player[] players, Deck deck) {
        players[turnManager.next()].drawCards(deck.draw(color == CardColor.WILD ? 4 : 2));
    }

}
