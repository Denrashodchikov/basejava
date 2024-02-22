package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected Integer findSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid,"dummy");
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    }

    @Override
    protected void addNewElement(Resume resume, int index) {
        int insertionIndex = Math.abs(index + 1);//Получаю индекс, который вернул Arrays.binarySearch
        int moveCount = storage.length - 1 - insertionIndex;
        if (moveCount >= 0) {
            System.arraycopy(storage, insertionIndex, storage, insertionIndex + 1, storage.length - 1 - insertionIndex);//Сдивгаю весь массив вправо на 1 элемент
        }
        storage[insertionIndex] = resume;//Делаю вставку нового элемента по ранее полученному индексу
    }

    @Override
    protected void remove(int index) {
        if (size - index >= 0) {
            System.arraycopy(storage, index + 1, storage, index, size - index);//Сдивгаю весь массив влево на 1 элемент
        }
    }

}
