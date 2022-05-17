package model;

public class UnoGameTable {

    private final UnoGame tipo;

    public UnoGameTable(UnoGame tipo)
    {
        this.tipo = tipo;
    }

    public void startGame()
    {
        tipo.startGame(7);
        /*
        deck.shuffle();
        //distribuzione carte
        IntStream.range(0, 7).forEach(i -> Arrays.stream(players).forEach(p -> p.drawCard(deck.draw())));

        while(deck.getDeck().peek().getValue() == CardValue.WILD_DRAW){ deck.shuffle(); }
        discards.push(deck.draw());

        switch (discards.peek().getValue()){
            case REVERSE -> {}
            case DRAW -> {}
            case SKIP -> {}
            case WILD -> {}
            default -> turnManager = new TurnManager(discards.peek());
            //if discard discard.peek == draw -> il primo pesca due carte
            //if discard discard.peek == reverse -> il primo gioca e poi cambia giro
            //if discard discard.peek == skip -> il primo viene skippato
            //if discard discard.peek == wild -> il primo sceglie il colore
            //if discard discard.peek == wild_four -> carta rimessa nel deck e se ne prende un'altra
        }

        //debug
        System.out.println(discards.peek());
        for (Player p : players) {
            System.out.println(p.getName() + " " + p.getHand() + "\n-playable: " + p.getPlayableCards(discards.peek()));
            System.out.println();
        }
        */

    }


    /*
    public void getPlayableCards(){

        List<Card> playableCards = players[turnManager.getPlayer()].getPlayableCards(turnManager.getCard());

        if (playableCards.size() == 0) {
            Card drawed = deck.draw();
            players[turnManager.getPlayer()].drawCard(drawed);
            if (!drawed.isPlayable(turnManager.getCard())) turnManager.skipTurn();
        }

        turnManager.skipTurn();
    }
    */

    /*
    public void reverse(){
        Collections.reverse(Arrays.asList(players));
        turnManager.setPlayer(Math.abs(turnManager.getPlayer() - (players.length - 1)));
    }

    */
}
