package de.michiruf.scalor.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.Module;
import dagger.Provides;

import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Michael Ruf
 * @since 2016-03-08
 */
@Module(
        injects = {
                Configuration.class,
                ObjectMapper.class,
                SaveHook.class
        },
        library = true
)
public class ConfigurationModule {

    @SuppressWarnings("unused")
    @Provides
    @Named("configurationJson")
    public String provideConfigurationPath() {
        return "settings.json";
    }

    @SuppressWarnings("unused")
    @Provides
    @Singleton
    public Configuration provideConfiguration(ObjectMapper objectMapper,
                                              @Named("configurationJson") String configurationJson,
                                              SaveHook saveHook) {
        Configuration configuration;
        try {
            byte[] data = Files.readAllBytes(Paths.get(configurationJson));
            configuration = objectMapper.readValue(data, Configuration.class);
        } catch (IOException e) {
            configuration = new Configuration();
        }

        saveHook.register(configuration);
        return configuration;
    }

    @SuppressWarnings("unused")
    @Provides
    @Singleton
    public ObjectMapper provideObjectMapper() {
        return new ObjectMapper();
    }
}
