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
import java.util.Arrays;

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
            case "view", "edit" -> resume = storage.get(uuid);
            case "create" -> resume = new Resume("", "");
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
}
