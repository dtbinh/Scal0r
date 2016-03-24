package de.michiruf.scalor.ui;

import de.michiruf.scalor.config.Configuration;
import de.michiruf.scalor.helper.ScreenHelper;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * @author Michael Ruf
 * @since 2016-03-23
 */
@Singleton
public class IndicatorFrame extends JFrame {

    private Configuration configuration;

    @Inject
    public IndicatorFrame(Configuration configuration) {
        super();
        this.configuration = configuration;
        setUndecorated(true);
        setAlwaysOnTop(true);
        setBackground(new Color(0, 0, 0, 0));
        setLocationRelativeTo(null);
        setContentPane(new TranslucentPane());
        setVisible(false);
    }

    public void rearrange() {
        ScreenHelper.showOnScreen(configuration.getScanScreen() - 1, this);
        setBounds(configuration.getScanX(), configuration.getScanY(),
                configuration.getScanWidth(), configuration.getScanHeight());
        // TODO display red frame for output and green for scan
    }

    public static class TranslucentPane extends JPanel {

        public TranslucentPane() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(new Color(255, 0, 0, 100));
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
