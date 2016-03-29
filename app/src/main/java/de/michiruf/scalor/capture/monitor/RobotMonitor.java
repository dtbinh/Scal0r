package de.michiruf.scalor.capture.monitor;

import de.michiruf.scalor.config.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.Observable;

/**
 * @author Michael Ruf
 * @since 2016-03-23
 */
@Singleton
public class RobotMonitor implements Monitor {

    private final Robot robot;
    private final Configuration configuration;
    private Rectangle dimens;

    @Inject
    public RobotMonitor(Robot robot, Configuration configuration) {
        this.robot = robot;
        this.configuration = configuration;
        configuration.addObserver(this);
        updateDimens();
    }

    @Override
    public BufferedImage captureScreen() {
        return robot.createScreenCapture(dimens);
    }

    @Override
    public void update(Observable o, Object arg) {
        updateDimens();
    }

    private void updateDimens() {
        dimens = new Rectangle(configuration.getScanX(), configuration.getScanY(),
                configuration.getScanWidth(), configuration.getScanHeight());
    }
}
