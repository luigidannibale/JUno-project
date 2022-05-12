package model;

import model.Cards.Card;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AIPlayer extends Player{


    public AIPlayer(String name) {
        super(name);
    }

    @Override
    public void shoutUno() {}

    @Override
    public List<Card> getPlayableCards(Card check){
        //cosi mette le carte con stesso numero per prime (?)
        return super.getPlayableCards(check).stream().sorted(Comparator.comparing(Card::getColor).thenComparing(Card::getValue)).collect(Collectors.toList());
        //Comparator<Card> comp = Comparator.<Card>naturalOrder();
        //return super.getPlayableCards(check).stream().sorted(comp).collect(Collectors.toList());
    }

}
