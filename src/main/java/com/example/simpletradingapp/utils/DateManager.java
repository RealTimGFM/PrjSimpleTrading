package com.example.simpletradingapp.utils;

import java.sql.Date;

public class DateManager {
    private static Date fakeToday = Date.valueOf("2017-01-01");

    public static Date getFakeToday() {
        return fakeToday;
    }

    public static void setFakeToday(Date newDate) {
        fakeToday = newDate;
        System.out.println("🕒 Fake 'today' updated to: " + fakeToday);
    }
}
