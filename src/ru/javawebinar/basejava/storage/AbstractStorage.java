package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    public final Resume get(String uuid) {
        Object searchKey = getNotExistingSearchKey(uuid);
        return getElement(searchKey);
    }

    public final void update(Resume resume) {
        Object searchKey = getNotExistingSearchKey(resume.getUuid());
        updateElement(searchKey, resume);
    }

    public final void save(Resume resume) {
        Object searchKey = getExistingSearchKey(resume.getUuid());
        saveElement(resume, searchKey);
    }

    public final void delete(String uuid) {
        Object searchKey = getNotExistingSearchKey(uuid);
        removeElement(searchKey);
    }

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistingSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract boolean isExist(Object searchKey);

    protected abstract void removeElement(Object searchKey);

    protected abstract void updateElement(Object searchKey, Resume resume);

    protected abstract Resume getElement(Object searchKey);

    protected abstract void saveElement(Resume resume, Object searchKey);

    protected abstract Object findSearchKey(String uuid);

    public abstract int size();

}
