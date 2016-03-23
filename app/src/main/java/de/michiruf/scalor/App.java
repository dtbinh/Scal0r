package de.michiruf.scalor;

import dagger.ObjectGraph;
import de.michiruf.scalor.ui.MainWindowPresenter;

/**
 * @author Michael Ruf
 * @since 2016-03-23
 */
public class App {

    public static void main(String args[]) {
        ObjectGraph appGraph = ObjectGraph.create(new AppModule());
        appGraph.injectStatics();
        appGraph.get(MainWindowPresenter.class).setVisible(true);
    }
}
