package de.michiruf.scalor.helper;

import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * @author Michael Ruf
 * @since 2016-03-28
 */
public class ImageResizeHelper {

    // TODO make scaling configurable
    private Image resizeImage(BufferedImage bufferedImage) {
        if (bufferedImage == null) // Fail safe
            return null;

        // TODO we could use OpenGL here: new GLU().gluScaleImage();

        return ImageCompat.toCompatibleImage(bufferedImage)
                .getScaledInstance(bufferedImage.getWidth(), bufferedImage.getHeight() * 2,
                        Image.SCALE_FAST);
    }
}
