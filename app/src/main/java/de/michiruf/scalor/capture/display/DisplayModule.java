package de.michiruf.scalor.capture.display;

import dagger.Module;
import dagger.Provides;
import de.michiruf.scalor.config.Configuration;
import de.michiruf.scalor.helper.OpenGLHelper;

import javax.inject.Singleton;

/**
 * @author Michael Ruf
 * @since 2016-03-28
 */
@Module(
        injects = {
                Display.class
        },
        library = true,
        complete = false
)
public class DisplayModule {

    @SuppressWarnings("unused")
    @Provides
    @Singleton
    public Display provideDisplay(Configuration configuration) {
        if (OpenGLHelper.isOpenGLSupported()) {
            return new OpenGLDisplayFrame(configuration);
        }

        return new GraphicsDisplayFrame(configuration);
    }
}
