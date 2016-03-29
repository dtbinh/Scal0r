package de.michiruf.scalor.capture.display;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.GLPixelBuffer;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
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
// TODO cleanup some stuff. Got 4gb ram!!!
@Singleton
public class OpenGLDisplayFrame extends DisplayFrame implements GLEventListener {

    private final GLCanvas canvas;
    private byte[] image;
    private TextureData textureData;
    private Texture texture;

    @Inject
    public OpenGLDisplayFrame(Configuration configuration) {
        super(configuration);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // TODO stop capturing on close
        setUndecorated(true);
        setAlwaysOnTop(true);
        setBackground(new Color(0, 0, 0, 0));
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        canvas = new GLCanvas() {{
            addGLEventListener(OpenGLDisplayFrame.this);
        }};
        add(canvas, BorderLayout.CENTER);

        updateBounds();
    }

    @Override
    public void draw(Object image) {
        if (image == null) {
            return;
        }

        if (image instanceof BufferedImage) {
            this.image = ImageDataHelper.getRGBData((BufferedImage) image);
            canvas.display();
        } else if (image instanceof byte[]) {
            this.image = (byte[]) image;
            canvas.display();
        }
    }

    @Override
    public void init(GLAutoDrawable drawable) {
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        drawable.getContext().makeCurrent();
        GL gl = drawable.getGL();

        if (texture == null || textureData == null) {
            textureData = new TextureData(
                    gl.getGLProfile(),
                    GL.GL_RGB,
                    getWidth(),
                    getHeight(),
                    0,
                    new GLPixelBuffer.GLPixelAttributes(GL.GL_RGB, GL.GL_UNSIGNED_BYTE),
                    false,
                    false,
                    false,
                    ByteBuffer.wrap(image),
                    null
            );

            texture = new Texture(gl, textureData);
            texture.setTexParameterf(gl, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
            texture.setTexParameterf(gl, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
            texture.setTexParameterf(gl, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
            texture.setTexParameterf(gl, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
            return;
        }

        textureData.setBuffer(ByteBuffer.wrap(image));
        texture.updateImage(gl, textureData);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }
}
