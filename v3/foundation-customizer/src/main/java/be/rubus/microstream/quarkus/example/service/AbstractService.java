package be.rubus.microstream.quarkus.example.service;

import be.rubus.microstream.quarkus.example.database.Locks;
import be.rubus.microstream.quarkus.example.database.Root;
import jakarta.annotation.PostConstruct;
import one.microstream.storage.types.StorageManager;

import jakarta.inject.Inject;

public abstract class AbstractService {

    @Inject
    protected StorageManager storageManager;

    @Inject
    protected Locks locks;

    protected Root root;

    @PostConstruct
    void init() {
        root = (Root) storageManager.root();
    }
}
