package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected int size;
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    public int size() {
        return size;
    }

    @Override
    protected Resume getElement(Integer searchKey) {
        return storage[ searchKey];
    }

    public void clear() {
        size = 0;
        Arrays.fill(storage, null);
    }

    @Override
    protected void updateElement(Integer searchKey, Resume resume) {
        storage[ searchKey] = resume;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public List<Resume> getAsList() {
        List<Resume> copyResumes = Arrays.asList(Arrays.copyOfRange(storage, 0, size));
        return copyResumes;

    }

    public final void saveElement(Resume resume, Integer searchKey) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow!", resume.getUuid());
        } else {
            addNewElement(resume,searchKey);
            size++;
        }
    }

    @Override
    protected void removeElement(Integer searchKey) {
        remove( searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    protected abstract Integer findSearchKey(String uuid);

    protected abstract void addNewElement(Resume resume, int index);

    protected abstract void remove(int index);
}
