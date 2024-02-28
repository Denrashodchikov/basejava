package ru.javawebinar.basejava.model;

public enum ContactType {
    PHONE("Phone"),
    SKYPE("Skype"),
    EMAIL("E-mail"),
    LINKEDIN("LinkedIn"),
    GITHUB("GitHub"),
    STACKOVERFLOW("StackoverFlow"),
    HOMEPAGE("Home Page");

    public final String getTitle() {
        return title;
    }

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

}
