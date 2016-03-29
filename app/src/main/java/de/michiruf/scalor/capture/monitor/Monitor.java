package de.michiruf.scalor.capture.monitor;

import java.util.Observer;

/**
 * @author Michael Ruf
 * @since 2016-03-23
 */
public interface Monitor extends Observer {

    Object captureScreen();
}
