package de.michiruf.scalor.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author Michael Ruf
 * @since 2016-03-08
 */
public class SaveHook {

    private final ObjectMapper objectMapper;
    private final String configurationPath;

    @Inject
    public SaveHook(ObjectMapper objectMapper, @Named("configurationJson") String configurationPath) {
        this.objectMapper = objectMapper;
        this.configurationPath = configurationPath;
    }

    public void register(Configuration configuration) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    byte[] data = objectMapper.writeValueAsBytes(configuration);
                    Files.write(Paths.get(configurationPath), data, StandardOpenOption.CREATE,
                            StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace(); // TODO Error
                }
            }
        });
    }
}
