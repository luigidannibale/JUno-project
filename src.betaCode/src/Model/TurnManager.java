package Model;

import Model.Cards.Card;
import Model.Enumerations.CardColor;
import Model.Enumerations.CardValue;

/**
 * Class used to manage the flow of turns in a Uno Match
 * Provides a good help to keep trace of the player that has to play and the last card played
 * @author D'annibale Luigi, Venturini Daniele
 */
public class TurnManager{
    private int player;
    private Card card;

    /**
     * Builds a TurnManager
     * @param card: the first card of the discards stack
     */
    public TurnManager(Card card){
        this.card = card;
    }

    /**
     * Increments the player index to the next
     */
    public void passTurn(){ player = next(); }

    /**
     *
     * @return nextPlayer: prvides the next player index
     */
    public int next(){ return ++player == 4 ? 0 : ++player; }

    /**
     *
     * @return player: provides the player who has to play the turn
     */
    public int getPlayer(){ return player; }

    ///////////////l'unico utilizzo sensato di setPLayer è nel reverse forse è meglio farli interagire
    /**
     *
     * @param player: the player that has to play the turn
     */
    public void setPlayer(int player){ this.player = player; }

    /**
     *
     * @return card: returns the last card played, more precisely a card which has the value of that card and the color (that can be either the same of the card or
     * the one chosen after a wild card)
     */
    public Card getCard(){return card;}

    /**
     *
     * @param card: the card that has been played
     */
    public void updateCard(Card card){this.card = card;}

    /**
     *
     * @param value : the value of the last card played
     * @param color : the color of the last card played (or the color that has been chosen after a wild)
     */
    public void UpdateCard(CardValue value, CardColor color){ updateCard(new Card(color,value)); }
}