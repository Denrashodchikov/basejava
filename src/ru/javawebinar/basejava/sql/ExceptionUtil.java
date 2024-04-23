package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.SQLException;

public class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static StorageException convertException(SQLException e) {
//        if (e instanceof PSQLException) {
//
////            http://www.postgresql.org/docs/9.3/static/errcodes-appendix.html
//            if (e.getSQLState().equals("23505")) {
//                return new ExistStorageException(null);
//            }
//        }
        if (e.getSQLState().startsWith("02000")) {
            return new NotExistStorageException("");
        }
        if (e.getSQLState().startsWith("23505")) {
            return new ExistStorageException("");
        }
        return new StorageException(e);
    }
}
