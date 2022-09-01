package View.Elements;

import Model.Player.HumanPlayer;
import Model.Player.Player;

import java.util.ArrayList;

public class ViewPlayer
{

    ArrayList<ViewAnimableCard> imagesHand;
    ViewPlayerProfile playerProfile;

    public ViewPlayer(String name) { this(new HumanPlayer(name, "null"), new CircleImage()); }

    public ViewPlayer(Player player, CircleImage profilePicture)
    {
        playerProfile.player = player;
        playerProfile.profilePicture = profilePicture;
//        this.player = player;
//        this.profilePicture = profilePicture;
    }

    public ViewPlayer(Player player)
    {
        playerProfile.player = player;
        playerProfile.profilePicture = new CircleImage();
//        this.player = player;
//        this.profilePicture = new CircleImage();
    }

    public void updatePlayer(Player player)
    {
        //magari si aggiorna da solo non server passarlo?
        playerProfile.player = player;
//        this.player = player;
    }

    public void setImagesHand(ArrayList<ViewAnimableCard> imagesHand){ this.imagesHand = imagesHand; }

    public void setProfilePicture(CircleImage profilePicture) {
        playerProfile.profilePicture = profilePicture;
        //        this.profilePicture = profilePicture;
    }

    public Player getPlayer(){
        return playerProfile.player;
        //        return player;
    }

    public ArrayList<ViewAnimableCard> getImagesHand() { return imagesHand; }

    public CircleImage getProfilePicture() {
        return playerProfile.profilePicture;
//        return profilePicture;
    }
}
