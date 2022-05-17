package model;

import model.Cards.Card;
import model.Player.AIPlayer;
import model.Player.Player;

import java.util.List;
import java.util.Stack;

public class UnoBasicGame extends UnoGame{

    public UnoBasicGame(Player[] players)
    {
        deck = new Deck();
        discards = new Stack<>();
        this.players = players;
    }

    public void gamePlay(){
        while(!win){
            Player player = players[turnManager.getPlayer()];
            Card lastCard = turnManager.getCard();
            List<Card> playableCards = player.getPlayableCards(lastCard);

            if (playableCards.size() == 0) {
                Card drawed = deck.draw();
                player.drawCard(drawed);
                if (drawed.isPlayable(lastCard)) playableCards.add(drawed);
            }
            Card cardToPlay = null;
            if (player instanceof AIPlayer) cardToPlay = playableCards.get(0);


            discards.push(cardToPlay);
            turnManager.updateCard(cardToPlay);

            win = win || players[turnManager.getPlayer()].getHand().empty() ;
            turnManager.passTurn();
        }
    }
}
