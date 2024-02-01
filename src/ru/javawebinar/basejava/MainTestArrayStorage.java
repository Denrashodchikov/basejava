package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.ArrayStorage;
import ru.javawebinar.basejava.storage.Storage;

/**
 * Test for your ru.javawebinar.basejava.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    private final static Storage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.setUuid("uuid1");
        Resume r2 = new Resume();
        r2.setUuid("uuid2");
        Resume r3 = new Resume();
        r3.setUuid("uuid3");
        Resume r4 = new Resume();
        r4.setUuid("uuid2");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r4);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();

        System.out.println("Test method Update()");
        Resume rUpd = new Resume();
        rUpd.setUuid("uuuuuu");
        ARRAY_STORAGE.update(rUpd);

        System.out.println("Get r2: " + ARRAY_STORAGE.get(r2.getUuid()));

        System.out.println("Size: " + ARRAY_STORAGE.size());
        ARRAY_STORAGE.delete(r3.getUuid());
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
