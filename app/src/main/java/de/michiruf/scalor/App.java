package de.michiruf.scalor;

import dagger.ObjectGraph;
import de.michiruf.scalor.ui.MainWindowPresenter;

import javax.swing.SwingUtilities;

/**
 * @author Michael Ruf
 * @since 2016-03-23
 */
public class App {

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

            ObjectGraph appGraph = ObjectGraph.create(new AppModule());
            appGraph.injectStatics();
            appGraph.get(MainWindowPresenter.class).setVisible(true);
        });
    }
}
