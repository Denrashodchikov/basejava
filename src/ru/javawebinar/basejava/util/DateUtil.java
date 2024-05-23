package ru.javawebinar.basejava.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static LocalDate of(int year, Month month){
        return LocalDate.of(year, month, 1);
    }

    public static String convert(LocalDate localDate){
        return localDate==null ? "" : localDate.equals(NOW) ? "по настоящее время" : localDate.format(DateTimeFormatter.ofPattern("MM/yyyy"));
    }

    public static LocalDate of(String str){
        return str.equals("по настоящее время") ? NOW : YearMonth.parse(str, DateTimeFormatter.ofPattern("MM/yyyy")).atDay(1);
    }
}
