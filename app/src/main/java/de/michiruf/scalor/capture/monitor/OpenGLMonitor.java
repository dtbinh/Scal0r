package de.michiruf.scalor.capture.monitor;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.opengl.GLProfile;
import de.michiruf.scalor.config.Configuration;
import jogamp.opengl.awt.Java2D;

import javax.inject.Inject;
import java.awt.Rectangle;
import java.nio.ByteBuffer;
import java.util.Observable;

/**
 * @author lukasz1985
 * @see <a href="http://gamedev.stackexchange.com/questions/72911/how-to-save-am-image-of-a-screen-using-jogl/72915#72915">Stackexchange</a>
 */
public class OpenGLMonitor implements Monitor {

    private ContextHandler contextHandler;
    private final Configuration configuration;
    private Rectangle dimens;

    @Inject
    public OpenGLMonitor(Configuration configuration) {
        contextHandler = new ContextHandler();
        this.configuration = configuration;
        configuration.addObserver(this);
        updateDimens();
    }

    @Override
    public byte[] captureScreen() {
        return captureScreenImpl();

//
//        if (!contextCreator.isContextAvailable()) {
//            return null;
//        }
//
//        contextCreator.makeCurrent();
//        GL gl = contextCreator.getContext().getGL();
//
//        // TODO remove this
//        contextCreator.getContext().setGL(new DebugGL4(contextCreator.getContext().getGL().getGL4()));
//        gl = contextCreator.getContext().getGL();
//        // -----
//
//        int x = (int) dimens.getX();
//        int y = (int) dimens.getY();
//        int width = (int) dimens.getWidth();
//        int height = (int) dimens.getHeight();
//
//        ByteBuffer buffer = ByteBuffer.allocate(width * height * 3);
//        gl.glReadPixels(x, y, width, height, GL.GL_RGB, GL.GL_BYTE, buffer);
//        return buffer.array();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    private byte[] captureScreenImpl() {
        final byte[][] data = {null};
        Java2D.invokeWithOGLContextCurrent(null, () -> {
            GLDrawableFactory factory = GLDrawableFactory.getFactory(GLProfile.getDefault());
            GLContext context = factory.createExternalGLContext();
            context.makeCurrent();
            GL gl = context.getGL();

            int x = (int) dimens.getX();
            int y = (int) dimens.getY();
            int width = (int) dimens.getWidth();
            int height = (int) dimens.getHeight();

            ByteBuffer buffer = ByteBuffer.allocate(width * height * 3);
            gl.glReadPixels(x, y, width, height, GL.GL_RGB, GL.GL_BYTE, buffer);
            data[0] = buffer.array();
        });
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

    private static class ContextHandler {

        private GLContext context;

        public ContextHandler() {
            this(GLProfile.getDefault());
        }

        public ContextHandler(GLProfile profile) {
            Java2D.invokeWithOGLContextCurrent(null, () -> {
                GLDrawableFactory factory = GLDrawableFactory.getFactory(profile);
                context = factory.createExternalGLContext();
            });
        }

        public boolean isContextAvailable() {
            return context != null;
        }

        public GLContext getContext() {
            return context;
        }

        public void makeCurrent() {
            context.makeCurrent();
        }
    }
}
