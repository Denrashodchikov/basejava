package ru.javawebinar.basejava.model;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<String> listText = new ArrayList<>();

    public ListSection() {
    }

    public ListSection(List<String> listText) {
        this.listText = listText;
    }

    public List<String> getListText() {
        return listText;
    }

    public void setListText(List<String> listText) {
        Objects.requireNonNull(listText, "listText must not be null");
        this.listText = listText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return listText.equals(that.listText);
    }

    @Override
    public int hashCode() {
        return listText.hashCode();
    }

    @Override
    public String toString() {
        return listText.toString().replace("[", "").replace("]", "");
    }
}
