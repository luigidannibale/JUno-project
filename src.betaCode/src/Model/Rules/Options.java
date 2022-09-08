package Model.Rules;

import Model.Cards.Color;
import Model.DeckManager;
import Model.Players.Player;
import Model.TurnManager;

public class Options
{
    private final TurnManager turnManager;
    private final Player[] players;
    private final DeckManager deckManager;
    private final Color color;
    private final Player playerToSwapCards;
    private final int currentPlayer;
    private final int nextPlayer;
    private Options(OptionsBuilder builder)
    {
        turnManager = builder.turnManager;
        players = builder.players;
        deckManager = builder.deckManager;
        color = builder.color;
        playerToSwapCards = builder.playerToSwapCards;
        currentPlayer = builder.currentPlayer;
        nextPlayer = builder.nextPlayer;
    }
    public TurnManager getTurnManager() { return turnManager; }
    public Player[] getPlayers() { return players; }
    public DeckManager getDeck() { return deckManager; }
    public Color getColor() { return color; }
    public Player getPlayerToSwapCards() { return playerToSwapCards; }
    public int getCurrentPlayer() { return currentPlayer; }
    public int getNextPlayer() { return nextPlayer; }

    public static class OptionsBuilder
    {
        private TurnManager turnManager;
        private Player[] players;
        private DeckManager deckManager;
        private Color color;
        private Player playerToSwapCards;
        private int currentPlayer;
        private int nextPlayer;

        public OptionsBuilder(TurnManager turnManager, Player[] players, DeckManager deckManager)
        {
            this.turnManager = turnManager;
            this.players = players;
            this.deckManager = deckManager;
            this.currentPlayer = turnManager.getPlayer();
            this.nextPlayer = turnManager.next();
        }
        public OptionsBuilder turnManager(TurnManager turnManager)
        {
            this.turnManager = turnManager;
            return this;
        }
        public OptionsBuilder players(Player[] players)
        {
            this.players = players;
            return this;
        }
        public OptionsBuilder deck (DeckManager deckManager)
        {
            this.deckManager = deckManager;
            return this;
        }
        public OptionsBuilder color (Color color)
        {
            this.color = color;
            return this;
        }
        public OptionsBuilder playerToSwapCards(Player  playerToSwapCards)
        {
            this.playerToSwapCards = playerToSwapCards;
            return this;
        }
        public OptionsBuilder currentPlayer(int currentPlayer)
        {
            this.currentPlayer = currentPlayer;
            return this;
        }
        public OptionsBuilder nextPlayer(int nextPlayer)
        {
            this.nextPlayer = nextPlayer;
            return this;
        }
        public Options build()  { return new Options(this); }
    }
}
