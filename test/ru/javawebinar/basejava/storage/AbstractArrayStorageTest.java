package ru.javawebinar.basejava.storage;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.opentest4j.AssertionFailedError;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    public void saveOverflow() {
        Assertions.assertThrows(AssertionFailedError.class, () -> {
            try {
                storage.clear();
                for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                    storage.save(new Resume("uuid_" + i));
                }
                storage.save(resume5);
            } catch (StorageException e) {
                Assertions.fail("Storage is full ahead of time!");
            }
        });
    }
}
