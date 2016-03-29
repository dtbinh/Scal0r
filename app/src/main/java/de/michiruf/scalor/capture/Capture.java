package de.michiruf.scalor.capture;

import de.michiruf.scalor.capture.display.Display;
import de.michiruf.scalor.capture.monitor.Monitor;
import de.michiruf.scalor.helper.FrameCounter;
import de.michiruf.scalor.helper.HighPriorityDefaultThreadFactory;

import javax.inject.Inject;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Michael Ruf
 * @since 2016-03-23
 */
public class Capture {

    private final Monitor monitor;
    private final Display display;
    private final ScheduledExecutorService executor;
    private ScheduledFuture<?> executorFuture;
    private FrameCounter frameCounter;

    @Inject
    public Capture(Monitor monitor, Display display) {
        this.monitor = monitor;
        this.display = display;
        this.executor = Executors.newScheduledThreadPool(1, new HighPriorityDefaultThreadFactory());
    }

    public void start() {
        display.setVisible(true);
        display.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                    stop();
                }
            }
        });

        frameCounter = FrameCounter.createAndStart();
        // 100 frames maximum should be good enough
        executorFuture = executor.scheduleAtFixedRate(this::capture, 0, 10, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        display.setVisible(false);
        if (executorFuture != null) {
            executorFuture.cancel(true);
        }

        System.out.println("Capture frames average: " + frameCounter.getFrames());
    }

    public boolean isRunning() {
        return display.isVisible();
    }

    private void capture() {
        frameCounter.tick();
        display.draw(monitor.captureScreen());
    }
}
