package Model;

import Model.Cards.Card;
import Model.Player.AIPlayer;
import Model.Player.Player;
import Model.Rules.ClassicRules;

import java.util.List;

public class UnoBasicGame extends UnoGame{

    public UnoBasicGame(Player[] players)
    {
        super(players,new ClassicRules());
    }

    public void gamePlay(){
        while(!win){
            Player player = players[turnManager.getPlayer()];
            Card lastCard = turnManager.getLastCardPlayed();
            List<Card> playableCards = player.getPlayableCards(lastCard);

            if (playableCards.size() == 0)
            {
                Card drawed = deck.draw();
                player.drawCard(drawed);
                if (drawed.isPlayable(lastCard)) playableCards.add(drawed);
            }
            Card cardToPlay = null;
            if (player instanceof AIPlayer) cardToPlay = playableCards.get(0);

            discards.push(cardToPlay);
            turnManager.updateLastCardPlayed(cardToPlay);

            win = players[turnManager.getPlayer()].getHand().empty() ;
            turnManager.passTurn();
        }
    }
    public void playCard(int player,Card card)
    {
        this.players[player].playCard(card);
        discards.push(card);
        //ruleManager.cardActionPerformance(card);
    }
    /*@Override
    public List<Card> getPLayableCards()
    {
        List<Card> playableCards = super.getPLayableCards();
        if(playableCards.stream().anyMatch(card -> card.getColor()!= CardColor.WILD) && playableCards.stream().anyMatch(card -> card.getColor()==CardColor.WILD))
        {//if there is at least one not wild card and one wild card all the wild cards are not playables
            playableCards = playableCards.stream().filter(card -> card.getColor()!=CardColor.WILD).toList();
        }
        return playableCards;
    }*/
}
