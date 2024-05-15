package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.ResumeTestData;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.model.TextSection;
import ru.javawebinar.basejava.util.JsonParser;


public class JsonParserTest {
    @Test
    public void testResume() {
        Resume R1 = ResumeTestData.createResume("47d9c87e-5f2b-46b1-8574-326df40b6897","fullname");
        String json = JsonParser.write(R1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assertions.assertEquals(R1, resume);
    }

    @Test
    public void write() {
        Section section1 = new TextSection("Objective1");
        String json = JsonParser.write(section1, Section.class);
        System.out.println(json);
        Section section2 = JsonParser.read(json, Section.class);
        Assertions.assertEquals(section1, section2);
    }
}
