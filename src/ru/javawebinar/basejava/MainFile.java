package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        //File file = new File("/Users/denisrashodcikov/IdeaProjects/basejava/.gitignore");
        String filePath = "./.gitignore";
        File file = new File(filePath);
        //System.out.println(file.getCanonicalFile());
        File dir = new File("./src/ru/javawebinar/basejava");
        //System.out.println(dir.getCanonicalFile());
        // System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                // System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            //System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //HOMEWORK 8
        System.out.println("_____HOMEWORK 8________");
        File directory = new File("/Users/denisrashodcikov/IdeaProjects/basejava");
        //File directory = new File("/Users/denisrashodcikov/IdeaProjects/basejava/src/ru/javawebinar/basejava");
        getFilesList(directory);

    }

    public static void getFilesList(File file) {
        if (file.isDirectory()) {
            for (File f : Objects.requireNonNull(file.listFiles())) {
                getFilesList(f);
            }
        } else {
            System.out.println(file.getName());
        }
    }
}
