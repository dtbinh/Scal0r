package de.michiruf.scalor.capture;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;
import java.awt.AWTException;
import java.awt.Robot;

/**
 * @author Michael Ruf
 * @since 2016-03-23
 */
@Module(
        injects = {
                Capture.class,
                DisplayFrame.class,
                Monitor.class,
                Robot.class
        },
        library = true,
        complete = false
)
public class CaptureModule {

    @SuppressWarnings("unused")
    @Provides
    @Singleton
    public Robot provideRobot() {
        try {
            return new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        return null;
    }
}
