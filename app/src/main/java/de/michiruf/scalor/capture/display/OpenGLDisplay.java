package de.michiruf.scalor.capture.display;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;
import de.michiruf.scalor.config.Configuration;
import de.michiruf.scalor.helper.ImageCompat;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

/**
 * @author Michael Ruf
 * @since 2016-03-28
 */
@Singleton
public class OpenGLDisplay extends DisplayFrame {

    private BufferedImage currentFrameImage;

    @Inject
    public OpenGLDisplay(Configuration configuration) {
        super(configuration);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // TODO stop capturing on close
        setUndecorated(true);
        setAlwaysOnTop(true);
        setBackground(new Color(0, 0, 0, 0));
        setLocationRelativeTo(null);

        updateBounds();

        setContentPane(new MyGLJPanel());
    }

    @Override
    public void draw(Image image) {
        currentFrameImage = (BufferedImage) image;
    }

    private class MyGLJPanel extends GLJPanel implements GLEventListener {

        private FPSAnimator animator;

        public MyGLJPanel() {
            addGLEventListener(this);
        }

        @Override
        public void init(GLAutoDrawable drawable) {
            GL gl = drawable.getGL();

            // Global settings.
            gl.glEnable(GL.GL_DEPTH_TEST);
            gl.glDepthFunc(GL.GL_LEQUAL);
            gl.glClearColor(0f, 0f, 0f, 1f);

            // Start animator (which should be a field).
            animator = new FPSAnimator(this, 60);
            animator.start();
        }

        @Override
        public void dispose(GLAutoDrawable drawable) {

        }

        @Override
        public void display(GLAutoDrawable drawable) {
            GL gl = drawable.getGL();

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
                    ByteBuffer.wrap(ImageCompat.getBufferedImageData(currentFrameImage))
            );
            gl.glDisable(GL.GL_BLEND);
        }

        @Override
        public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
            setBounds(0, 0, width, height);
        }
    }
}
