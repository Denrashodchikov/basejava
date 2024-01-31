package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int size;
    final int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];

    public void clear() {
        size = 0;
        Arrays.copyOf(storage, size);
    }

    public void update(Resume resume) {
        int id = findSearchKey(resume.getUuid());
        if (id >= 0) {
            storage[id] = resume;
        } else {
            System.out.println("Uuid: " + resume.getUuid() + " not found!");
        }
    }

    public void save(Resume resume) {
        if (size >= storage.length) {
            System.out.println("Storage is full!");
        } else if (findSearchKey(resume.getUuid()) >= 0) {
            System.out.println("Resume already exist!");
        } else {
            storage[size] = resume;
            size++;
        }
    }

    public Resume get(String uuid) {
        int id = findSearchKey(uuid);
        if (id >= 0) {
            return storage[id];
        } else {
            System.out.println("Uuid: " + uuid + " not found!");
            return null;
        }
    }

    public void delete(String uuid) {
        int id = findSearchKey(uuid);
        if (id >= 0) {
            storage[id] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int findSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
