package de.michiruf.scalor.helper;

import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.opengl.GLProfile;
import jogamp.opengl.awt.Java2D;

public class OpenGLContextHandler {

    private GLContext context;

    public OpenGLContextHandler() {
        this(GLProfile.getDefault());
    }

    public OpenGLContextHandler(GLProfile profile) {
        Java2D.invokeWithOGLContextCurrent(null, () -> {
            GLDrawableFactory factory = GLDrawableFactory.getFactory(profile);
            context = factory.createExternalGLContext();
        });
    }

    public GLContext getContext() {
        return context;
    }

    public void makeCurrent() {
        context.makeCurrent();
    }

    public void invoke(Runnable r) {
        Java2D.invokeWithOGLContextCurrent(null, () -> {
            // makeCurrent() could be outside of the invoke
            makeCurrent();
            r.run();
        });
    }
}
