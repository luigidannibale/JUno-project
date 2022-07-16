package Model.Cards;

import Model.Deck;
import Model.Enumerations.*;
import Model.Interfaces.*;
import Model.Player.Player;
import Model.TurnManager;

public class DrawCard extends Card implements WildAction, SkipAction {
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



}
