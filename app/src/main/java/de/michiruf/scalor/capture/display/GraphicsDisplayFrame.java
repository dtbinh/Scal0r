package de.michiruf.scalor.capture.display;

import de.michiruf.scalor.config.Configuration;
import de.michiruf.scalor.helper.ImageCompat;
import de.michiruf.scalor.helper.ImageDataHelper;

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

    private BufferedImage currentFrameImage;

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

    public void draw(Object image) {
        if (image == null) {
            return;
        }

        if (image instanceof BufferedImage) {
            currentFrameImage = (BufferedImage) image;
        } else if (image instanceof byte[]) {
            currentFrameImage = ImageDataHelper.getBufferedImage(getWidth(), getHeight(), (byte[]) image);
        }
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        //super.paint(g); // NOTE is this really good to skip this?
        if (currentFrameImage == null)
            return;

        g.drawImage(ImageCompat.toCompatibleImage(currentFrameImage)
                        .getScaledInstance(currentFrameImage.getWidth(), currentFrameImage.getHeight() * 2,
                                Image.SCALE_FAST),
                0, 0, getWidth(), getHeight(), null);
        g.dispose();
    }
}
