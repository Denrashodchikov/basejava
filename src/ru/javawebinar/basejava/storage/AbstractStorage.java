package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    public final Resume get(String uuid) {
        int index = findSearchKey(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return getElement(index);
    }

    public final void update(Resume resume) {
        int index = findSearchKey(resume.getUuid());
        if (index >= 0) {
            updateElement(index, resume);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    public final void save(Resume resume) {
        if (findSearchKey(resume.getUuid()) >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            saveElement(resume);
        }
    }

    public final void delete(String uuid) {
        int index = findSearchKey(uuid);
        if (index >= 0) {
            removeElement(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract void removeElement(int index);

    protected abstract void updateElement(int index, Resume resume);

    protected abstract Resume getElement(int index);

    protected abstract void saveElement(Resume resume);

    protected abstract int findSearchKey(String uuid);

    public abstract int size();

}
