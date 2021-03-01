package com.zuhlke.assignment.utils.utils;

import com.zuhlke.assignment.utils.DateFormatter;

import org.junit.Test;

import static org.junit.Assert.*;


public class DateFormatterTest {

    @Test
    public void dateAndTimeTest() {
        DateFormatter formatter = new DateFormatter();
        String out = formatter.getFormatterDate();
        assertNotNull(out);
        assertTrue(out.contains("T"));
    }
}
