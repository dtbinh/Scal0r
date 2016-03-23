package de.michiruf.scalor.ui;

import de.michiruf.scalor.capture.Capture;
import de.michiruf.scalor.config.Configuration;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.inject.Inject;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author Michael Ruf
 * @since 2016-03-01
 */
public class MainWindowController {

    @Inject
    protected static Configuration configuration;
    @Inject
    protected static IndicatorFrame indicatorFrame;
    @Inject
    protected static Capture capture;

    @FXML
    public TextField scanX;
    @FXML
    public TextField scanY;
    @FXML
    public TextField scanWidth;
    @FXML
    public TextField scanHeight;

    @FXML
    public TextField outputX;
    @FXML
    public TextField outputY;
    @FXML
    public TextField outputWidth;
    @FXML
    public TextField outputHeight;

    @SuppressWarnings("unused") // due to FXML
    @FXML
    protected void initialize() {
        if (configuration == null) return;

        initializeTextField(scanX, configuration.getScanX());
        initializeTextField(scanY, configuration.getScanY());
        initializeTextField(scanWidth, configuration.getScanWidth());
        initializeTextField(scanHeight, configuration.getScanHeight());

        initializeTextField(outputX, configuration.getOutputX());
        initializeTextField(outputY, configuration.getOutputY());
        initializeTextField(outputWidth, configuration.getOutputWidth());
        initializeTextField(outputHeight, configuration.getOutputHeight());
    }

    private void initializeTextField(TextField field, int value) {
        if (value != 0) {
            field.setText(Integer.toString(value));
        }
        field.setOnKeyReleased(event -> {
            save();
            indicatorFrame.rearrange();
        });
        field.setOnMousePressed(event -> indicatorFrame.setVisible(true));
        field.setOnMouseExited(event -> indicatorFrame.setVisible(false));
    }

    private void save() {
        saveTextField(scanX, configuration::setScanX);
        saveTextField(scanY, configuration::setScanY);
        saveTextField(scanWidth, configuration::setScanWidth);
        saveTextField(scanHeight, configuration::setScanHeight);

        saveTextField(outputX, configuration::setOutputX);
        saveTextField(outputY, configuration::setOutputY);
        saveTextField(outputWidth, configuration::setOutputWidth);
        saveTextField(outputHeight, configuration::setOutputHeight);
    }

    private void saveTextField(TextField field, SaveAction saveAction) {
        try {
            int port = Integer.parseInt(field.getText());
            if (port != 0) {
                saveAction.call(port);
            }
        } catch (NumberFormatException e) {
            // Do nothing
        }
    }

    @FXML
    public void onStartClick() {
        capture.start();
    }

    @FXML
    public void onGithubClick() {
        try {
            Desktop.getDesktop().browse(new URL("https://github.com/michiruf").toURI());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public interface SaveAction {

        void call(int value);
    }
}
