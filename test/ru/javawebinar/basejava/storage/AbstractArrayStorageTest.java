package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String DUMMY = "dummy";
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_5 = "uuid5";
    private static final Resume resume1;
    private static final Resume resume2;
    private static final Resume resume3;
    private static final Resume resume4;
    private static final Resume resume5;
    private static final int INITIAL_SIZE = 3;

    static{
        resume1 = new Resume(UUID_1);
        resume2 = new Resume(UUID_2);
        resume3 = new Resume(UUID_3);
        resume4 = new Resume(UUID_4);
        resume5 = new Resume(UUID_5);
    }



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
        assertGet(resume1);
    }

    @Test
    void getNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            assertGet(new Resume(DUMMY));
        });
    }

    @Test
    void size() {
        assertSize(INITIAL_SIZE);
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
        Assertions.assertArrayEquals(storage.getAll(), new Resume[0]);
    }

    @Test
    void update() {
        Resume r3 = new Resume(UUID_3);
        storage.update(r3);
        assertGet(r3);
        assertSize(INITIAL_SIZE);
    }

    @Test
    void updateNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.update(new Resume(UUID_4));
        });
    }

    @Test
    void getAll() {
        Resume[] resumes = new Resume[]{resume1, resume2, resume3};
        Assertions.assertArrayEquals(storage.getAll(), resumes);
    }

    @Test
    void save() {
        storage.save(resume5);
        assertGet(resume5);
        assertSize(INITIAL_SIZE+1);
    }

    @Test
    void saveOverflow() {
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

    @Test
    void saveExistStorage() {
        Assertions.assertThrows(ExistStorageException.class, () -> {
            storage.save(new Resume(UUID_2));
        });
    }

    @Test
    void delete() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.delete(UUID_2);
            assertSize(INITIAL_SIZE-1);
            assertGet(resume2);
        });
    }

    @Test
    void deleteNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.delete(UUID_4);
        });
    }

    private void assertSize(int size) {
        Assertions.assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        Assertions.assertEquals(storage.get(resume.getUuid()), resume);
    }
}