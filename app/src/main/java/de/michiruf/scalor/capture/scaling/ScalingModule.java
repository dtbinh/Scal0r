package de.michiruf.scalor.capture.scaling;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * @author Michael Ruf
 * @since 2016-03-29
 */
@Module(
        injects = {
                Scaling.class
        },
        library = true,
        complete = false
)
public class ScalingModule {

    @SuppressWarnings("unused")
    @Provides
    @Singleton
    public Scaling provideScaling() {
        // TODO
        return image -> image;
    }
}
