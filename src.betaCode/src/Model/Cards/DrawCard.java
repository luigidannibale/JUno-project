package Model.Cards;

import Model.Cards.Enumerations.CardColor;
import Model.Cards.Enumerations.CardValue;
import Model.Cards.Interfaces.SkipAction;
import Model.Cards.Interfaces.WildAction;
import Model.Deck;
import Model.Player.Player;

public class DrawCard extends ActionCard implements WildAction, SkipAction {
    private int numberOfCardsToDraw;


    public DrawCard(CardColor color, int numberOfCardsToDraw)
    {
        super(color, color == CardColor.WILD ? CardValue.WILD_DRAW : CardValue.DRAW);
        this.numberOfCardsToDraw = numberOfCardsToDraw;
    }
    public DrawCard(CardColor color)
    {
        super(color, color == CardColor.WILD ? CardValue.WILD_DRAW : CardValue.DRAW);
        this.numberOfCardsToDraw = (color == CardColor.WILD) ? 4 : 2;
    }

    public int getNumberOfCardsToDraw() {
        return numberOfCardsToDraw;
    }

    public void setNumberOfCardsToDraw(int numberOfCardsToDraw) {
        this.numberOfCardsToDraw = numberOfCardsToDraw;
    }

    public void performDrawAction(Player player, int cardsToDraw, Deck deck)
    {
        player.drawCards(deck.draw(cardsToDraw));
    }


}
