package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    private static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> {
        if (o1.getFullName().equals(o2.getFullName())) {
            return o1.getUuid().compareTo(o2.getUuid());
        } else {
            return o1.getFullName().compareTo(o2.getFullName());
        }
    };

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

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> sortedList = getAsList();
        sortedList.sort(RESUME_COMPARATOR);
        return sortedList;
    }

    protected abstract List<Resume> getAsList();

    protected abstract boolean isExist(Object searchKey);

    protected abstract void removeElement(Object searchKey);

    protected abstract void updateElement(Object searchKey, Resume resume);

    protected abstract Resume getElement(Object searchKey);

    protected abstract void saveElement(Resume resume, Object searchKey);

    protected abstract Object findSearchKey(String uuid);

    public abstract int size();

}
