package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MainCollections {
    private static final String DUMMY = "dummy";
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_5 = "uuid5";
    private static final Resume resume1 = new Resume(UUID_1);
    private static final Resume resume2 = new Resume(UUID_2);
    private static final Resume resume3 = new Resume(UUID_3);
    private static final Resume resume4 = new Resume(UUID_4);
    private static final Resume resume5 = new Resume(UUID_5);

    public static void main(String[] args) {
        Collection<Resume> collection = new ArrayList<>();
        collection.add(resume1);
        collection.add(resume2);
        collection.add(resume3);

        for (Resume r :
                collection) {
            System.out.println(r);
            if (r.getUuid().equals(UUID_1)) {
//                collection.remove(r);
            }
        }
        Iterator<Resume> iterator = collection.iterator();
        while (iterator.hasNext()) {
            Resume r = iterator.next();
            System.out.println(r);
            if (r.getUuid().equals(UUID_1)) {
                iterator.remove();
            }
        }
        System.out.println(collection);


        Map<String, Resume> map = new HashMap<>();
        map.put(UUID_1,resume1);
        map.put(UUID_2,resume2);
        map.put(UUID_3,resume3);

        for (String uuid : map.keySet()) {
            System.out.println(map.get(uuid));
        }

        for (Map.Entry<String, Resume> entry : map.entrySet()){
            System.out.println(entry.getValue());
        }

        List<Resume> list = Arrays.asList(resume1,resume2,resume3);
        list.remove(1);
        System.out.println(list);
    }
}
