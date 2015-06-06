package com.breugel.manu.projectfd.server;

import com.breugel.manu.projectfd.common.ProjectFDRuntimeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MBR on 3/16/2015.
 */
public class Util {

    public static final String DATE_FORMAT = "yyyy-MM-dd-HH:mm";
    public static final String FOOD_DATA = "c:\\Projects\\foods.csv";
    public final static String CONSUMPTIONS_DATA = "c:\\Projects\\consumptions.csv";

    public static String formatDate(Date date) {
        return new SimpleDateFormat(Util.DATE_FORMAT).format(date);
    }

    public static boolean isToday(String date) {
        // validate that date is of DATE_FORMAT format
        try {
            (new SimpleDateFormat(DATE_FORMAT)).parse(date);
        } catch (ParseException e) {
            throw new ProjectFDRuntimeException("Illegal string date format " + date + ". Failed to compare date.", e);
        }

        Date now = new Date();
        String nowStr = (new SimpleDateFormat(DATE_FORMAT)).format(now);
        return date.startsWith(nowStr.substring(0, 10));
    }

    public static boolean isPreviousDay(String date, int daysBack) {
        if (daysBack < 0) {
            throw new IllegalArgumentException("daysBack parameter must be strictly greater than zero");
        }

        // validate that date is of DATE_FORMAT format
        try {
            (new SimpleDateFormat(DATE_FORMAT)).parse(date);
        } catch (ParseException e) {
            throw new ProjectFDRuntimeException("Illegal string date format " + date + ". Failed to compare date.", e);
        }

        Date pastDate = new Date();
        pastDate.setTime(pastDate.getTime() - daysBack*24*60*60*1000);
        String strDate = (new SimpleDateFormat(DATE_FORMAT)).format(pastDate);
        return date.startsWith(strDate.substring(0, 10));

    }
}
