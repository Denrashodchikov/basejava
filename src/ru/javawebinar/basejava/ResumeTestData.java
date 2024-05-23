package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DateUtil;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.basejava.model.SectionType.*;

public class ResumeTestData {
    public static Resume createResume(String uuid, String fullName){
        Resume resume = new Resume(uuid,fullName);

        resume.setContacts(ContactType.PHONE, "8800553535");
        resume.setContacts(ContactType.SKYPE, "grigoriy");
        resume.setContacts(ContactType.EMAIL, "kislin@gmail.com");

        //TextSection
        TextSection textSection_1 = new TextSection();
        textSection_1.setText("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        TextSection textSection_2 = new TextSection();
        textSection_2.setText("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        //ListSection
        ListSection listSection_1 = new ListSection();
        List<String> list_1 = new ArrayList<>();
        list_1.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        list_1.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.");
        list_1.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        list_1.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        list_1.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        list_1.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        list_1.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        listSection_1.setListText(list_1);


        ListSection listSection_2 = new ListSection();
        List<String> list_2 = new ArrayList<>();
        list_2.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        list_2.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        list_2.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        list_2.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        list_2.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        list_2.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        list_2.add("Python: Django.");
        list_2.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        list_2.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        list_2.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        list_2.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
        list_2.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer");
        list_2.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
        list_2.add("Родной русский, английский \"upper intermediate\"");
        listSection_2.setListText(list_2);

        //CompanySection
        CompanySection experienceSection = new CompanySection();
        List<Company> companyList = new ArrayList<>();
        Company company_1 = new Company();
        Period period_1 = new Period();
        period_1.setStartDate(DateUtil.of(2013, Month.OCTOBER));
        period_1.setEndDate(DateUtil.NOW);
        period_1.setTitle("Автор проекта.");
        period_1.setDescription("Создание, организация и проведение Java онлайн проектов и стажировок.");
        company_1.setHomePage(new Link("Java Online Projects","https://javaops.ru/"));
        //company_1.setHomePage(new Link("",""));
        company_1.setPeriods(List.of(period_1));
        companyList.add(company_1);

        Company company_2 = new Company();
        Period period_2 = new Period();
        period_2.setStartDate(DateUtil.of(2014, Month.OCTOBER));
        period_2.setEndDate(DateUtil.of(2016,Month.JANUARY));
        period_2.setTitle("Старший разработчик (backend)");
        period_2.setDescription("Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        company_2.setHomePage(new Link("Wrike","https://www.wrike.com/"));
        company_2.setPeriods(List.of(period_2));
        companyList.add(company_2);

        Company company_3 = new Company();
        company_3.setHomePage(new Link("Wrike","https://www.wrike.com/"));
        company_3.setPeriods(List.of(new Period(DateUtil.of(2012,Month.JANUARY),DateUtil.of(2014,Month.OCTOBER),"Java архитектор","Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python")));
        companyList.add(company_3);

        experienceSection.setCompanies(companyList);


        //EDUCATION
        CompanySection educationSection = new CompanySection();
        List<Company> companyList2 = new ArrayList<>();
        Company edu_1 = new Company();
        edu_1.setHomePage(new Link("Заочная физико-техническая школа при МФТИ","https://mipt.ru/"));
        edu_1.setPeriods(List.of(new Period(DateUtil.of(1984,Month.SEPTEMBER),DateUtil.of(1987,Month.JUNE),"Закончил с отличием","")));
        companyList2.add(edu_1);
        Company edu_2 = new Company();
        edu_2.setHomePage(new Link("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики","http://www.ifmo.ru/"));
        edu_2.setPeriods(List.of(new Period(DateUtil.of(1993,Month.SEPTEMBER),DateUtil.of(1996,Month.JULY),"Аспирантура (программист С, С++)",""),
                new Period(DateUtil.of(1987,Month.SEPTEMBER),DateUtil.of(1993,Month.JULY),"Инженер (программист Fortran, C)","")));
        companyList2.add(edu_2);
        Company edu_3 = new Company();
        edu_3.setHomePage(new Link("Alcatel","http://www.alcatel.ru/"));
        edu_3.setPeriods(List.of(new Period(DateUtil.of(1997,Month.SEPTEMBER),DateUtil.of(1998,Month.MARCH),"6 месяцев обучения цифровым телефонным сетям (Москва)","")));
        companyList2.add(edu_3);
        Company edu_4 = new Company();
        edu_4.setHomePage(new Link("Siemens AG","http://www.siemens.ru/"));
        edu_4.setPeriods(List.of(new Period(DateUtil.of(2005,Month.JANUARY),DateUtil.of(2005,Month.APRIL),"3 месяца обучения мобильным IN сетям (Берлин)","")));
        companyList2.add(edu_4);
        educationSection.setCompanies(companyList2);

        resume.setSections(OBJECTIVE,textSection_1);
        resume.setSections(PERSONAL,textSection_2);
        resume.setSections(ACHIEVEMENT,listSection_1);
        resume.setSections(QUALIFICATIONS,listSection_2);
        resume.setSections(EXPERIENCE,experienceSection);
        resume.setSections(EDUCATION,educationSection);

        resume.setSections(OBJECTIVE,textSection_1);
        resume.setSections(PERSONAL,textSection_2);
        resume.setSections(ACHIEVEMENT,listSection_1);
        resume.setSections(QUALIFICATIONS,listSection_2);
        return resume;
    }
}
