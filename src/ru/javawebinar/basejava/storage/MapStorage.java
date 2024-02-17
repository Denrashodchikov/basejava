package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getElement(Object searchKey) {
        return storage.get(searchKey.toString());
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey(searchKey.toString());
    }

    @Override
    protected void removeElement(Object searchKey) {
        storage.remove(searchKey.toString());
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
        return uuid;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

}
