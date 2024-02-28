package ru.javawebinar.basejava.util;

import java.time.LocalDate;
import java.time.Month;

public class DateUtil {
    public static LocalDate of(int year, Month month){
        LocalDate.of(year, month, 1);
        return null;
    }
}
