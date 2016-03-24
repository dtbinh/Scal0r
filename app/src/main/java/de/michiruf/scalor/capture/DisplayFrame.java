package de.michiruf.scalor.capture;

import de.michiruf.scalor.config.Configuration;
import de.michiruf.scalor.helper.ScreenHelper;

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
    private Configuration configuration;

    @Inject
    public DisplayFrame(Configuration configuration) {
        this.configuration = configuration;
        imageLabel = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setAlwaysOnTop(true);
        setBackground(new Color(0, 0, 0, 0));
        setLocationRelativeTo(null);
        setContentPane(imageLabel);
    }

    public void rearrange() {
        ScreenHelper.showOnScreen(configuration.getOutputScreen() - 1, this);
        setBounds(configuration.getOutputX(), configuration.getOutputY(),
                configuration.getOutputWidth(), configuration.getOutputHeight());
    }

    public void draw(Image bufferedImage) {
        imageLabel.setIcon(new ImageIcon(bufferedImage));
    }
}
