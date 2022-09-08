package Model.Players;

import Model.Cards.Card;
import Model.Cards.Color;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class specializes {@link Player} to provide the artificial intelligence methods,
 * in particular the method the simulates the human choice factor are :
 * <ul>
 *     <li>choosing to say Uno (human players can forget so the AI should not be perfect too)</li>
 *     <li>choosing to expose someone that forget to say Uno (human players can forget so the AI should not be perfect too)</li>
 *     <li>choosing the best card to play</li>
 *     <li>choosing the color when performing a Wild</li>
 * </ul>
 * In general for each of all the choices a human player performs, there has to be a corresponding AI method.
 * @author D'annibale Luigi, Venturini Daniele
 */
public class AIPlayer extends Player
{
    /**
     * Creates an {@link AIPlayer} with the name
     * @param name
     */
    public AIPlayer(String name) { super(name); }

    /**
     * @return a map with the {@link Color} and the count of the times it occurs
     */
    private Map<Color,Long> colorWeights(){ return hand.stream().collect(Collectors.groupingBy(Card::getColor, Collectors.counting())); }

    /**
     * This method has been designed to be smart enough to decide which {@link Card} is the most convenient to play.
     * @param check: the card that is on top of the discards
     * @return a List of {@link Card} representing the  cards, sorted from the most convenient to the less.
     */
    private List<Card> chooseBestCards(Card check)
    {
        List<Card> playableCards = super.getValidCards(check);
        List<Card> wildCards = playableCards.stream().filter(c -> c.getColor() == Color.WILD).toList();
        Map<Color, Long> colorWeights = colorWeights();
        playableCards.removeAll(wildCards);
        playableCards.sort(Comparator.comparing(i -> colorWeights.get(((Card) i).getColor())).reversed().thenComparing(i -> ((Card)i).getColor().VALUE));
        playableCards.addAll(wildCards);
        return playableCards;
    }

    /**
     * @param check
     * @return a List of {@link Card}, sorted from the most convenient to play to the less.
     */
    @Override
    public List<Card> getValidCards(Card check){ return chooseBestCards(check); }

    /**
     * This method has been designed to be smart enough to decide which {@link Color} is the most convenient to choose.
     * @return the most convenient {@link Color} for him
     */
    public Color chooseBestColor()
    {
        Map<Color, Long> colorWeights = colorWeights();
        colorWeights.remove(Color.WILD);

        Long max = colorWeights.isEmpty() ? 1L : Collections.max(colorWeights.values());
        Optional<Color> bestColor = colorWeights.keySet().stream().filter(color -> Objects.equals(colorWeights.get(color), max)).findFirst();
        return (bestColor.isEmpty()) ? randomColor():bestColor.get();
    }

    /**
     * This method has been designed to be smart enough to decide which {@link Player} is the most convenient to swap hand with.
     * @param players
     * @param current
     * @return the {@link Player} that is the most convenient to swap hand with.
     */
    public Player chooseBestPlayerToSwap(Player[] players, int current)
    {
        System.out.println("CURRENT " + players[current]);
        int min = players[0].getHand().size();
        Player bestPlayer = players[0];
        for (Player p : players){
            System.out.println("PLAYER " + p + " HAND SIZE " + p.getHand().size());
            if (p.getHand().size() < min && !p.equals(players[current])) bestPlayer = p;
        }
        System.out.println("BEST PLAYER " + bestPlayer);
        return bestPlayer;
    }

    /**
     * This method exists to make the Artificial Intelligence have the possibility to be wrong.
     * @return true if shouted Uno, false otherwise.
     */
    public boolean chooseToSayUno()
    {
        boolean choice = choiceFactor();
        if (choice) shoutUno();
        return choice;
    }

    /**
     * @return 20% false, 80%true
     */
    public boolean choiceFactor()
    {
        return new Random().nextInt(1,6)>1;
    }

    /**
     * @return a random {@link Color} except Wild
     */
    private Color randomColor(){ return Color.values()[new Random().nextInt(0,4)]; }
}
