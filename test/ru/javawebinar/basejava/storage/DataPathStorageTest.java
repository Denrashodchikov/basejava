package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.strategy.DataStreamSerializer;

public class DataPathStorageTest extends AbstractStorageTest {
    public DataPathStorageTest() {
        super(new PathStorage(STORAGEDIR.getAbsolutePath(), new DataStreamSerializer()));
    }
}
