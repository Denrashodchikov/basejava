package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.SortedArrayStorage;
import ru.javawebinar.basejava.storage.Storage;

import java.util.Arrays;

/**
 * Test for your ru.javawebinar.basejava.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    //private final static Storage ARRAY_STORAGE = new ArrayStorage();
    private final static Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        String[] strStorage = {"uuid1","uuid2","uuid2","uuid4","uuid5"};
        System.out.println("index: " + Arrays.binarySearch(strStorage,"uuid2"));//index: 2      Возвращает индекс найденного элемента "uuid2"
        System.out.println("index: " + Arrays.binarySearch(strStorage,0,2,"uuid2"));//index: 1      Возвращает индекс найденного элемента "uuid2" в заданном диапозоне
        System.out.println("index: " + Arrays.binarySearch(strStorage,"uuid3"));//index: -4     Если элемента в массиве нет, то возвращает (-(insertion point) - 1), то есть строка "uuid3" имеет insertion point = 3
        System.out.println("index: " + Arrays.binarySearch(strStorage,"uuid"));//index: -1      => строка "uuid" имеет insertion point = 0


        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Resume r1 = new Resume("uuid1");
        Resume r2 = new Resume("uuid5");
        Resume r3 = new Resume("uuid3");
        Resume r4 = new Resume("uuid2");

        System.out.println(ARRAY_STORAGE.size());
        ARRAY_STORAGE.save(r1);
        System.out.println(ARRAY_STORAGE.size());
        ARRAY_STORAGE.save(r2);
        System.out.println(ARRAY_STORAGE.size());
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r4);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();

        System.out.println("Test method Update()");
        Resume rUpd = new Resume("uuuuuu");
        ARRAY_STORAGE.update(rUpd);

        System.out.println("Get r2: " + ARRAY_STORAGE.get(r2.getUuid()));

        System.out.println("Size: " + ARRAY_STORAGE.size());
        System.out.println("Delete:");
        ARRAY_STORAGE.delete(r4.getUuid());
        printAll();
        System.out.println("Size: " + ARRAY_STORAGE.size());
        ARRAY_STORAGE.clear();
        printAll();
//        System.out.println("Test Full Storage:");
//        System.out.println("Size: " + ARRAY_STORAGE.size());
//        for (int i = 0; i <10002; i++) {
//            Resume r = new Resume();
//            r.setUuid("u"+i);
//            ARRAY_STORAGE.save(r);
//        }
        System.out.println("Size: " + ARRAY_STORAGE.size());

    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
