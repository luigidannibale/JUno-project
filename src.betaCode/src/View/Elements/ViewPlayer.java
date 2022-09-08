package View.Elements;

import Model.Player.HumanPlayer;
import Model.Player.Player;

import java.awt.*;
import java.util.ArrayList;

public class ViewPlayer
{
    private Player player;
    private ArrayList<ViewAnimableCard> imagesHand;
    private CircularImage profilePicture;

    private final Rectangle namePosition = new Rectangle();

    public ViewPlayer(String name) { this(new HumanPlayer(name, "null"), new CircularImage()); }

    public ViewPlayer(Player player, CircularImage profilePicture)
    {
        this.player = player;
        this.profilePicture = profilePicture;
    }

    public ViewPlayer(Player player)
    {
        this.player = player;
        this.profilePicture = new CircularImage();
    }

    public void updatePlayer(Player player)
    {
        this.player = player;
    }

    public void setNamePosition(int x, int y, int width, int height) { namePosition.setRect(x, y ,width, height); }

    public Rectangle getNamePosition() { return namePosition; }

    public void setProfilePicture(CircularImage profilePicture) { this.profilePicture = profilePicture; }

    public Player getPlayer(){ return player; }

    public CircularImage getProfilePicture() { return profilePicture; }

    public void setImagesHand(ArrayList<ViewAnimableCard> imagesHand){ this.imagesHand = imagesHand; }

    public ArrayList<ViewAnimableCard> getImagesHand() { return imagesHand; }
}
