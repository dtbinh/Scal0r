package de.michiruf.scalor.capture;

import de.michiruf.scalor.config.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

/**
 * @author Michael Ruf
 * @since 2016-03-23
 */
@Singleton
public class Monitor {

    private final Robot robot;
    private final Configuration configuration;

    @Inject
    public Monitor(Robot robot, Configuration configuration) {
        this.robot = robot;
        this.configuration = configuration;
    }

    public BufferedImage captureScreen() {
        return robot.createScreenCapture(
                new Rectangle(configuration.getScanX(), configuration.getScanY(),
                        configuration.getScanWidth(), configuration.getScanHeight()));
    }
}
