package Model;

import Model.Cards.Card;
import Model.Player.AIPlayer;
import Model.Player.Player;

import java.util.List;

public class UnoBasicGame extends UnoGame{

    public UnoBasicGame(Player[] players)
    {
        super(players);
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
    public void playCard(Player player,Card card)
    {
        player.playCard(card);
        discards.push(card);
        cardActionPerformance(card);
    }
}
