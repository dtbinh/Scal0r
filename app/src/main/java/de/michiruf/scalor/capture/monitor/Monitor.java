package de.michiruf.scalor.capture.monitor;

import java.awt.image.BufferedImage;
import java.util.Observer;

/**
 * @author Michael Ruf
 * @since 2016-03-23
 */
public interface Monitor extends Observer {

    BufferedImage captureScreen();
}
