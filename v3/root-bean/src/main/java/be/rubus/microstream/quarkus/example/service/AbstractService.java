package be.rubus.microstream.quarkus.example.service;

import be.rubus.microstream.quarkus.example.database.Locks;
import be.rubus.microstream.quarkus.example.database.Root;
import one.microstream.storage.types.StorageManager;

import jakarta.inject.Inject;

public abstract class AbstractService {

    @Inject
    protected Locks locks;

    @Inject
    protected Root root;

}
