package de.michiruf.scalor.capture;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Image;

/**
 * @author Michael Ruf
 * @since 2016-03-23
 */
@Singleton
public class DisplayFrame extends JFrame {

    private JLabel imageLabel;

    @Inject
    public DisplayFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setAlwaysOnTop(true);
        setBackground(new Color(0, 0, 0, 0));
        setLocationRelativeTo(null);
//        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setBounds(0, 0, 200, 200); // TODO remove

        imageLabel = new JLabel();
//        imageLabel.setBounds(getX(), -getHeight() / 4, getWidth(), getHeight() * 2);
        setContentPane(imageLabel);

        setVisible(true);
    }

    public void draw(Image bufferedImage) {
        imageLabel.setIcon(new ImageIcon(bufferedImage));
    }
}
