package model.Interfaces;

import model.Enumerations.CardColor;

import java.util.Random;

public interface WildAction {
    default CardColor changeColor() { return CardColor.values()[new Random().nextInt(4)]; }
}
