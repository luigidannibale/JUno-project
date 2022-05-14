package model.Interfaces;

import model.Player.Player;

public class GameTurnNode{

    GameTurnNode preNode,nextNode;
    Player player;

    public GameTurnNode nextPlayer(boolean turnFlow) { return turnFlow ? nextNode : preNode; }

}
