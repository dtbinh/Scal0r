package de.michiruf.scalor.capture.display;

import de.michiruf.scalor.config.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Image;

/**
 * @author Michael Ruf
 * @since 2016-03-23
 */
@Singleton
public class ImageIconDisplayFrame extends DisplayFrame {

    private JLabel imageLabel;

    @Inject
    public ImageIconDisplayFrame(Configuration configuration) {
        super(configuration);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // TODO stop capturing on close
        setUndecorated(true);
        setAlwaysOnTop(true);
        setBackground(new Color(0, 0, 0, 0));
        setLocationRelativeTo(null);

        imageLabel = new JLabel();
        setContentPane(imageLabel);

        updateBounds();
    }

    @Override
    public void draw(Image image) {
        // SwingUtilities should give more performance
        SwingUtilities.invokeLater(() -> {
            imageLabel.setIcon(new ImageIcon(image));
            imageLabel.revalidate();
            image.flush(); // NOTE is this good?
        });
    }
}
