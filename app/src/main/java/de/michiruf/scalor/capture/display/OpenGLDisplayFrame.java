package de.michiruf.scalor.capture.display;

import com.jogamp.opengl.DebugGL4;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import de.michiruf.scalor.config.Configuration;
import de.michiruf.scalor.helper.ImageDataHelper;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

/**
 * @author Michael Ruf
 * @since 2016-03-28
 */
@Singleton
public class OpenGLDisplayFrame extends DisplayFrame {

    private final MyGLCanvas canvas;
    private boolean isFirstImage = true;
    private boolean gotByteImage = false;

    @Inject
    public OpenGLDisplayFrame(Configuration configuration) {
        super(configuration);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // TODO stop capturing on close
        setUndecorated(true);
        setAlwaysOnTop(true);
        setBackground(new Color(0, 0, 0, 0));
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        canvas = new MyGLCanvas();
        add(canvas, BorderLayout.CENTER);

        updateBounds();
    }

    @Override
    public void draw(BufferedImage image) {
        if (isFirstImage) {
            isFirstImage = false;
            return;
        }
        if (gotByteImage || image == null) {
            return;
        }
        canvas.draw(ImageDataHelper.getImageByteData(image));
    }

    @Override
    public void draw(byte[] image) {
        if (image == null) {
            return;
        }
        gotByteImage = true;
        canvas.draw(image);
    }

    private static class MyGLCanvas extends GLCanvas implements GLEventListener {

        private byte[] image;

        public MyGLCanvas() {
            super();
            addGLEventListener(this);
        }

        @Override
        public void init(GLAutoDrawable drawable) {
        }

        @Override
        public void dispose(GLAutoDrawable drawable) {
        }

        @Override
        public void display(GLAutoDrawable drawable) {
            // TODO we could use OpenGL here to scale also: new GLU().gluScaleImage();

            // remove this
            GL4 gl4 = drawable.getGL().getGL4();
            drawable.setGL(new DebugGL4(gl4));
            // -----

            GL gl = drawable.getGL();
            gl.glClear(GL.GL_COLOR_BUFFER_BIT); // TODO need this?
            gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
            gl.glEnable(GL.GL_BLEND);
            gl.glTexImage2D(
                    GL.GL_TEXTURE_2D,
                    0,                  // Detail level
                    GL.GL_RGB,
                    getWidth(),
                    getHeight(),
                    0,                  // Border (must be zero)
                    GL.GL_RGB,          // Format
                    GL.GL_BYTE,         // Data type
                    ByteBuffer.wrap(image)
            );
            gl.glDisable(GL.GL_BLEND);
        }

        @Override
        public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
            setBounds(0, 0, width, height);
        }

        private void draw(byte[] image) {
            this.image = image;
            display();
        }
    }
}
