package Model.Player;

import java.util.HashMap;

public class mainDebug
{

    public static void main(String[] args)
    {
        HumanPlayer p = new HumanPlayer("luigi","password");
        HashMap<String,Object> profile = (HashMap) PlayerManager.findPlayerByNickname(p.getName());
        assert (profile != null): "nickname non esistente";
        assert (PlayerManager.checkPassword(profile,p.getPassword())) : "password non valida";

    }
}
