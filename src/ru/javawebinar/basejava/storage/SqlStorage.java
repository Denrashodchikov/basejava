package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume;");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("SELECT * FROM resume r" +
                " LEFT JOIN contact c " +
                "        ON r.uuid = c.resume_uuid" +
                " LEFT JOIN section s " +
                "        ON r.uuid = s.resume_uuid" +
                "     WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, rs.getString("full_name"));
            do {
                setContacts(resume, rs);
                setSections(resume, rs);
            } while (rs.next());

            return resume;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?;")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            deleteFrom(conn, resume, "contact");
            deleteFrom(conn, resume, "section");
            insertContacts(conn, resume);
            insertSection(conn, resume);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            insertContacts(conn, resume);
            insertSection(conn, resume);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.rowDeleted()) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> map = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name,uuid;")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    map.put(rs.getString("uuid"), new Resume(rs.getString("uuid"), rs.getString("full_name")));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact;")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    setContacts(map.get(rs.getString("resume_uuid")), rs);
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section;")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    setSections(map.get(rs.getString("resume_uuid")), rs);
                }
            }
            return map.values().stream().toList();
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return !rs.next() ? 0 : Integer.parseInt(rs.getString(1));
        });
    }

    private void insertContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?);")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void setContacts(Resume resume, ResultSet rs) throws SQLException {
        resume.setContacts(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
    }

    private void insertSection(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type_sec, text) VALUES (?,?,?);")) {
            for (Map.Entry<SectionType, Section> e : resume.getSections().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                switch (SectionType.valueOf(e.getKey().name())) {
                    case PERSONAL, OBJECTIVE -> ps.setString(3, e.getValue().toString());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        StringBuilder str = new StringBuilder();
                        ListSection listSection = (ListSection) e.getValue();
                        for (String s : listSection.getListText()) {
                            str.append(s);
                            str.append("\n");
                        }
                        str.deleteCharAt(str.length() - 1);
                        ps.setString(3, str.toString());
                    }
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteFrom(Connection conn, Resume resume, String table) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM " + table + " WHERE resume_uuid = ?;")) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }

    private void setSections(Resume resume, ResultSet rs) throws SQLException {
        String text = rs.getString("text");
        if (text != null) {
            SectionType sectionType = SectionType.valueOf(rs.getString("type_sec"));
            switch (sectionType) {
                case PERSONAL, OBJECTIVE -> resume.setSections(sectionType, new TextSection(text));
                case ACHIEVEMENT, QUALIFICATIONS -> resume.setSections(sectionType, new ListSection(Arrays.stream(text.split("/n")).toList()));
            }
        }
    }

}
