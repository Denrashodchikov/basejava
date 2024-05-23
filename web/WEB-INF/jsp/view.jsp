<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="static ru.javawebinar.basejava.model.SectionType.OBJECTIVE" %>
<%@ page import="ru.javawebinar.basejava.model.CompanySection" %>
<%@ page import="ru.javawebinar.basejava.model.TextSection" %>
<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
            <c:if test="${contactEntry.value!=''}">
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
            </c:if>
        </c:forEach>
    <p>
    <br>


    <c:forEach var="sectionType" items="<%=SectionType.values()%>">
        <c:set var="sectionData" value="${resume.getSection(sectionType)}"/>
        <jsp:useBean id="sectionData" type="ru.javawebinar.basejava.model.Section"/>
        <c:if test="${sectionData!=''}">
        <c:choose>
            <c:when test="${sectionType=='OBJECTIVE' || sectionType=='PERSONAL'}">
                    <h2><a>${sectionType.title}</a></h2>
                    <%=((TextSection) sectionData).getText()%>
            </c:when>
            <c:when test="${sectionType=='QUALIFICATIONS' || sectionType=='ACHIEVEMENT'}">
                    <h2><a>${sectionType.title}</a></h2>
                <c:forEach var="item" items="<%=((ListSection) sectionData).getListText()%>">
                    <li>${item}</li>
                </c:forEach>
            </c:when>
            <c:when test="${sectionType=='EXPERIENCE' || sectionType=='EDUCATION'}">
                <c:set var="companyExist" value="<%=((CompanySection) sectionData).getCompanies().get(0).getHomePage().getName()%>"/>
                <c:if test="${companyExist != '' }">
                    <h2><a>${sectionType.title}</a></h2>
                    <c:forEach var="company" items="<%=((CompanySection) sectionData).getCompanies()%>">
                        <h3>${company.homePage.name}</h3>
                        <dl>
                            <dt>Сайт компании:</dt><a>${company.homePage.website}</a>
                        </dl>
                        <c:forEach var="period" items="${company.periods}">
                            <jsp:useBean id="period" type="ru.javawebinar.basejava.model.Period"/>
                            <dl>
                                <dt>Начальная дата:</dt><a><%=DateUtil.convert(period.getStartDate())%></a>
                            </dl>
                            <dl>
                                <dt>Конечная дата:</dt><a><%=DateUtil.convert(period.getEndDate())%></a>
                            </dl>
                            <dl>
                                <dt>Должность:</dt><a>${period.title}</a>
                            </dl>
                            <dl>
                                <dt>Описание:</dt><a>${period.description}</a>
                            </dl>
                        </c:forEach>
                    </c:forEach>
                </c:if>
            </c:when>
        </c:choose>
    </c:if>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
