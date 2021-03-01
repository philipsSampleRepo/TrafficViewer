package com.zuhlke.assignment.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

public class DateFormatter {

    @Inject
    public DateFormatter() {
    }

    public String getFormatterDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss");
        return formatter.format(date);
    }
}
