package ru.javawebinar.basejava.model;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {
    // Unique identifier
    private final String uuid;
    private String fullName;
    private Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);


    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public Resume() {
        this(UUID.randomUUID().toString());
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public String getUuid() {
        return uuid;
    }


    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public void setContacts(Map<ContactType, String> contacts) {
        this.contacts = contacts;
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    public void setSections(Map<SectionType, Section> sections) {
        this.sections = sections;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        if (fullName != null ? !fullName.equals(resume.fullName) : resume.fullName != null) return false;
        if (contacts != null ? !contacts.equals(resume.contacts) : resume.contacts != null) return false;
        return sections != null ? sections.equals(resume.sections) : resume.sections == null;
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (contacts != null ? contacts.hashCode() : 0);
        result = 31 * result + (sections != null ? sections.hashCode() : 0);
        return result;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp == 0 ? uuid.compareTo(o.uuid) : cmp;
    }

    @Override
    public String toString() {
        return "Resume{" +
                "\nuuid='" + uuid + '\'' +
                ", \nfullName='" + fullName + '\'' +
                ", \ncontacts=" + contacts +
                ", \nsections=" + sections +
                '}';
    }
}
