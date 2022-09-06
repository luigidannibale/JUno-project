package Model.Player;

import Model.Cards.Card;
import Model.Cards.CardColor;

import java.util.*;
import java.util.stream.Collectors;

public class AIPlayer extends Player
{
    public AIPlayer(String name) { super(name); }

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

    @Override
    public List<Card> getValidCards(Card check){ return chooseBestCards(check); }

    public CardColor chooseBestColor()
    {
        Map<CardColor, Long> colorWeights = colorWeights();
        colorWeights.remove(CardColor.WILD);

        Long max = colorWeights.isEmpty() ? 1L : Collections.max(colorWeights.values());
        Optional<CardColor> bestColor = colorWeights.keySet().stream().filter(color -> Objects.equals(colorWeights.get(color), max)).findFirst();
        return (bestColor.isEmpty()) ? randomColor():bestColor.get();
    }
    public Player chooseBestPlayerToSwap(Player[] players, int current)
    {
        int min = players[0].getHand().size();
        Player bestPlayer = players[0];
        for (Player p: players) if (p.getHand().size()<min && !p.equals(players[current])) bestPlayer = p;
        return bestPlayer;
    }
    public boolean chooseToSayUno()
    {
        boolean choice = choiceFactor();
        if (choice) shoutUno();
        return choice;
    }

    /**
     * 25% false, 75%true
     * @return
     */
    public boolean choiceFactor()
    {
        return new Random().nextInt(1,4)>1;
    }

    private CardColor randomColor(){ return CardColor.values()[new Random().nextInt(0,4)]; }
}
