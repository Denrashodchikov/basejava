package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    public final Resume get(String uuid) {
        SK searchKey = getNotExistingSearchKey(uuid);
        return getElement(searchKey);
    }

    public final void update(Resume resume) {
        SK searchKey = getNotExistingSearchKey(resume.getUuid());
        updateElement(searchKey, resume);
    }

    public final void save(Resume resume) {
        SK searchKey = getExistingSearchKey(resume.getUuid());
        saveElement(resume, searchKey);
    }

    public final void delete(String uuid) {
        SK searchKey = getNotExistingSearchKey(uuid);
        removeElement(searchKey);
    }

    private SK getExistingSearchKey(String uuid) {
        SK searchKey = findSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistingSearchKey(String uuid) {
        SK searchKey = findSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> sortedList = getAsList();
        sortedList.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return sortedList;
    }

    protected abstract List<Resume> getAsList();

    protected abstract boolean isExist(SK searchKey);

    protected abstract void removeElement(SK searchKey);

    protected abstract void updateElement(SK searchKey, Resume resume);

    protected abstract Resume getElement(SK searchKey);

    protected abstract void saveElement(Resume resume, SK searchKey);

    protected abstract SK findSearchKey(String uuid);

    public abstract int size();

}
