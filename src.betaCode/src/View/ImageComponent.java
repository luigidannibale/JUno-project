package View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class ImageComponent extends JLabel
{
    protected ImageIcon icona;

    public ImageComponent(String imagePath)
    {
        this(imagePath,-1,-1,true);
    }

    public ImageComponent(String imagePath, int width, int height, boolean addDefaultScalingListener)
    {
        icona = (width > 0 && height > 0)?  ScaleImage(imagePath, width, height):
                                            new ImageIcon(imagePath);
        if (width < 0 && height < 0){
            width = icona.getIconWidth();
            height = icona.getIconHeight();
        }
        if(addDefaultScalingListener) AddScalingOnHovering();
        setPreferredSize(new Dimension(width, height));
        setIcon(icona);
    }

    public void setBiggerIcon() {   scaleUpIcon();}
    public void setSmallerIcon() {  setIcon(icona);}

    private void scaleUpIcon()
    {
        Image im = ((ImageIcon) getIcon()).getImage();
        im = im.getScaledInstance(im.getWidth(this)+15, im.getHeight(this)+5, Image.SCALE_DEFAULT);
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

    protected ImageIcon ScaleImage(String imagePath, int width, int height){
        return new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
    }

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
