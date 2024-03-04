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
        Path[] listFiles = directory.listPaths();
        if (listPaths == null) {
            throw new StorageException("Directory is empty: ", directory.getName());
        }
        List<Resume> resumesList = new ArrayList<>();
        for (Path f : listPaths) {
            resumesList.add(getElement(f));
        }
        return resumesList;
    }

    @Override
    protected boolean isExist(Path Path) {
        return Path.exists();
    }

    @Override
    protected void removeElement(Path Path) {
        if (!Path.delete()) {
            throw new StorageException("Path doesn't delete : ", Path.getName());
        }
    }

    @Override
    protected void updateElement(Path Path, Resume resume) {
        try {
            doWrite(resume, new BufferedOutputStream(new PathOutputStream(Path)));
        } catch (IOException e) {
            throw new StorageException("IO Error", Path.getName(), e);
        }
    }

    @Override
    protected Resume getElement(Path Path) {
        try {
            return doRead(new BufferedInputStream(new PathInputStream(Path)));
        } catch (IOException e) {
            throw new StorageException("Unable to read Path:", Path.getName());
        }
    }


    @Override
    protected void saveElement(Resume resume, Path Path) {
        try {
            Path.createNewPath();
            updateElement(Path, resume);
        } catch (IOException e) {
            throw new StorageException("IO Error", Path.getName(), e);
        }
    }

    @Override
    protected Path findSearchKey(String uuid) {
        return new Path(directory, uuid);
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
        Path[] listPaths = directory.listPaths();
        if (listPaths == null) {
            throw new StorageException("Directory is empty: ", directory.getName());
        }
        return listPaths.length;
    }

    protected abstract void doWrite(Resume resume, OutputStream Path) throws IOException;

    protected abstract Resume doRead(InputStream Path) throws IOException;

}
