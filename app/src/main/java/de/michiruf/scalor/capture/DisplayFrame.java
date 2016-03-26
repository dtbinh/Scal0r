package de.michiruf.scalor.capture;

import de.michiruf.scalor.config.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Image;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Michael Ruf
 * @since 2016-03-23
 */
@Singleton
public class DisplayFrame extends JFrame implements Observer {

    private JLabel imageLabel;
    private Configuration configuration;

    @Inject
    public DisplayFrame(Configuration configuration) {
        this.configuration = configuration;
        configuration.addObserver(this);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // TODO stop capturing on close
        setUndecorated(true);
        setAlwaysOnTop(true);
        setBackground(new Color(0, 0, 0, 0));
        setLocationRelativeTo(null);

        imageLabel = new JLabel();
        setContentPane(imageLabel);

        updateBounds();
    }

    public void draw(Image bufferedImage) {
        // TODO Maybe improve this by just update the data instead of the ImageIcon
        // TODO Maybe do not use a JFrame and a Label to draw the image

        // SwingUtilities should give more performance
        SwingUtilities.invokeLater(() -> {
            imageLabel.setIcon(new ImageIcon(bufferedImage));
            imageLabel.revalidate();
            bufferedImage.flush(); // TODO is this good?
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        updateBounds();
    }

    private void updateBounds() {
        setBounds(configuration.getOutputX(), configuration.getOutputY(),
                configuration.getOutputWidth(), configuration.getOutputHeight());
    }
}
