package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public final Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK searchKey = getNotExistingSearchKey(uuid);
        return getElement(searchKey);
    }

    public final void update(Resume resume) {
        SK searchKey = getNotExistingSearchKey(resume.getUuid());
        updateElement(searchKey, resume);
    }

    public final void save(Resume resume) {
        LOG.info("Save " + resume);
        SK searchKey = getExistingSearchKey(resume.getUuid());
        saveElement(resume, searchKey);
    }

    public final void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK searchKey = getNotExistingSearchKey(uuid);
        removeElement(searchKey);
    }

    private SK getExistingSearchKey(String uuid) {
        SK searchKey = findSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistingSearchKey(String uuid) {
        SK searchKey = findSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
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
