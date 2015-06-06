package com.breugel.manu.projectfd.server;

import com.breugel.manu.projectfd.common.ProjectFDRuntimeException;
import com.breugel.manu.projectfd.server.Util;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MBR on 3/16/2015.
 */
public class TestUtil {

    @Test
    public void isTodayNow() {
        Date now = new Date();
        String nowStr = new SimpleDateFormat(Util.DATE_FORMAT).format(now);
        Assert.assertTrue(Util.isToday(nowStr));
    }

    @Test
    public void isTodayWithin2Minutes() { // this WILL fail if test is run at less than 2 minutes before midnight...
        Date now = new Date();
        now.setTime(now.getTime() + 2*60*1000);
        String nowStr = new SimpleDateFormat(Util.DATE_FORMAT).format(now);

        Assert.assertTrue(Util.isToday(nowStr));
    }

    @Test
    public void isTodayWithYesterday() {
        Date now = new Date();
        now.setTime(now.getTime() - 24*60*60*1000);
        String nowStr = new SimpleDateFormat(Util.DATE_FORMAT).format(now);

        Assert.assertFalse(Util.isToday(nowStr));
    }

    @Test
    public void isPastDay() {
        Date past = new Date();
        past.setTime(past.getTime() - 24*60*60*1000);
        String pastStr = new SimpleDateFormat(Util.DATE_FORMAT).format(past);

        Assert.assertTrue(Util.isPreviousDay(pastStr, 1));
        Assert.assertFalse(Util.isPreviousDay(pastStr, 2));
    }

    @Test(expected = ProjectFDRuntimeException.class)
    public void isTodayWithGarbageShouldThrow() {
        Util.isToday("foobar");
    }

}
