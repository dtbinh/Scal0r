package de.michiruf.scalor.capture.monitor;

import com.jogamp.opengl.GL;
import de.michiruf.scalor.config.Configuration;
import de.michiruf.scalor.helper.OpenGLContextHandler;

import javax.inject.Inject;
import java.awt.Rectangle;
import java.nio.ByteBuffer;
import java.util.Observable;

/**
 * @author Michael Ruf
 * @since 2016-02-29
 */
public class OpenGLMonitor implements Monitor {

    private final OpenGLContextHandler contextHandler;
    private final Configuration configuration;
    private Rectangle dimens;

    @Inject
    public OpenGLMonitor(Configuration configuration) {
        contextHandler = new OpenGLContextHandler();
        this.configuration = configuration;
        configuration.addObserver(this);
        updateDimens();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public byte[] captureScreen() {
        final byte[][] data = {null};
        contextHandler.invoke(() -> {
            GL gl = contextHandler.getContext().getGL();

            int x = (int) dimens.getX();
            int y = (int) dimens.getY();
            int width = (int) dimens.getWidth();
            int height = (int) dimens.getHeight();

            ByteBuffer buffer = ByteBuffer.allocate(width * height * 3);
            // TODO glReadPixel currently only reads pixels within the application!
            gl.glReadPixels(x, y, width, height, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, buffer);
            data[0] = buffer.array();
        });
        // TODO better waiting mechanism
        while (data[0] == null) {
            // Just wait for it
        }
        return data[0];
    }

    @Override
    public void update(Observable o, Object arg) {
        updateDimens();
    }

    private void updateDimens() {
        dimens = new Rectangle(configuration.getScanX(), configuration.getScanY(),
                configuration.getScanWidth(), configuration.getScanHeight());
    }
}
