package Model.Rules;

import Model.Cards.*;
import Model.Deck;
import Model.Cards.CardValue;
import Model.Cards.SkipAction;
import Model.Cards.WildAction;
import Model.Player.Player;
import Model.TurnManager;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class MemeRules extends UnoGameRules
{
    private boolean isStacking;
    private int stackedCardsToDraw;
    private int playersToBlock;
    private int cardsPlayed;
    private ActionPerformResult lastAction;

    public MemeRules()
    {
        cardsDistribution = Deck.classicRules;
        cardsDistribution.putAll(new HashMap<>(){{
            put(CardValue.SKIP,4);
            put(CardValue.DRAW,4);
            put(CardValue.REVERSE,4);
            put(CardValue.WILD,4);
            put(CardValue.WILD_DRAW,8);
        }});
        stackableCards = true;
        numberOfPlayableCards = 3;
        numberOfCardsPerPlayer = 11;
    }

    @Override
    public List<Card> getPlayableCards(List<Card> playerPlayableHand, Card discardsPick)
    {
        if (isStacking){
            playerPlayableHand = filtraValore(playerPlayableHand.stream(), discardsPick).toList();
        }
        //if (discardsPick instanceof SkipAction || discardsPick instanceof WildAction )
        //    playerPlayableHand = playerPlayableHand.stream().filter(card -> card.getValue() == discardsPick.getValue()).toList();
        return playerPlayableHand;
    }

    private List<Card> getStackableCards(List<Card> playerPlayableHand, Card discardsPick)
    { return filtraValore(playerPlayableHand.stream(), discardsPick).toList(); }

    private Stream<Card> filtraSkipAndDraw(Stream<Card> stream){
        return stream.filter(card -> card instanceof DrawCard || card instanceof SkipCard);
    }

    private Stream<Card> filtraValore(Stream<Card> stream, Card card){
        return stream.filter(card.isValueValid);
    }

    private Stream<Card> filtraNonWild(Stream<Card> stream){
        return stream.filter(c -> !c.isWild.test(c));
    }

    @Override
    public ActionPerformResult cardActionPerformance(Options parameters)
    {
        TurnManager turnManager = parameters.getTurnManager();
        Player[] players = parameters.getPlayers();
        Player currentPlayer = players[turnManager.getPlayer()];
        Card lastCardPlayed = turnManager.getLastCardPlayed();

        ActionPerformResult actionPerformResult = ActionPerformResult.SUCCESSFUL;

        if (lastCardPlayed instanceof ReverseCard) _ReverseAction(turnManager);
        else if(lastCardPlayed instanceof SkipCard){
            playersToBlock += 1;
            System.out.println("PLAYERS TO BLOCK " + playersToBlock);
        }
        else if(lastCardPlayed instanceof DrawCard drawCard && lastAction != ActionPerformResult.NO_COLOR_PROVIDED)
        {
            stackedCardsToDraw += drawCard.getNumberOfCardsToDraw();
            System.out.println("CARTE DA PESCARE " + stackedCardsToDraw);
            drawCard.setNumberOfCardsToDraw(stackedCardsToDraw);
            turnManager.updateLastCardPlayed(drawCard);
        }


        var playable = filtraNonWild(filtraValore(currentPlayer.getHand().stream(), lastCardPlayed)).toList();
        System.out.println("STACKABILI " + playable + " rimanenti da giocare " + (numberOfPlayableCards - cardsPlayed - 1));
        if (cardsPlayed != numberOfPlayableCards - 1 && playable.size() > 0){
            System.out.println("CI SONO E NE ASPETTA ALTRE");
            isStacking = true;
            cardsPlayed += 1;
            return ActionPerformResult.SUCCESSFUL;
        }
        else {
            System.out.println("NON CI SONO E CONTROLLA IL PROSSIMO");
            if (lastCardPlayed instanceof SkipCard || lastCardPlayed instanceof DrawCard){
                playable = filtraValore(players[turnManager.next()].getHand().stream(), lastCardPlayed).toList();
                System.out.println("PROSSIMO STACKABILI " + playable);
                if (playable.size() > 0){
                    System.out.println("CI SONO DEVE RIBATTERE");
                    isStacking = true;
                    cardsPlayed = 0;
                    turnManager.passTurn();
                    return ActionPerformResult.SUCCESSFUL;
                }
            }
            else System.out.println("IL PROSSIMO NON DEVE RIBATTERE");
        }

        System.out.println("NON CI SONO NE' NEL CURRENT NE' NEL PROSSIMO -> FAI L'AZIONE");
        while(playersToBlock > 1){
            _SkipAction(turnManager, players);
            playersToBlock --;
        }
        if (!(lastCardPlayed instanceof ReverseCard)) actionPerformResult = super.cardActionPerformance(parameters);
        if (actionPerformResult == ActionPerformResult.SUCCESSFUL)
        {
            System.out.println("RESETTA I DATI");
            turnManager.passTurn();
            isStacking = false;
            stackedCardsToDraw = 0;
            playersToBlock = 0;
            cardsPlayed = 0;
        }
        lastAction = actionPerformResult;
        return actionPerformResult;
    }

    //@Override
    public void oldCardActionPerformance(TurnManager turnManager, Player[] players, Deck deck)
    {   /*
        Card lastCard = turnManager.getLastCardPlayed();
        if (lastCard instanceof WildAction && lastCard.getColor() == CardColor.WILD &&
                players[turnManager.next()].getHand().stream().anyMatch(card -> card.getValue()==CardValue.WILD_DRAW)) {
            //serve per forza lo switch
            CardColor c = ((WildAction) lastCard).changeColor();
            turnManager.updateLastCardPlayed(CardBuilder.createFlowCard(c, CardValue.WILD));
        }
        if(lastCard instanceof DrawCard)
            players[turnManager.next()].drawCards(deck.draw(((DrawCard) lastCard).getNumberOfCardsToDraw()));
        else if(lastCard instanceof ReverseCard)
            ((ReverseCard) lastCard).reverse(turnManager, players);
        if(lastCard instanceof SkipAction)
            ((SkipAction) lastCard).skipTurn(turnManager);

        switch (lastCard.getValue()){
            case REVERSE -> ((ReverseCard) lastCard).reverse(turnManager,players); //if discard discard.peek == reverse -> il primo gioca e poi cambia giro
            case DRAW -> {//if discard discard.peek == draw -> il primo pesca due carte
                players[turnManager.getPlayer()].drawCards(deck.draw(2));
                ((DrawCard) lastCard).skipTurn(turnManager);
            }
            case SKIP -> ((SkipCard) lastCard).skipTurn(turnManager); //if discard discard.peek == skip -> il primo viene skippato
            case WILD -> {//if discard discard.peek == wild -> il primo sceglie il colore
                CardColor c = ((WildCard) lastCard).changeColor();
                turnManager.updateLastCardPlayed(CardValue.WILD,c);
            }
            case WILD_DRAW -> {
                players[turnManager.getPlayer()].drawCards(deck.draw(4));
                ((DrawCard) lastCard).skipTurn(turnManager);
                CardColor c = ((DrawCard) lastCard).changeColor();
                turnManager.updateLastCardPlayed(CardValue.WILD,c);
            }
            default -> {}
        }*/
    }
}
