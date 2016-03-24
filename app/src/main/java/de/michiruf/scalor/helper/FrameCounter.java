package de.michiruf.scalor.helper;

/**
 * @author Michael Ruf
 * @since 2016-03-24
 */
public class FrameCounter {

    public static FrameCounter createAndStart() {
        FrameCounter frameCounter = new FrameCounter();
        frameCounter.start();
        return frameCounter;
    }

    private long startTime;
    private long stopTime;
    private boolean stopped;
    private long ticks;

    public FrameCounter() {
        stopped = false;
        ticks = 0;
    }

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void tick() {
        ticks++;
    }

    public void stop() {
        stopTime = System.currentTimeMillis();
    }

    public double getFrames() {
        if (!stopped) {
            stop();
        }
        return (double) ticks / ((double) (stopTime - startTime) / 1000d);
    }
}
