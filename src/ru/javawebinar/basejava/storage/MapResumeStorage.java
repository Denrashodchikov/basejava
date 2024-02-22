package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getElement(Resume searchKey) {
        return searchKey;
    }

    @Override
    protected boolean isExist(Resume searchKey) {
        return searchKey != null;
    }

    @Override
    protected void removeElement(Resume searchKey) {
        storage.remove((searchKey).getUuid());
    }

    @Override
    protected void updateElement(Resume searchKey, Resume resume) {
        storage.put(resume.getUuid(),resume);
    }

    @Override
    protected void saveElement(Resume resume, Resume searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume findSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAsList() {
        return new ArrayList(storage.values());
    }

}
