package com.xpzheng.lang;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class CalendarUtilsTest {

    @Test
    public void testMonthView() throws ParseException {
        int year = 2019;
        DateFormat df = new SimpleDateFormat("yyyy-MM");
        for (int i = 1; i <= 12; i++) {
            List<Date> days = CalenderUtils.monthView(df.parse(String.format("%d-%d", year, i)), true);
            System.out.println(String.format("======================== %d月 ========================", i));
            System.out.println(String.format("%s%20s%20s%20s%20s%20s%20s", "日", "一", "二", "三", "四", "五", "六"));
            for (int j = 0; j < days.size(); j += 7) {
                Object[] arr = new Object[7];
                for (int k = 0; k < 7; k++) {
                    arr[k] = getDate(days.get(j + k));
                }
                System.out.println(String.format("%d%8d%8d%8d%8d%8d%8d", arr));
            }
            System.out.println();
        }
    }

    public int getDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DATE);
    }

}
