package Model.Player;

import Model.Cards.Card;
import Model.Cards.CardColor;

import java.util.*;
import java.util.stream.Collectors;

public class AIPlayer extends Player
{
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
    public List<Card> getValidCards(Card check)
    {
        List<Card> playableCards = super.getValidCards(check);
        List<Card> wildCards = playableCards.stream().filter(c -> c.getColor() == CardColor.WILD).toList();
        Map<CardColor, Long> colorWeights = colorWeights();
        playableCards.removeAll(wildCards);
        playableCards.sort(Comparator.comparing(i -> colorWeights.get(((Card) i).getColor())).reversed().thenComparing(i -> ((Card)i).getColor().VALUE));
        playableCards.addAll(wildCards);
        return playableCards;
    }

    public CardColor chooseBestColor(){
        //funziona ma sembra esageratamente lungo
        //var some = hand.stream().collect(Collectors.groupingBy(Card::getColor, Collectors.counting())).entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        Map<CardColor, Long> colorWeights = colorWeights();
        var some = hand.stream().filter(c -> c.getColor() != CardColor.WILD).sorted(Comparator.comparing(c -> colorWeights.get(((Card)c).getColor())).reversed()).toList();
        //some.forEach(System.out::println);
        return some.get(0).getColor();
    }
}
