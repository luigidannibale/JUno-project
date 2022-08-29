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
    //@Override
    public List<Card> chooseBestCards(Card check)
    {
        List<Card> playableCards = super.getValidCards(check);
        List<Card> wildCards = playableCards.stream().filter(c -> c.getColor() == CardColor.WILD).toList();
        Map<CardColor, Long> colorWeights = colorWeights();
        playableCards.removeAll(wildCards);
        playableCards.sort(Comparator.comparing(i -> colorWeights.get(((Card) i).getColor())).reversed().thenComparing(i -> ((Card)i).getColor().VALUE));
        playableCards.addAll(wildCards);
        return playableCards;
    }

    public CardColor chooseBestColor()
    {

        Map<CardColor, Long> colorWeights = colorWeights();
        colorWeights.remove(CardColor.WILD);

        Long max = Collections.max(colorWeights.values());
        Optional<CardColor> bestColor = colorWeights.keySet().stream().filter(color -> colorWeights.get(color) == max).findFirst();
        return (bestColor.isEmpty()) ? randomColor():bestColor.get();
//        var some = hand.stream().filter(c -> c.getColor() != CardColor.WILD).sorted(Comparator.comparing(c -> colorWeights.get(((Card)c).getColor())).reversed()).toList();
//        CardColor chosen = some.size() == 0 ? randomColor() : some.get(0).getColor();
//        return chosen;
    }
    private CardColor randomColor()
    {
        var colors = Arrays.stream(CardColor.values()).toList();
        colors.remove(CardColor.WILD);
        Collections.shuffle(colors);
        return colors.get(0);
    }
}
