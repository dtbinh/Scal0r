package de.michiruf.scalor.capture.display;

import dagger.Module;
import dagger.Provides;
import de.michiruf.scalor.config.Configuration;

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
        return new GraphicsDisplayFrame(configuration);
    }

    // TODO Maybe improve this by just update the data instead of the ImageIcon
    // TODO Maybe do not use a JFrame and a Label to draw the image
}
