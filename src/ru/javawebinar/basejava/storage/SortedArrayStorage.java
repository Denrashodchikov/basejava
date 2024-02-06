package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int findSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void addNewElement(Resume resume) {
        int insertionIndex = Math.abs(findSearchKey(resume.getUuid()) + 1);//Получаю индекс, который вернул Arrays.binarySearch
        int moveCount = storage.length - 1 - insertionIndex;
        if (moveCount >= 0) {
            System.arraycopy(storage, insertionIndex, storage, insertionIndex + 1, storage.length - 1 - insertionIndex);//Сдивгаю весь массив вправо на 1 элемент
        }
        storage[insertionIndex] = resume;//Делаю вставку нового элемента по ранее полученному индексу
    }

    @Override
    protected void removeElement(int index) {
        if (size - index >= 0) {
            System.arraycopy(storage, index + 1, storage, index, size - index);//Сдивгаю весь массив влево на 1 элемент
        }
    }
}
