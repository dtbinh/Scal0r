package de.michiruf.scalor.ui;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Toolkit;
import java.io.IOException;

/**
 * @author Michael Ruf
 * @since 2016-03-08
 */
@Singleton
public class MainWindowPresenter extends JFrame {

    @Inject
    public MainWindowPresenter(@Named("mainFxml") String fxmlPath) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(300, 450);
        int x = Toolkit.getDefaultToolkit().getScreenSize().width / 2 - getSize().width / 2;
        int y = Toolkit.getDefaultToolkit().getScreenSize().height / 2 - getSize().height / 2;
        setLocation(x, y);

        initializeFx(fxmlPath);
    }

    private void initializeFx(String fxmlPath) {
        JFXPanel jfxPanel = new JFXPanel();
        add(jfxPanel);

        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlPath));
                jfxPanel.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
