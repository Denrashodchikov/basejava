package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        //String name = request.getParameter("name");
        //response.getWriter().write(name == null ? "Hello resumes!" : "Hello " + name + "!");

        Storage storage = Config.get().getStorage();
        List<Resume> listResumes = storage.getAllSorted();
        PrintWriter out = response.getWriter();
        out.print("""
                <html>
                <style>
                table, th, td {
                  border:1px solid black;
                }
                </style>
                <body>

                <h2>Resumes</h2>

                <table style="width:100%">
                  <tr>
                    <th>Uuid</th>
                    <th>Full name</th>
                  </tr>
                """);
        for (Resume resume : listResumes) {
            out.print(
                    "  <tr>\n" +
                            "    <td>" + resume.getUuid() + "</td>\n" +
                            "    <td>" + resume.getFullName() + "</td>\n" +
                            "  </tr>\n"
            );
        }
        out.print(
                """
                        </table>
                        </body>
                        </html>
                        """);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
