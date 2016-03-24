package de.michiruf.scalor.capture;

import de.michiruf.scalor.config.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
        imageLabel = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setAlwaysOnTop(true);
        setBackground(new Color(0, 0, 0, 0));
        setLocationRelativeTo(null);
        setContentPane(imageLabel);

        updateBounds();
    }

    public void draw(Image bufferedImage) {
        imageLabel.setIcon(new ImageIcon(bufferedImage));
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
