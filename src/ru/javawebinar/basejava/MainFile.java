package ru.javawebinar.basejava;

import java.io.File;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
//        //File file = new File("/Users/denisrashodcikov/IdeaProjects/basejava/.gitignore");
//        String filePath = "./.gitignore";
//        File file = new File(filePath);
//        //System.out.println(file.getCanonicalFile());
//        File dir = new File("./src/ru/javawebinar/basejava");
//        //System.out.println(dir.getCanonicalFile());
//        // System.out.println(dir.isDirectory());
//        String[] list = dir.list();
//        if (list != null) {
//            for (String name : list) {
//                // System.out.println(name);
//            }
//        }
//
//        try (FileInputStream fis = new FileInputStream(filePath)) {
//            //System.out.println(fis.read());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        //HOMEWORK 8
        System.out.println("_____HOMEWORK 8,9________");
        //File directory = new File("/Users/denisrashodcikov/IdeaProjects/basejava");
        File directory = new File("/Users/denisrashodcikov/IdeaProjects/basejava/src/ru/javawebinar/basejava");
        getFilesList(directory, "");

    }

    public static void getFilesList(File file, String str) {
        final StringBuilder tab = new StringBuilder();
        tab.append(str);
        if (file.isDirectory()) {
            System.out.println(str + "-" + file.getName() + ":");
            tab.append("\t");
            for (File f : Objects.requireNonNull(file.listFiles())) {
                getFilesList(f, tab.toString());
            }
        } else {
            System.out.println(str + "-" + file.getName());
        }
    }
}
