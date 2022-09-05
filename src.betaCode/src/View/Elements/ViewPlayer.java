package View.Elements;

import Model.Player.HumanPlayer;
import Model.Player.Player;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.ArrayList;

public class ViewPlayer
{
    private Player player;
    private ArrayList<ViewAnimableCard> imagesHand;
    private Rectangle namePosition;
    private CircleImage profilePicture;

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
        this.player = player;
    }
    public void reset()
    {

    }

    public void setProfilePicture(CircleImage profilePicture) { this.profilePicture = profilePicture; }

    public Player getPlayer(){ return player; }

    public CircleImage getProfilePicture() { return profilePicture; }

    public void setImagesHand(ArrayList<ViewAnimableCard> imagesHand){ this.imagesHand = imagesHand; }

    public ArrayList<ViewAnimableCard> getImagesHand() { return imagesHand; }
}
