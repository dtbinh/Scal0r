package de.michiruf.scalor;

import org.junit.Test;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

/**
 * @author Michael Ruf
 * @since 2016-03-24
 */
public class RobotPerformanceTest {

    int n = 100;

    @Test
    public void testRobotPerformance() throws AWTException {
        Robot robot = new Robot();
        Rectangle area = new Rectangle(0, 330, 1920, 540);
        long start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            robot.createScreenCapture(area);
        }
        long end = System.currentTimeMillis();
        double frames = (double) n / ((double) (end - start) / 1000d);
        System.out.println("Robot Frames: " + frames);
    }

    @Test
    public void testRobotScalingPerformance() throws AWTException {
        Robot robot = new Robot();
        Rectangle area = new Rectangle(0, 330, 1920, 540);
        long start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            BufferedImage src = robot.createScreenCapture(area);
            Image dst = src.getScaledInstance(src.getWidth(), src.getHeight() * 2, Image.SCALE_FAST);
        }
        long end = System.currentTimeMillis();
        double frames = (double) n / ((double) (end - start) / 1000d);
        System.out.println("Robot Scaled Frames: " + frames);
    }
}
