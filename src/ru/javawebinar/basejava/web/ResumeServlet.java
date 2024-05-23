package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete" -> {
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            }
            case "view"-> resume = fillResume(storage.get(uuid));
            case "edit"-> resume = fillResume(storage.get(uuid));
            case "create" -> resume = fillResume(null);
            default -> throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r;
        boolean existResume = isNotEmpty(uuid);
        if (existResume) {
            r = storage.get(uuid);
        } else {
            r = new Resume();
        }
        if (isNotEmpty(fullName)) {
            r.setFullName(fullName);
            for (ContactType type : ContactType.values()) {
                String value = request.getParameter(type.name());
                if (isNotEmpty(value)) {
                    r.setContacts(type, value);
                } else {
                    r.getContacts().remove(type);
                }
            }
            for (SectionType type : SectionType.values()) {
                String value = request.getParameter(type.getTitle());
                String[] values = request.getParameterValues(type.getTitle());
                switch (type) {
                    case PERSONAL, OBJECTIVE -> {
                        if (isNotEmpty(value)) {
                            r.setSections(type, new TextSection(value));
                        } else {
                            r.getSections().remove(type);
                        }
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        if (isNotEmpty(value)) {
                            r.setSections(type, new ListSection(Arrays.stream(value.split("\\n")).map(s -> s.replaceAll("([\\r\\n])", "")).filter(s -> !s.equals("")).toList()));
                        } else {
                            r.getSections().remove(type);
                        }
                    }
                    case EDUCATION, EXPERIENCE -> {
                        //
                    }
                }
            }
            if (existResume) {
                storage.update(r);
            } else {
                storage.save(r);
            }
        } else if (existResume) {
            storage.delete(uuid);
        }
        response.sendRedirect("resume");
    }

    private static boolean isNotEmpty(String s) {
        return s != null && s.trim().length() != 0;
    }

    private Resume fillResume(Resume r) {
        Resume resume = r;
        if (resume == null) {
            resume = new Resume("","");
        }
        for (ContactType c : ContactType.values()) {
            if (resume.getContact(c) == null) {
                resume.setContacts(c, "");
            }
        }
        for (SectionType s : SectionType.values()) {
            if (resume.getSection(s) == null) {
                switch (s) {
                    case PERSONAL, OBJECTIVE -> resume.setSections(s, new TextSection(""));
                    case ACHIEVEMENT, QUALIFICATIONS -> resume.setSections(s, new ListSection(new ArrayList<>(1)));
                    case EDUCATION, EXPERIENCE -> resume.setSections(s, new CompanySection(List.of(new Company(new Link("", ""),List.of(new Period(null,null,"",""))))));
                }
            }
        }
        return resume;
    }
}
