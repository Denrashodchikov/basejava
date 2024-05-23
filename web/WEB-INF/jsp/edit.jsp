<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.model.Company" %>
<%@ page import="ru.javawebinar.basejava.model.Section" %>
<%@ page import="ru.javawebinar.basejava.model.CompanySection" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" required name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>

        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <c:set var="sectionData" value="${resume.getSection(sectionType)}"/>
            <jsp:useBean id="sectionData" type="ru.javawebinar.basejava.model.Section"/>
            <h2><a>${sectionType.title}</a></h2>
            <c:choose>
                <c:when test="${sectionType=='OBJECTIVE' || sectionType=='PERSONAL' || sectionType=='QUALIFICATIONS' || sectionType=='ACHIEVEMENT'}">
                    <textarea name="${sectionType.title}" rows="4"
                              cols="100">${resume.getSection(sectionType)}</textarea>
                </c:when>
                <c:when test="${sectionType=='EXPERIENCE' || sectionType=='EDUCATION'}">
                        <c:forEach var="company" items="<%=((CompanySection) sectionData).getCompanies()%>" varStatus="company_index">
                            <dl>
                                <dt>Название компании:</dt>
                                <dd><input type="text" name="${sectionType.title}" size=30 value="${company.homePage.name}"></dd>
                            </dl>
                            <dl>
                                <dt>Сайт компании:</dt>
                                <dd><input type="text" name="${sectionType.title}${company_index.index}website" size=30 value="${company.homePage.website}"></dd>
                            </dl>
                            <c:forEach var="period" items="${company.periods}" varStatus="period_index">
                                <jsp:useBean id="period" type="ru.javawebinar.basejava.model.Period"/>
                                <dl>
                                    <dt>Начальная дата:</dt>
                                    <dd>
                                        <input type="text" name="${sectionType.title}${company_index.index}startDate${period_index.index}" size=10
                                               value="<%=DateUtil.convert(period.getStartDate())%>" placeholder="MM/yyyy">
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>Конечная дата:</dt>
                                    <dd>
                                        <input type="text" name="${sectionType.title}${company_index.index}endDate${period_index.index}" size=10
                                               value="<%=DateUtil.convert(period.getEndDate())%>" placeholder="MM/yyyy">
                                </dl>
                                <dl>
                                    <dt>Должность:</dt>
                                    <dd><input type="text" name='${sectionType.title}${company_index.index}title${period_index.index}' size=75
                                               value="${period.title}">
                                </dl>
                                <dl>
                                    <dt>Описание:</dt>
                                    <dd><textarea name="${sectionType.title}${company_index.count}description${period_index.index}" rows=5
                                                  cols=75>${period.description}</textarea></dd>
                                </dl>
                            </c:forEach>
                        </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="button" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
