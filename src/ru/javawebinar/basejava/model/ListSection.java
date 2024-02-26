package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection extends Section {
    private List<String> listText = new ArrayList<>();

    public List<String> getListText() {
        return listText;
    }

    public void setListText(List<String> listText) {
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
        return "\nListSection{" +
                "listText=" + listText +
                "}\n";
    }
}
