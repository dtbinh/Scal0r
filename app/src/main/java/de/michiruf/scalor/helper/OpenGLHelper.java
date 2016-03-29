package de.michiruf.scalor.helper;

import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLProfile;

/**
 * @author Michael Ruf
 * @since 2016-03-28
 */
public class OpenGLHelper {

    // TODO Is this good?
    public static boolean isOpenGLSupported() {
        try {
            GLDrawableFactory factory = GLDrawableFactory.getFactory(GLProfile.getDefault());
            return factory.canCreateGLPbuffer(factory.getDefaultDevice(), GLProfile.getDefault());
        } catch (GLException e) {
            return false;
        }
    }
}
