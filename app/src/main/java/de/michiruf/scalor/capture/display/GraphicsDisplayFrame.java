package de.michiruf.scalor.capture.display;

import de.michiruf.scalor.config.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * @author Michael Ruf
 * @since 2016-03-23
 */
@Singleton
public class GraphicsDisplayFrame extends DisplayFrame {

    private Image currentFrameImage;

    @Inject
    public GraphicsDisplayFrame(Configuration configuration) {
        super(configuration);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // TODO stop capturing on close
        setUndecorated(true);
        setAlwaysOnTop(true);
        setBackground(new Color(0, 0, 0, 0));
        setLocationRelativeTo(null);

        updateBounds();
    }

    @Override
    public void draw(BufferedImage image) {
        currentFrameImage = image;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        //super.paint(g); // NOTE is this really good to skip this?

        if (currentFrameImage == null)
            return;

        g.drawImage(currentFrameImage, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
    }
}
