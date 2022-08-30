package View.Elements;

import Model.Player.HumanPlayer;
import Model.Player.Player;

import java.util.ArrayList;

public class ViewPlayer {
    Player player;
    ArrayList<ViewAnimableCard> imagesHand;
    CircleImage profilePicture;

    public ViewPlayer(String name) { this(new HumanPlayer(name, "null"), new CircleImage()); }

    public ViewPlayer(Player player, CircleImage profilePicture)
    {
        this.player = player;
        this.profilePicture = profilePicture;
    }

    public ViewPlayer(Player player)
    {
        this.player = player;
        this.profilePicture = new CircleImage();
    }

    public void updatePlayer(Player player)
    {
        //magari si aggiorna da solo non server passarlo?
        this.player = player;
    }

    public void setImagesHand(ArrayList<ViewAnimableCard> imagesHand){ this.imagesHand = imagesHand; }

    public void setProfilePicture(CircleImage profilePicture) { this.profilePicture = profilePicture; }

    public Player getPlayer(){ return player; }

    public ArrayList<ViewAnimableCard> getImagesHand() { return imagesHand; }

    public CircleImage getProfilePicture() { return profilePicture; }
}
