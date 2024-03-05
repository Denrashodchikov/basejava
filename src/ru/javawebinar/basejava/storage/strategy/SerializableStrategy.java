package ru.javawebinar.basejava.storage.strategy;

import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializableStrategy {

    void doWrite(Resume resume, OutputStream os) throws IOException;
    Resume doRead(InputStream is) throws IOException;
}
