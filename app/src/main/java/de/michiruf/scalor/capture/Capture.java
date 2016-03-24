package de.michiruf.scalor.capture;

import javax.inject.Inject;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * @author Michael Ruf
 * @since 2016-03-23
 */
public class Capture {

    private final Monitor monitor;
    private final DisplayFrame displayFrame;
    private final Thread captureThread;
    private boolean shellRun = false;

    @Inject
    public Capture(Monitor monitor, DisplayFrame displayFrame) {
        this.monitor = monitor;
        this.displayFrame = displayFrame;
        captureThread = new Thread(() -> {
            while (shellRun) {
                capture();
            }
        });
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
        captureThread.start();
    }

    public void stop() {
        displayFrame.setVisible(false);
        shellRun = false;
    }

    public boolean isRunning() {
        return shellRun;
    }

    private void capture() {
        displayFrame.draw((monitor.captureScreen()));
    }

    // TODO make scaling configurable
    private Image resizeImage(BufferedImage bufferedImage) {
        return bufferedImage.getScaledInstance(bufferedImage.getWidth(), bufferedImage.getHeight() * 2,
                Image.SCALE_FAST);
    }
}
