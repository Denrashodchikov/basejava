package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int findSearchKey(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void addNewElement(Resume resume) {
        int inputIndex = Math.abs(findSearchKey(resume.getUuid()) + 1);//Получаю индекс, который вернул Arrays.binarySearch
        if (storage.length - 1 - inputIndex >= 0)
            System.arraycopy(storage, inputIndex, storage, inputIndex + 1, storage.length - 1 - inputIndex);//Сдивгаю весь массив вправо на 1 элемент
        storage[inputIndex] = resume;//Делаю вставку нового элемента по ранее полученному индексу
    }

    @Override
    protected void removeElement(int index) {
        if (size - index >= 0) System.arraycopy(storage, index + 1, storage, index, size - index);//Сдивгаю весь массив влево на 1 элемент
    }
}
