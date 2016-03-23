package de.michiruf.scalor;

import dagger.Module;
import de.michiruf.scalor.capture.CaptureModule;
import de.michiruf.scalor.config.ConfigurationModule;
import de.michiruf.scalor.ui.UiModule;

/**
 * @author Michael Ruf
 * @since 2016-03-23
 */
@Module(
        includes = {
                CaptureModule.class,
                ConfigurationModule.class,
                UiModule.class
        }
)
public class AppModule {
}
