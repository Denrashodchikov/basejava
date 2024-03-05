package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;

    private final SerializableStrategy serializableStrategy;


    protected FileStorage(File directory, SerializableStrategy serializableStrategy) {
        Objects.requireNonNull(directory, "dir must not be null");
        Objects.requireNonNull(serializableStrategy, "serializableStrategy must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " directory is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " directory is not readeble/writeble");
        }
        this.directory = directory;
        this.serializableStrategy = serializableStrategy;
    }

    @Override
    protected List<Resume> getAsList() {
        File[] listFiles = directory.listFiles();
        if (listFiles == null) {
            throw new StorageException("Directory is empty: ", directory.getName());
        }
        List<Resume> resumesList = new ArrayList<>();
        for (File f : listFiles) {
            resumesList.add(getElement(f));
        }
        return resumesList;
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void removeElement(File file) {
        if (!file.delete()) {
            throw new StorageException("File doesn't delete : ", file.getName());
        }
    }

    @Override
    protected void updateElement(File file, Resume resume) {
        try {
            serializableStrategy.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO Error", file.getName(), e);
        }
    }

    @Override
    protected Resume getElement(File file) {
        try {
            return serializableStrategy.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Unable to read file:", file.getName());
        }
    }

    @Override
    protected void saveElement(Resume resume, File file) {
        try {
            file.createNewFile();
            updateElement(file, resume);
        } catch (IOException e) {
            throw new StorageException("IO Error", file.getName(), e);
        }
    }

    @Override
    protected File findSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    public void clear() {
        File[] listFiles = directory.listFiles();
        if (listFiles == null) {
            throw new StorageException("Directory is empty: ", directory.getName());
        }
        for (File f : listFiles) {
            removeElement(f);
        }
    }

    @Override
    public int size() {
        File[] listFiles = directory.listFiles();
        if (listFiles == null) {
            throw new StorageException("Directory is empty: ", directory.getName());
        }
        return listFiles.length;
    }
}
