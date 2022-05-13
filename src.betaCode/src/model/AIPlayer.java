package model;

import model.Cards.Card;

import java.util.*;
import java.util.stream.Collectors;

public class AIPlayer extends Player{


    public AIPlayer(String name) {
        super(name);
    }

    @Override
    public void shoutUno() {}

    @Override
    public List<Card> getPlayableCards(Card check){
        //cosi le ordina bene ma mi pare esagerato
        Set<Card> playable = new LinkedHashSet<>();
        List<Card> play = super.getPlayableCards(check);
        List<Card> sameColor = play.stream().filter(card -> card.getColor() == check.getColor()).toList();
        List<Card> sameValue = play.stream().filter(card -> card.getValue() == check.getValue()).toList();
        playable.addAll(sameColor);
        playable.addAll(sameValue);
        playable.addAll(play);
        return playable.stream().toList();
        //cosi mette le carte con stesso numero per prime (?)
        //return super.getPlayableCards(check).stream().sorted(Comparator.comparing(Card::getColor).thenComparing(Card::getValue)).collect(Collectors.toList());
    }

}
