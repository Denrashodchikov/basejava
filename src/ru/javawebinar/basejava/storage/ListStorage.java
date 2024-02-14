package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
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
    protected Resume getElement(int index) {
        return storage.get(index);
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
    public void removeElement(int index) {
        storage.remove(index);
    }

    protected int findSearchKey(String uuid) {
        for (Resume r :
                storage) {
            if (r.getUuid().equals(uuid)) {
                return storage.indexOf(r);
            }
        }
        return -1;
    }

    public void updateElement(int index, Resume resume) {
        storage.set(index, resume);
    }

    public void saveElement(Resume resume) {
        if (findSearchKey(resume.getUuid()) >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            storage.add(resume);
        }
    }


}
