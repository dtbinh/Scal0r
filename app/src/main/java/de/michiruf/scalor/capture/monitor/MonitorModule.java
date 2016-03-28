package de.michiruf.scalor.capture.monitor;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLProfile;
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
//        try {
//            GLProfile defaultProfile = GLProfile.getDefault();
//            GLCapabilities capabilities = new GLCapabilities(defaultProfile);
//            capabilities.setRedBits(8);
//            capabilities.setBlueBits(8);
//            capabilities.setGreenBits(8);
//            capabilities.setAlphaBits(8);
//            return new OpenGLMonitor(configuration, capabilities);
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }

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
