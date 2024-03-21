package synthesizer;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import javax.swing.ImageIcon;

public class ImagePanel extends JPanel {

    private Image img;

    public ImagePanel () {
        // Load the image
        ImageIcon imgIcon = new ImageIcon(getClass().getResource("/PianoBG.jpg"));
        this.img = imgIcon.getImage();
        if (imgIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.out.println("Failed to load image");
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);
    }
}
