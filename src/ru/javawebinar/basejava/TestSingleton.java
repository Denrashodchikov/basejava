package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.SectionType;

public class TestSingleton {
    private static TestSingleton instance;

    public static TestSingleton getInstance() {
        if (instance == null) {
            instance = new TestSingleton();
        }
        return instance;
    }

    private TestSingleton() {
    }

    public enum Singleton {
        INSTANCE
    }

    public static void main(String[] args) {
        Singleton.valueOf("INSTANCE");

        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
        }

    }
}
