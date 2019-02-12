package com.gates.standstrong.utils;

import java.sql.Date;
import java.util.Calendar;

public class DateUtils {

    public static Date addDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return new Date(c.getTimeInMillis());
    }
}
