package de.michiruf.scalor.helper;

import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.opengl.GLProfile;

/**
 * @author Michael Ruf
 * @since 2016-03-28
 */
public class OpenGLHelper {

    public static boolean isOpenGLSupported() {
        return true; // TODO

        // See:
        // http://www.coderanch.com/t/587914/java/java/JOGL-offscreenDrawable-PBuffers-save-png
//        GLDrawableFactory.getFactory(GLProfile.getDefault()).canCreateGLPbuffer(GLProfile.getDefaultDevice());
    }
}
