package View.Elements;

import Model.Players.Player;

import java.awt.*;
import java.util.ArrayList;

/**
 * Class used to provide the {@link Player} a graphic representation, with its profile picture {@link CircularImage} and its hand with {@link ViewAnimableCard}
 * @author Luigi D'Annibale, Daniele Venturini
 */
public class ViewPlayer
{
    private Player player;
    private ArrayList<ViewAnimableCard> imagesHand;
    private CircularImage profilePicture;
    private final Rectangle namePosition = new Rectangle();

    /**
     * Creates a new {@link ViewPlayer} with the given {@link Player} and {@link CircularImage}
     * @param player
     * @param profilePicture
     */
    public ViewPlayer(Player player, CircularImage profilePicture)
    {
        this.player = player;
        this.profilePicture = profilePicture;
    }

    /**
     * Creates a new {@link ViewPlayer} with the given {@link Player} and the default {@link CircularImage}
     * @param player
     */
    public ViewPlayer(Player player)
    {
        this.player = player;
        this.profilePicture = new CircularImage();
    }

    //SETTERS
    public void setImagesHand(ArrayList<ViewAnimableCard> imagesHand){ this.imagesHand = imagesHand; }
    public void setProfilePicture(CircularImage profilePicture) { this.profilePicture = profilePicture; }
    public void setNamePosition(int x, int y, int width, int height) { namePosition.setRect(x, y ,width, height); }

    //GETTERS
    public Player getPlayer(){ return player; }
    public ArrayList<ViewAnimableCard> getImagesHand() { return imagesHand; }
    public CircularImage getProfilePicture() { return profilePicture; }
    public Rectangle getNamePosition() { return namePosition; }




}
