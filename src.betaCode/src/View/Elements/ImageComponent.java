package View.Elements;

import Utilities.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ImageComponent extends JLabel
{
    protected ImageIcon icona;

    private int scaleX = 15;
    private int scaleY = 5;
    //protected double scalingPercentage = Config.scalingPercentage;

    public ImageComponent(String imagePath)
    {
        this(imagePath,-1,-1,true);
    }

    public ImageComponent(String imagePath, int width, int height, boolean addDefaultScalingListener)
    {
        icona = new ImageIcon(imagePath);
        if (width <= 0 && height <= 0)
        {
            width = icona.getIconWidth();
            height = icona.getIconHeight();
        }
        if (height == 0 || width == 0)
            System.out.println(imagePath);

        setPreferredSize(new Dimension(width,height));
        //icona = ScaleImage(icona, width, height);

        if(addDefaultScalingListener) AddScalingOnHovering();
        //setIcon(icona);
    }

    public void setBiggerIcon() {   scaleUpIcon();}
    public void setSmallerIcon() {  setIcon(icona);}

    private void scaleUpIcon()
    {
        Image im = ((ImageIcon) getIcon()).getImage();
        int width = (int) (im.getWidth(this) + scaleX * Config.scalingPercentage);
        int height = (int) (im.getHeight(this) + scaleY * Config.scalingPercentage);
        im = im.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        super.setIcon(new ImageIcon(im));
    }

    public void AddScalingOnHovering(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
               //setBiggerIcon();
                scaleUpIcon();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                //setSmallerIcon();
                setIcon(icona);
            }});
    };

    @Override
    public void setPreferredSize(Dimension preferredSize)
    {
        super.setPreferredSize(preferredSize);
        //System.out.println(preferredSize);

        icona = ScaleImage(icona,preferredSize.width,preferredSize.height);
        setIcon(icona);
        repaint();
    }

    protected ImageIcon ScaleImage(ImageIcon image, int width, int height){
        return new ImageIcon(image.getImage().getScaledInstance(getPreferredSize().width, getPreferredSize().height, Image.SCALE_DEFAULT));
//        return new ImageIcon(image.getImage().getScaledInstance((int) (width * Config.scalingPercentage), (int) (height * Config.scalingPercentage), Image.SCALE_DEFAULT));
    }

}
//nouse code

    /*public void AddSclingOnHoveringGif(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                showGif();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setIcon(icona);
                showgif = false;
            }});
    };
    private void showGif()
    {
        showgif = true;
        repaint();
    }*/
    /*@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = 100;
        int height= 100;
        g.drawImage(icona.getImage(), 0, 0, width, height, this);
        if(showgif)
            g.drawImage(new ImageIcon(iconaPath+GIF).getImage(), 0, 0, width, height, this);
    }*/
    /*public void resetIcon()
    {
        setIcon(getImageIcon());
    }
    public void setIcon()
    {
        setIcon(getImageIcon());
    }*/

