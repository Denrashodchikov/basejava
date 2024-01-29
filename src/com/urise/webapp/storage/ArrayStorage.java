package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int size;
    private Resume[] storage = new Resume[10000];

    public void clear() {
        size = 0;
        Arrays.copyOf(storage, size);
    }

    public void update(Resume r) {
        if (r != null) {
            int id = findId(r.getUuid());
            if (id >= 0) {
                storage[id] = r;
            } else {
                System.out.println("Uuid: " + r.getUuid() + " not found!");
            }
        } else {
            System.out.println("Resume is null.");
        }
    }

    public void save(Resume r) {
        if (size < storage.length) {
            if (findId(r.getUuid()) <= 0) {
                storage[size] = r;
                size++;
            }
        } else {
            System.out.println("Storage is full!");
        }
    }

    public Resume get(String uuid) {
        int id = findId(uuid);
        if (id >= 0) {
            return storage[id];
        } else {
            System.out.println("Uuid: " + uuid + " not found!");
            return null;
        }
    }

    public void delete(String uuid) {
        int id = findId(uuid);
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
        return Arrays.copyOf(storage,size);
    }

    public int size() {
        return size;
    }

    private int findId(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
