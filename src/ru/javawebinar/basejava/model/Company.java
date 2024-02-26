package ru.javawebinar.basejava.model;

import java.util.List;

public class Company extends Section {
    public void setName(String name) {
        this.name = name;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setPeriods(List<Period> periods) {
        this.periods = periods;
    }

    private String name;
    private String website;
    private List<Period> periods;

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (!name.equals(company.name)) return false;
        if (website != null ? !website.equals(company.website) : company.website != null) return false;
        return periods != null ? periods.equals(company.periods) : company.periods == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (website != null ? website.hashCode() : 0);
        result = 31 * result + (periods != null ? periods.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "\nCompany{" +
                "name='" + name + '\'' +
                ", website='" + website + '\'' +
                ", periods=" + periods +
                "}\n";
    }
}
