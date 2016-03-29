package de.michiruf.scalor.capture;

import dagger.Module;
import de.michiruf.scalor.capture.display.DisplayModule;
import de.michiruf.scalor.capture.monitor.MonitorModule;
import de.michiruf.scalor.capture.scaling.ScalingModule;

/**
 * @author Michael Ruf
 * @since 2016-03-23
 */
@Module(
        includes = {
                DisplayModule.class,
                MonitorModule.class,
                ScalingModule.class
        },
        injects = {
                Capture.class
        },
        library = true,
        complete = false
)
public class CaptureModule {
}
