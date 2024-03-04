package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path directory;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "dir must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " directory is not readeble/writeble");
        }
    }

    @Override
    protected List<Resume> getAsList() {
        if (directory == null) {
            throw new StorageException("Directory is empty: ", directory.toString());
        }
        try {
            List<Resume> resumesList = new ArrayList<>();
            Files.list(directory).forEach(path -> resumesList.add(getElement(path)));
            return resumesList;
        } catch (IOException e) {
            throw new StorageException("Error get as list ", directory.toString());
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void removeElement(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new StorageException("Path doesn't delete : ", path.toString());
        }
    }

    @Override
    protected void updateElement(Path path, Resume resume) {
        try {
            doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("IO Error", path.toString(), e);
        }
    }

    @Override
    protected Resume getElement(Path path) {
        try {
            return doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Unable to read Path:", path.toString());
        }
    }

    @Override
    protected void saveElement(Resume resume, Path path) {
        updateElement(path, resume);
    }

    @Override
    protected Path findSearchKey(String uuid) {
        return Paths.get(directory.toAbsolutePath()+ "/" + uuid);
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::removeElement);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Directory is empty: ", directory.toString());
        }
    }

    protected abstract void doWrite(Resume resume, OutputStream path) throws IOException;

    protected abstract Resume doRead(InputStream path) throws IOException;

}
