package Model.Player;

import Model.Cards.Card;
import Model.Cards.Enumerations.CardColor;

import java.util.*;
import java.util.stream.Collectors;

public class AIPlayer extends Player{


    public AIPlayer(String name) {
        super(name);
    }

    @Override
    public void shoutUno() {}

    /**
     * @return a map with the colors and the count of the occurrencies for each color
     */
    private Map<CardColor,Long> colorWeights(){ return hand.stream().collect(Collectors.groupingBy(Card::getColor, Collectors.counting())); }

    /**
     * this method has been designed to be smart enough to decide which card is the most convenient to play
     * @param check: the card that is on top of the discards 
     * @return List<Card> of playable cards
     */
    @Override
    public List<Card> getPlayableCards(Card check)
    {
        List<Card> playableCards = super.getPlayableCards(check);
        List<Card> wildCards = playableCards.stream().filter(c -> c.getColor() == CardColor.WILD).toList();
        playableCards.removeAll(wildCards);
        playableCards.sort(Comparator.comparing(i -> colorWeights().get(((Card) i).getColor())).reversed().thenComparing(i -> ((Card)i).getColor().VALUE));
        playableCards.addAll(wildCards);
        return playableCards;
    }
}
