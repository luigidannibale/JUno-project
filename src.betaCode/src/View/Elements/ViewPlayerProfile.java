package View.Elements;

import Model.Player.HumanPlayer;
import Model.Player.Player;

public class ViewPlayerProfile
{
    Player player;
    //ArrayList<ViewAnimableCard> imagesHand;
    CircleImage profilePicture;

    public ViewPlayerProfile(String name) { this(new HumanPlayer(name, "null"), new CircleImage()); }

    public ViewPlayerProfile(Player player, CircleImage profilePicture)
    {
        this.player = player;
        this.profilePicture = profilePicture;
    }

    public ViewPlayerProfile(Player player)
    {
        this.player = player;
        this.profilePicture = new CircleImage();
    }

    public void updatePlayer(Player player)
    {
        //magari si aggiorna da solo non server passarlo?
        this.player = player;
    }


    public void setProfilePicture(CircleImage profilePicture) { this.profilePicture = profilePicture; }

    public Player getPlayer(){ return player; }

    public CircleImage getProfilePicture() { return profilePicture; }
}
