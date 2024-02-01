package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected int size;
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = findSearchKey(uuid);
        if (index == -1) {
            System.out.println("Uuid: " + uuid + " not found!");
            return null;
        }
        return storage[index];
    }

    protected abstract int findSearchKey(String uuid);
}
