package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        //File file = new File("/Users/denisrashodcikov/IdeaProjects/basejava/.gitignore");
        String filePath = "./.gitignore";
        File file = new File(filePath);
        //System.out.println(file.getCanonicalFile());
        File dir = new File("./src/ru/javawebinar/basejava");
        //System.out.println(dir.getCanonicalFile());
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }


        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
