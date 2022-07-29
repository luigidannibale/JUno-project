package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static View.ImageComponent.Formats.GIF;
import static View.ImageComponent.Formats.PNG;

public class ImageComponent extends JLabel
{
    public enum Formats{
        PNG(".png"),
        JPEG(".jpeg"),
        GIF(".gif");
        final String format;
        Formats(String format){this.format = format;}
        String getFormat(){return format;}
    }
    private ImageIcon icona;
    private String iconaPath;
    private boolean showgif = false;
    public ImageComponent(String imagePath)
    {
        iconaPath = imagePath;
        icona = new ImageIcon(imagePath);
        AddSclingOnHovering();
        setIcon(icona);
    }

    /*public ImageComponent(String imagePath,Formats format)
    {
        iconaPath = imagePath;
        icona = new ImageIcon(imagePath+format);
        AddSclingOnHovering();
        setIcon(icona);
        //setPreferredSize(new Dimension(350, 100));
    }*/
    public void setBiggerIcon()
    {
        scaleUpIcon();
    }
    public void setSmallerIcon()
    {
        setIcon(icona);
    }

    // not used, can be deleted
    public ImageIcon getImageIcon(){
        return icona;
    }

    private void scaleUpIcon()
    {
        Image im = ((ImageIcon) getIcon()).getImage();
        im = im.getScaledInstance(im.getWidth(this)+15, im.getHeight(this)+5, Image.SCALE_SMOOTH);
        super.setIcon(new ImageIcon(im));
    }
    private void AddSclingOnHovering(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
               setBiggerIcon();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setSmallerIcon();
            }});
    };

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

}
