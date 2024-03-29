package be.rubus.microstream.quarkus.example;

import one.microstream.integrations.quarkus.types.config.EmbeddedStorageFoundationCustomizer;
import one.microstream.storage.embedded.types.EmbeddedStorageFoundation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FoundationCustomizer implements EmbeddedStorageFoundationCustomizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FoundationCustomizer.class);

    @Override
    public void customize(EmbeddedStorageFoundation embeddedStorageFoundation) {
        LOGGER.info("(From the App) Additional configuration on the EmbeddedStorageFoundation");

        // do additional configuration
        /*
        storageFoundation.onConnectionFoundation(BinaryHandlersJDK8::registerJDK8TypeHandlers);
        storageFoundation.onConnectionFoundation(f -> f.registerCustomTypeHandler());
        storageFoundation.onConnectionFoundation(f -> f
                        .getCustomTypeHandlerRegistry()
                        .registerLegacyTypeHandler(
                                new LegacyTypeHandlerBook()
                        )
        );

         */
    }
}
