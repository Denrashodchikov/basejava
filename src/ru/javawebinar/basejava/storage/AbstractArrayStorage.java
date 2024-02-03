package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected int size;
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];

    public int size() {
        return size;
    }

    public final Resume get(String uuid) {
        int index = findSearchKey(uuid);
        if (index == -1) {
            System.out.println("Uuid: " + uuid + " not found!");
            return null;
        }
        return storage[index];
    }

    public void clear() {
        size = 0;
        Arrays.fill(storage, null);
    }

    public final void update(Resume resume) {
        int index = findSearchKey(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            System.out.println("Uuid: " + resume.getUuid() + " not found!");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public final void save(Resume resume) {
        if (size >= STORAGE_LIMIT) {
            System.out.println("Storage overflow!");
        } else if (findSearchKey(resume.getUuid()) >= 0) {
            System.out.println("Resume already exist!");
        } else {
            addNewElement(resume);
            size++;
        }
    }

    public final void delete(String uuid) {
        int index = findSearchKey(uuid);
        if (index >= 0) {
            removeElement(index);
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Element with uuid: " + uuid + " not found.");
        }
    }

    protected abstract int findSearchKey(String uuid);

    protected abstract void addNewElement(Resume resume);

    protected abstract void removeElement(int index);
}
