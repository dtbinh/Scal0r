package de.michiruf.scalor.capture;

import de.michiruf.scalor.capture.monitor.Monitor;
import de.michiruf.scalor.helper.FrameCounter;
import de.michiruf.scalor.helper.HighPriorityDefaultThreadFactory;
import de.michiruf.scalor.helper.ImageCompat;

import javax.inject.Inject;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
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
    private final DisplayFrame displayFrame;
    private final ScheduledExecutorService executor;
    private ScheduledFuture<?> executorFuture;
    private FrameCounter frameCounter;

    @Inject
    public Capture(Monitor monitor, DisplayFrame displayFrame) {
        this.monitor = monitor;
        this.displayFrame = displayFrame;
        this.executor = Executors.newScheduledThreadPool(1, new HighPriorityDefaultThreadFactory());
    }

    public void start() {
        displayFrame.setVisible(true);
        displayFrame.addKeyListener(new KeyAdapter() {
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
        displayFrame.setVisible(false);
        if (executorFuture != null) {
            executorFuture.cancel(true);
        }

        System.out.println("Capture frames average: " + frameCounter.getFrames());
    }

    public boolean isRunning() {
        return displayFrame.isVisible();
    }

    private void capture() {
        frameCounter.tick();
        displayFrame.draw(resizeImage(monitor.captureScreen()));
    }

    // TODO make scaling configurable
    private Image resizeImage(BufferedImage bufferedImage) {
        bufferedImage = ImageCompat.toCompatibleImage(bufferedImage);
        return bufferedImage.getScaledInstance(bufferedImage.getWidth(), bufferedImage.getHeight() * 2,
                Image.SCALE_FAST);
    }
}
