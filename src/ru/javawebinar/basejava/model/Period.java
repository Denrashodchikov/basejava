package ru.javawebinar.basejava.model;

import java.time.LocalDate;

public class Period {
    private LocalDate startDate;
    private LocalDate endDate;
    private String title;
    private String description;

    public Period() {
    }

    public Period(LocalDate startDate, LocalDate endDate, String title, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Period period = (Period) o;

        if (startDate != null ? !startDate.equals(period.startDate) : period.startDate != null) return false;
        if (endDate != null ? !endDate.equals(period.endDate) : period.endDate != null) return false;
        if (title != null ? !title.equals(period.title) : period.title != null) return false;
        return description != null ? description.equals(period.description) : period.description == null;
    }

    @Override
    public int hashCode() {
        int result = startDate != null ? startDate.hashCode() : 0;
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "\nPeriod{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                "}\n";
    }
}
