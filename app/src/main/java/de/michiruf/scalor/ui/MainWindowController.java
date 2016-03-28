package de.michiruf.scalor.ui;

import de.michiruf.scalor.capture.Capture;
import de.michiruf.scalor.config.Configuration;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    protected static Capture capture;

    @FXML
    private TextField scanX;
    @FXML
    private TextField scanY;
    @FXML
    private TextField scanWidth;
    @FXML
    private TextField scanHeight;

    @FXML
    private TextField outputX;
    @FXML
    private TextField outputY;
    @FXML
    private TextField outputWidth;
    @FXML
    private TextField outputHeight;

    @FXML
    private Button startStopButton;

    @SuppressWarnings("unused") // due to FXML
    @FXML
    protected void initialize() {
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
        field.setText(Integer.toString(value));
        field.setOnKeyReleased(event -> save());
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
            saveAction.call(Integer.parseInt(field.getText()));
        } catch (NumberFormatException e) {
            // Do nothing
        }
    }

    @FXML
    public void onStartStopClick() {
        if (!capture.isRunning()) {
            capture.start();
            startStopButton.setText("Stop");
        } else {
            capture.stop();
            startStopButton.setText("Start");
        }
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
