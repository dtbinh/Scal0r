package de.michiruf.scalor;

import com.jayway.awaitility.Awaitility;
import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;
import de.michiruf.scalor.capture.Capture;
import de.michiruf.scalor.capture.monitor.Monitor;
import de.michiruf.scalor.capture.monitor.WindowsJNAMonitor;
import de.michiruf.scalor.config.Configuration;
import de.michiruf.scalor.helper.FrameCounter;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Singleton;
import java.lang.reflect.Field;

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

    //    @Test
    public void testPerformance() {
        new Thread(c::start).start();

//        try {
//            Awaitility.await().atMost(5, TimeUnit.SECONDS).until(() -> false);
//        } catch (ConditionTimeoutException e) {
//            // Do nothing
//        }

        long start = System.currentTimeMillis();
        long timeout = 5000;
        Awaitility.await().until(() -> System.currentTimeMillis() - start > timeout);

        System.out.print("Windows JNA ");
        c.stop(); // TODO only 1 tick every time // WTF?!
    }

    @Test
    public void testPerformance2() {
        c.start();

        FrameCounter frameCounter = null;
        try {
            Field field = c.getClass().getDeclaredField("frameCounter");
            field.setAccessible(true);
            frameCounter = (FrameCounter) field.get(c);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        assert frameCounter != null;
        Awaitility.await().until(
                Awaitility.fieldIn(frameCounter).ofType(long.class).andWithName("ticks"),
                Matchers.equalTo(5l)
        );

        System.out.print("Windows JNA ");
        c.stop();
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
