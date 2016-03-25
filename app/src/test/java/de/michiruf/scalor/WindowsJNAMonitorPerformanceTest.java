package de.michiruf.scalor;

import com.jayway.awaitility.Awaitility;
import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;
import de.michiruf.scalor.capture.Capture;
import de.michiruf.scalor.capture.monitor.Monitor;
import de.michiruf.scalor.capture.monitor.WindowsJNAMonitor;
import de.michiruf.scalor.config.Configuration;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Singleton;

/**
 * @author Michael Ruf
 * @since 2016-03-25
 */
public class WindowsJNAMonitorPerformanceTest {

    private Capture c;

    @Before
    public void setUp() {
        ObjectGraph o = ObjectGraph.create(new TestModule());
        c = o.get(Capture.class);
    }

    @Test
    public void testPerformance() {
        c.start();
        Awaitility.await().until(() -> {
            try {
                Thread.sleep(5000);
                System.out.print("Windows JNA ");
                c.stop(); // TODO only 1 tick every time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @Module(
            includes = AppModule.class,
            injects = WindowsJNAMonitorPerformanceTest.class,
            overrides = true
    )
    public static class TestModule {

        @SuppressWarnings("unused")
        @Provides
        @Singleton
        public Monitor provideMonitor(Configuration configuration) {
            return new WindowsJNAMonitor(configuration);
        }
    }
}
