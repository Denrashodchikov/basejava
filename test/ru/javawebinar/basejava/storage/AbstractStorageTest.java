package ru.javawebinar.basejava.storage;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static ru.javawebinar.basejava.ResumeTestData.createResume;


public abstract class AbstractStorageTest {
    protected static final File STORAGEDIR = Config.get().getStorageDir();

    protected final Storage storage;
    private static final String DUMMY = "dummy";
    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final String UUID_2 = UUID.randomUUID().toString();
    private static final String UUID_3 = UUID.randomUUID().toString();
    private static final String UUID_4 = UUID.randomUUID().toString();
    private static final String UUID_5 = UUID.randomUUID().toString();
    private static final String FULLNAME_1 = "uuid1_full";
    private static final String FULLNAME_2 = "uuid2_full";
    private static final String FULLNAME_3 = "uuid3_full";
    private static final String FULLNAME_4 = "uuid4_full";
    private static final String FULLNAME_5 = "uuid5_full";
    private static final Resume resume1= createResume(UUID_1,FULLNAME_1);
    private static final Resume resume2 = createResume(UUID_2,FULLNAME_2);
    private static final Resume resume3 = createResume(UUID_3,FULLNAME_3);
    private static final Resume resume4 = createResume(UUID_4,FULLNAME_4);
    private static final int INITIAL_SIZE = 3;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
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
        Assertions.assertThrows(NotExistStorageException.class, () -> assertGet(createResume(DUMMY,DUMMY)));
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
        Resume r3 = createResume(UUID_3,"new Name");
        storage.update(r3);
        assertGet(r3);
        assertSize(INITIAL_SIZE);
    }

    @Test
    public void updateNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.update(createResume(UUID_4,FULLNAME_4)));
    }

    @Test
    public void getAllSorted() {
        List<Resume> list = storage.getAllSorted();
        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals(list, Arrays.asList(resume1, resume2, resume3));
    }

    @Test
    public void save() {
        storage.save(resume4);
        assertGet(resume4);
        assertSize(INITIAL_SIZE + 1);
    }

    @Test
    public void saveExistStorage() {
        Assertions.assertThrows(ExistStorageException.class, () -> storage.save(createResume(UUID_2,FULLNAME_2)));
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
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_4));
    }

    private void assertSize(int size) {
        Assertions.assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        Assertions.assertEquals(resume,storage.get(resume.getUuid()));
    }
}