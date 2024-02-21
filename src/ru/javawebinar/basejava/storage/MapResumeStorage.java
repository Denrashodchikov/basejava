package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getElement(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected void removeElement(Object searchKey) {
        storage.remove(((Resume) searchKey).getUuid());
    }

    @Override
    protected void updateElement(Object searchKey, Resume resume) {
        storage.put(resume.getUuid(),resume);
    }

    @Override
    protected void saveElement(Resume resume, Object searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Object findSearchKey(String uuid) {
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
