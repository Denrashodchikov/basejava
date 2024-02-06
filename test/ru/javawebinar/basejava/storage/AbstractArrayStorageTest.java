package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final Resume resume1 = new Resume(UUID_1);
    private static final Resume resume2 = new Resume(UUID_2);
    private static final Resume resume3 = new Resume(UUID_3);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    void get() {
        Assertions.assertEquals(storage.get("uuid1"), resume1);
    }

    @Test
    void getNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.get("dummy");
        });

    }

    @Test
    void size() {
        Assertions.assertEquals(3, storage.size());
    }

    @Test
    void clear() {
        storage.clear();
        Assertions.assertEquals(0, storage.size());
    }

    @Test
    void update() {
        Resume r3 = new Resume("uuid3");
        storage.update(r3);
        Assertions.assertSame(storage.get("uuid3"), r3);
        Assertions.assertEquals(3, storage.size());
    }

    @Test
    void updateNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.update(new Resume("uuid4"));
        });
    }

    @Test
    void getAll() {
        Resume[] resumes = new Resume[]{resume1, resume2, resume3};
        Assertions.assertArrayEquals(storage.getAll(), resumes);
    }

    @Test
    void save() {
        Resume r5 = new Resume("uuid5");
        storage.save(r5);
        Assertions.assertSame(storage.get("uuid5"), r5);
        Assertions.assertEquals(4, storage.size());
    }

    @Test
    void saveStorageFull() {
        try {
            for (int i = 3; i < 10000; i++) {
                storage.save(new Resume("uuid_" + i));
            }
        } catch (StorageException e) {
            Assertions.fail("Storage is full ahead of time!");
        }
    }

    @Test
    void saveExistStorage() {
        Assertions.assertThrows(ExistStorageException.class, () -> {
            storage.save(new Resume("uuid2"));
        });
    }

    @Test
    void delete() {
        storage.delete("uuid2");
        Assertions.assertEquals(2, storage.size());
    }

    @Test
    void deleteNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.update(new Resume("uuid4"));
        });
    }
}