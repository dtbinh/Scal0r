package de.michiruf.scalor.capture;

import dagger.Module;
import de.michiruf.scalor.capture.display.DisplayModule;
import de.michiruf.scalor.capture.monitor.MonitorModule;

/**
 * @author Michael Ruf
 * @since 2016-03-23
 */
@Module(
        includes = {
                DisplayModule.class,
                MonitorModule.class
        },
        injects = {
                Capture.class
        },
        library = true,
        complete = false
)
public class CaptureModule {
}
