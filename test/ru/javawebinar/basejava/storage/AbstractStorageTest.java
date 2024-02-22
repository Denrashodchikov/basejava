package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public abstract class AbstractStorageTest {
    final Storage storage;
    private static final String DUMMY = "dummy";
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_5 = "uuid5";
    private static final String FULLNAME_1 = "uuid1_full";
    private static final String FULLNAME_2 = "uuid2_full";
    private static final String FULLNAME_3 = "uuid3_full";
    private static final String FULLNAME_4 = "uuid4_full";
    private static final String FULLNAME_5 = "uuid5_full";
    private static final Resume resume1;
    private static final Resume resume2;
    private static final Resume resume3;
    private static final Resume resume4;
    static final Resume resume5;
    private static final int INITIAL_SIZE = 3;

    static {
        resume1 = new Resume(UUID_1,FULLNAME_1);
        resume2 = new Resume(UUID_2,FULLNAME_2);
        resume3 = new Resume(UUID_3,FULLNAME_3);
        resume4 = new Resume(UUID_4,FULLNAME_4);
        resume5 = new Resume(UUID_5,FULLNAME_5);
    }

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void get() {
        assertGet(resume1);
    }

    @Test
    public void getNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            assertGet(new Resume(DUMMY,DUMMY));
        });
    }

    @Test
    public void size() {
        assertSize(INITIAL_SIZE);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        Assertions.assertArrayEquals(storage.getAllSorted().toArray(new Resume[0]),new Resume[0]);
    }

    @Test
    public void update() {
        Resume r3 = new Resume(UUID_3,"new Name");
        storage.update(r3);
        assertGet(r3);
        assertSize(INITIAL_SIZE);
    }

    @Test
    public void updateNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.update(new Resume(UUID_4,FULLNAME_4));
        });
    }

    @Test
    public void getAllSorted() {
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        assertEquals(list, Arrays.asList(resume1, resume2, resume3));
    }

    @Test
    public void save() {
        storage.save(resume5);
        assertGet(resume5);
        assertSize(INITIAL_SIZE + 1);
    }

    @Test
    public void saveExistStorage() {
        Assertions.assertThrows(ExistStorageException.class, () -> {
            storage.save(new Resume(UUID_2,FULLNAME_2));
        });
    }

    @Test
    public void delete() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.delete(UUID_2);
            assertSize(INITIAL_SIZE - 1);
            assertGet(resume2);
        });
    }

    @Test
    public void deleteNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.delete(UUID_4);
        });
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        assertEquals(storage.get(resume.getUuid()), resume);
    }
}