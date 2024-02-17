package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected List<Resume> storage = new ArrayList<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getElement(Object searchKey) {
        return storage.get((Integer) searchKey);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    public void removeElement(Object searchKey) {
        storage.remove((int) searchKey);
    }

    protected Object findSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public void updateElement(Object searchKey, Resume resume) {
        storage.set((Integer) searchKey, resume);
    }

    public void saveElement(Resume resume, Object searchKey) {
        storage.add(resume);
    }


}
