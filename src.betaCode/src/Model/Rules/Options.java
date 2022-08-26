package Model.Rules;

import Model.Cards.CardColor;
import Model.Deck;
import Model.Player.Player;
import Model.TurnManager;

public class Options
{
    private final TurnManager turnManager;
    private final Player[] players;
    private final Deck deck;
    private final CardColor color;
    private final int playerToSwapCards;

    private Options(OptionsBuilder builder)
    {
        turnManager = builder.turnManager;
        players = builder.players;
        deck = builder.deck;
        color = builder.color;
        playerToSwapCards = builder.playerToSwapCards;
    }

    public static class OptionsBuilder
    {
        private TurnManager turnManager;
        private Player[] players;
        private Deck deck;
        private CardColor color;
        private int playerToSwapCards;
        public OptionsBuilder(TurnManager turnManager, Player[] players, Deck deck)
        {
            this.turnManager = turnManager;
            this.players = players;
            this.deck = deck;
        }
        public OptionsBuilder turnManager(TurnManager turnManager1)
        {
            this.turnManager = turnManager;
            return this;
        }
        public OptionsBuilder players(Player[] players)
        {
            this.players = players;
            return this;
        }
        public OptionsBuilder deck (Deck  deck)
        {
            this.deck = deck;
            return this;
        }
        public OptionsBuilder color (CardColor  color)
        {
            this.color = color;
            return this;
        }
        public OptionsBuilder playerToSwapCards(int  playerToSwapCards)
        {
            this.playerToSwapCards = playerToSwapCards;
            return this;
        }
        public Options build()
        {
            return new Options(this);
        }
    }



    public TurnManager getTurnManager() {
        return turnManager;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Deck getDeck() {
        return deck;
    }

    public CardColor getColor() {
        return color;
    }

    public int getPlayerToSwapCards() {
        return playerToSwapCards;
    }



}
