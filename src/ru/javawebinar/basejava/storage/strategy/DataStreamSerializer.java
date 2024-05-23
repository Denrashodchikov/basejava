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
            writeWithException(resume.getContacts().entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            writeWithException(resume.getSections().entrySet(), dos, entry -> {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> dos.writeUTF(((TextSection) entry.getValue()).getText());
                    case ACHIEVEMENT, QUALIFICATIONS -> writeWithException(((ListSection) entry.getValue()).getListText(), dos, s -> dos.writeUTF(String.valueOf(s)));
                    case EXPERIENCE, EDUCATION -> writeWithException(((CompanySection) entry.getValue()).getCompanies(), dos, company -> {
                        dos.writeUTF(company.getHomePage().getName());
                        dos.writeUTF(company.getHomePage().getWebsite());
                        writeWithException(company.getPeriods(), dos, period -> {
                            dos.writeUTF(period.getStartDate().toString());
                            dos.writeUTF(period.getEndDate().toString());
                            dos.writeUTF(period.getTitle());
                            dos.writeUTF(period.getDescription() == null ? "" : period.getDescription());
                            //dos.writeUTF(period.getDescription());
                        });
                    });
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            readSection(dis, () -> resume.setContacts(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readSection(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> resume.setSections(sectionType, new TextSection(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        ListSection listSection = new ListSection();
                        listSection.setListText(readWithException(dis, dis::readUTF));
                        resume.setSections(sectionType, listSection);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        CompanySection companySection = new CompanySection();
                        companySection.setCompanies(readWithException(dis, () -> new Company(new Link(dis.readUTF(), dis.readUTF()), readWithException(dis, () -> new Period(parseDate(dis.readUTF()), parseDate(dis.readUTF()), dis.readUTF(), dis.readUTF())))));
                        resume.setSections(sectionType, companySection);
                    }
                }
            });
            return resume;
        }
    }

    private LocalDate parseDate(String date) {
        String[] arrDt = date.split("-");
        return DateUtil.of(Integer.parseInt(arrDt[0]), Month.of(Integer.parseInt(arrDt[1])));
    }

    @FunctionalInterface
    public interface FIWrite<T> {
        void write(T t) throws IOException;
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, FIWrite<T> fiw) throws IOException {
        Objects.requireNonNull(collection);
        int size = collection.size();
        dos.writeInt(size);
        for (T t : collection) {
            fiw.write(t);
        }
    }

    @FunctionalInterface
    public interface FIRead<T> {
        T read() throws IOException;
    }

    private <T> List<T> readWithException(DataInputStream dis, FIRead<T> fir) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>();
        for (int k = 0; k < size; k++) {
            list.add(fir.read());
        }
        return list;
    }

    @FunctionalInterface
    public interface FIReadSec<T> {
        void read() throws IOException;
    }

    private <T> void readSection(DataInputStream dis, FIReadSec<T> fir) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            fir.read();
        }
    }
}
