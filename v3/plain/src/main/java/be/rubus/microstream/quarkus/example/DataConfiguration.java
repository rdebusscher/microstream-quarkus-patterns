package be.rubus.microstream.quarkus.example;

import be.rubus.microstream.quarkus.example.database.Root;
import one.microstream.reflect.ClassLoaderProvider;
import one.microstream.storage.embedded.configuration.types.EmbeddedStorageConfiguration;
import one.microstream.storage.embedded.types.EmbeddedStorageFoundation;
import one.microstream.storage.types.StorageManager;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

@ApplicationScoped
public class DataConfiguration {

    @Inject
    @ConfigProperty(name = "one.microstream.config")
    String microStreamConfigLocation;

    @Inject
    DataInit dataInit;

    @Produces
    @ApplicationScoped
    public StorageManager defineStorageManager() {

        EmbeddedStorageFoundation<?> embeddedStorageFoundation = embeddedStorageFoundation();
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

        // Required when using Quarkus
        embeddedStorageFoundation.onConnectionFoundation(cf -> cf.setClassLoaderProvider(ClassLoaderProvider.New(
                Thread.currentThread()
                        .getContextClassLoader())));

        StorageManager storageManager = embeddedStorageFoundation.start();

        // Check Root available within StorageManager
        Root root = (Root) storageManager.root();
        boolean initRequired = false;
        if (root == null) {
            root = new Root();
            initRequired = true;
        }
        // Prep Root
        root.setStorageManager(storageManager);

        // Init 'database' with some data
        if (initRequired) {
            dataInit.init(root, storageManager);
            storageManager.setRoot(root);
            storageManager.storeRoot();
        }
        return storageManager;
    }

    private EmbeddedStorageFoundation<?> embeddedStorageFoundation() {

        return EmbeddedStorageConfiguration.load(microStreamConfigLocation)
                .createEmbeddedStorageFoundation();

    }

    public void dispose(@Disposes StorageManager manager) {

        manager.close();
    }
}
