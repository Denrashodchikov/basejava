package ru.javawebinar.basejava.storage;


import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;


@Suite
@SelectClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapUuidStorageTest.class,
        FileStorageTest.class,
        PathStorageTest.class,
        MapResumeStorageTest.class,
        XmlPathStorageTest.class})
public class SuiteTestClass {

}