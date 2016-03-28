package de.michiruf.scalor.capture.display;

import java.awt.Image;
import java.awt.event.KeyListener;
import java.util.Observer;

/**
 * @author Michael Ruf
 * @since 2016-03-28
 */
public interface Display extends Observer {

    void draw(Image image);

    void setVisible(boolean visible);

    boolean isVisible();

    void addKeyListener(KeyListener keyListener);
}
