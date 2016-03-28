package de.michiruf.scalor.capture.display;

import de.michiruf.scalor.config.Configuration;

import javax.swing.JFrame;
import java.util.Observable;

/**
 * @author Michael Ruf
 * @since 2016-03-28
 */
public abstract class DisplayFrame extends JFrame implements Display {

    protected final Configuration configuration;

    public DisplayFrame(Configuration configuration) {
        this.configuration = configuration;
        configuration.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        updateBounds();
    }

    protected void updateBounds() {
        setBounds(configuration.getOutputX(), configuration.getOutputY(),
                configuration.getOutputWidth(), configuration.getOutputHeight());
    }
}
