package de.michiruf.scalor.capture;

import javax.inject.Inject;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Michael Ruf
 * @since 2016-03-23
 */
public class Capture {

    private final Monitor monitor;
    private final DisplayFrame displayFrame;
    private final ThreadPoolExecutor captureThread;
    private boolean shellRun = false;

    @Inject
    public Capture(Monitor monitor, DisplayFrame displayFrame) {
        this.monitor = monitor;
        this.displayFrame = displayFrame;
        captureThread = new ThreadPoolExecutor(4, 10, 1, TimeUnit.DAYS, new LinkedBlockingDeque<>());
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

        shellRun = true;
        captureThread.execute(new Runnable() {
            @Override
            public void run() {
                displayFrame.draw(resizeImage(monitor.captureScreen()));
                if (shellRun) {
                    captureThread.execute(this);
                }
            }
        });
    }

    public void stop() {
        displayFrame.setVisible(false);
        shellRun = false;
    }

    public boolean isRunning() {
        return shellRun;
    }

    // TODO make scaling configurable
    private Image resizeImage(BufferedImage bufferedImage) {
        return bufferedImage.getScaledInstance(bufferedImage.getWidth(), bufferedImage.getHeight() * 2,
                Image.SCALE_DEFAULT);
    }
}
