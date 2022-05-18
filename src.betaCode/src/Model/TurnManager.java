package Model;

import Model.Cards.Card;
import Model.Enumerations.CardColor;
import Model.Enumerations.CardValue;

public class TurnManager{
    int player;
    Card card;

    public TurnManager(Card card){
        this.card = card;
    }

    public void passTurnUpdateCard(CardValue v, CardColor c){ passTurnUpdateCard(new Card(c,v)); }
    public void passTurnUpdateCard(Card c){
        player = next();
        this.card = c;
    }

    public void passTurn(){
        player = next();
    }

    public int next(){ return ++player == 4 ? 0 : ++player; }
    public int getPlayer(){ return player; }
    public void setPlayer(int player){ this.player = player; }
    public Card getCard(){return card;}
    public void updateCard(Card c){card = c;}
}