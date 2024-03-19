package ru.javawebinar.basejava.storage.strategy;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DateUtil;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class DataStreamSerializer implements SerializableStrategy {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            //TODO implements sections
            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> dos.writeUTF(((TextSection) entry.getValue()).getText());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        ListSection listSection = (ListSection) entry.getValue();
                        dos.writeInt(listSection.getListText().size());
                        for (String s : listSection.getListText()) {
                            dos.writeUTF(s);
                        }
                    }
                    case EXPERIENCE, EDUCATION -> {
                        CompanySection companySection = (CompanySection) entry.getValue();
                        dos.writeInt(companySection.getCompanies().size());
                        for (Company company : companySection.getCompanies()) {
                            dos.writeUTF(company.getHomePage().getName());
                            dos.writeUTF(company.getHomePage().getWebsite());
                            List<Period> periodList = company.getPeriods();
                            dos.writeInt(periodList.size());
                            for (Period period : periodList) {
                                dos.writeUTF(period.getStartDate().toString());
                                dos.writeUTF(period.getEndDate().toString());
                                dos.writeUTF(period.getTitle());
                                dos.writeUTF(period.getDescription());
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.setContacts(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            //TODO implements sections
            int sizeSec = dis.readInt();
            for (int i = 0; i < sizeSec; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> resume.setSections(sectionType, new TextSection(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        int sizeListSec = dis.readInt();
                        ListSection listSection = new ListSection();
                        List<String> list = new ArrayList<>();
                        for (int j = 0; j < sizeListSec; j++) {
                            list.add(dis.readUTF());
                        }
                        listSection.setListText(list);
                        resume.setSections(sectionType, listSection);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        int sizeComp = dis.readInt();
                        CompanySection companySection = new CompanySection();
                        List<Company> companyList = new ArrayList<>();
                        for (int j = 0; j < sizeComp; j++) {
                            Company company = new Company();
                            company.setHomePage(new Link(dis.readUTF(), dis.readUTF()));
                            int sizePeriods = dis.readInt();
                            List<Period> periodList = new ArrayList<>();
                            for (int k = 0; k < sizePeriods; k++) {
                                periodList.add(new Period(parseDate(dis.readUTF()), parseDate(dis.readUTF()), dis.readUTF(), dis.readUTF()));
                            }
                            company.setPeriods(periodList);
                            companyList.add(company);
                        }
                        companySection.setCompanies(companyList);
                        resume.setSections(sectionType, companySection);
                    }
                }
            }
            return resume;
        }
    }

    private LocalDate parseDate(String date) {
        String[] arrDt = date.split("-");
        return DateUtil.of(Integer.parseInt(arrDt[0]), Month.of(Integer.parseInt(arrDt[1])));
    }


}
