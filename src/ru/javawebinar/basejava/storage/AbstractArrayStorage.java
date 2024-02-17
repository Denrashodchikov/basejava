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
    protected Resume getElement(Object searchKey) {
        return storage[(int) searchKey];
    }

    public void clear() {
        size = 0;
        Arrays.fill(storage, null);
    }

    @Override
    protected void updateElement(Object searchKey, Resume resume) {
        storage[(int) searchKey] = resume;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public final void saveElement(Resume resume,  Object searchKey) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow!", resume.getUuid());
        } else {
            addNewElement(resume,(int) searchKey);
            size++;
        }
    }

    @Override
    protected void removeElement(Object searchKey) {
        remove((Integer) searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }

    protected abstract Object findSearchKey(String uuid);

    protected abstract void addNewElement(Resume resume, int index);

    protected abstract void remove(int index);
}
