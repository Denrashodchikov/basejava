package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

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
        Arrays.fill(storage, null);
    }

    public void update(Resume resume) {
        int index = findSearchKey(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            System.out.println("Uuid: " + resume.getUuid() + " not found!");
        }
    }

    public void save(Resume resume) {
        if (size >= storage.length) {
            System.out.println("Storage overflow!");
        } else if (findSearchKey(resume.getUuid()) >= 0) {
            System.out.println("Resume already exist!");
        } else {
            storage[size] = resume;
            size++;
        }
    }

    public Resume get(String uuid) {
        int index = findSearchKey(uuid);
        if (index == -1) {
            System.out.println("Uuid: " + uuid + " not found!");
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
        int index = findSearchKey(uuid);
        if (index >= 0) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Element with uuid: " + uuid + " not found.");
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
