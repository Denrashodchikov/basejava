package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void execute(String query) {
        execute(query, PreparedStatement::execute);
    }

    public <T> T execute(String sql, SqlExecutor<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return executor.execute(ps);
        } catch (SQLException e) {
            throw getException(e);
        }
    }

    public interface SqlExecutor<T> {
        T execute(PreparedStatement st) throws SQLException;
    }

    private StorageException getException(SQLException e){
        if (e.getSQLState().startsWith("02000")) {
             return new NotExistStorageException("");
        }
        if (e.getSQLState().startsWith("23505")) {
            return new ExistStorageException("");
        }
        return new StorageException(e);
    }
}
