package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;


/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected int findSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void addNewElement(Resume resume) {
        storage[size] = resume;
    }

    @Override
    protected void remove(int index) {
        storage[index] = storage[size - 1];
    }
}
