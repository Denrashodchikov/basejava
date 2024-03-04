package ru.javawebinar.basejava.model;

import java.util.List;

public class Company extends Section {
    private static final long serialVersionUID = 1L;

    private Link homePage;
    private List<Period> periods;

    public Company(Link homePage, List<Period> periods) {
        this.homePage = homePage;
        this.periods = periods;
    }

    public Company() {
    }

    public Link getHomePage() {
        return homePage;
    }

    public void setHomePage(Link homePage) {
        this.homePage = homePage;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void setPeriods(List<Period> periods) {
        this.periods = periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (!homePage.equals(company.homePage)) return false;
        return periods.equals(company.periods);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + periods.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "\nCompany{" +
                "homePage=" + homePage +
                ", periods=" + periods +
                '}';
    }
}
