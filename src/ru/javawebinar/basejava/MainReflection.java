package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume();
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);

        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "newuuid");
        System.out.println(r);
        // TODO : invoke r.toString via reflection
        System.out.println("Invoke r.toString via reflection: ");
        System.out.println(r.getClass().getMethod("toString").invoke(r));
    }
}
