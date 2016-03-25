package de.michiruf.scalor.capture.monitor;

import dagger.Module;
import dagger.Provides;
import de.michiruf.scalor.config.Configuration;

import javax.inject.Singleton;
import java.awt.AWTException;
import java.awt.Robot;

/**
 * @author Michael Ruf
 * @since 2016-03-25
 */
@Module(
        injects = {
                Monitor.class
        },
        library = true,
        complete = false
)
public class MonitorModule {

    @SuppressWarnings("unused")
    @Provides
    @Singleton
    public Monitor provideMonitor(Configuration configuration) {
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            return new WindowsJNAMonitor(configuration);
        } else {
            try {
                return new RobotMonitor(new Robot(), configuration);
            } catch (AWTException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
