package de.michiruf.scalor.capture.monitor;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLProfile;
import de.michiruf.scalor.config.Configuration;

import javax.inject.Inject;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.Observable;

/**
 * @author lukasz1985
 * @see <a href="http://gamedev.stackexchange.com/questions/72911/how-to-save-am-image-of-a-screen-using-jogl/72915#72915">Stackexchange</a>
 */
public class OpenGLMonitor implements Monitor {

    private final Configuration configuration;
    private Rectangle dimens;

    @Inject
    public OpenGLMonitor(Configuration configuration) {
        this.configuration = configuration;
        configuration.addObserver(this);
        updateDimens();
    }

    @Override
    public BufferedImage captureScreenWithBufferedImage() {
        // We do not need this kind here
        return null;
    }

    @Override
    public byte[] captureScreenWithByteArray() {
        // TODO HERE I AM
        GL gl = null;
        try {
            gl = (GL) GLProfile.getDefault().getGLCtor(false).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (gl == null) {
            return null;
        }

        int x = (int) dimens.getX();
        int y = (int) dimens.getY();
        int width = (int) dimens.getWidth();
        int height = (int) dimens.getHeight();

        ByteBuffer buffer = ByteBuffer.allocate(width * height * 3);
        gl.glReadPixels(x, y, width, height, GL.GL_RGB, GL.GL_BYTE, buffer);
        return buffer.array();
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
