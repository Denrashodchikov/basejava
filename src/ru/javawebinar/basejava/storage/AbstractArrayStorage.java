package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {//implements Storage {
    protected int size;
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    public int size() {
        return size;
    }

    @Override
    protected Resume getElement(int index) {
        return storage[index];
    }

    public void clear() {
        size = 0;
        Arrays.fill(storage, null);
    }

    @Override
    protected void updateElement(int index, Resume resume) {
        storage[index] = resume;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public final void saveElement(Resume resume) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow!", resume.getUuid());
        } else {
            addNewElement(resume);
            size++;
        }
    }

    @Override
    protected void removeElement(int index) {
        remove(index);
        storage[size - 1] = null;
        size--;
    }

    protected abstract int findSearchKey(String uuid);

    protected abstract void addNewElement(Resume resume);

    protected abstract void remove(int index);
}
