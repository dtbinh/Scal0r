package de.michiruf.scalor.ui;

import dagger.Module;
import dagger.Provides;

import javax.inject.Named;

/**
 * @author Michael Ruf
 * @since 2016-03-23
 */
@Module(
        injects = {
                IndicatorFrame.class,
                MainWindowPresenter.class
        },
        staticInjections = {
                MainWindowController.class
        },
        library = true,
        complete = false
)
public class UiModule {

    @SuppressWarnings("unused")
    @Provides
    @Named("mainFxml")
    public String provideMainFxml() {
        return "MainWindow.fxml";
    }
}
