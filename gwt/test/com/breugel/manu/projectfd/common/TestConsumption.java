package com.breugel.manu.projectfd.common;

import com.breugel.manu.projectfd.server.Util;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * Created by MBR on 3/29/2015.
 */
public class TestConsumption {

    public Consumption c;

    @Before
    public void setUp() {
        c = new Consumption();
        c.setFood("banaan");
        c.setCategory(Category.FRUIT);
        c.setDate(Util.formatDate(new Date()));
        c.setId(0);
        c.setScore("0");
    }

    @Test(expected = ProjectFDRuntimeException.class)
    public void emptyConsumptionIsInvalid() {
        c = new Consumption();
        c.throwIfInvalid();
    }

    @Test
    public void validConsumptionShouldNotThrow() {
        c.throwIfInvalid();
    }

    @Test(expected = ProjectFDRuntimeException.class)
    public void nullCategoryShouldThrow() {
        c.setCategory(null);
        c.throwIfInvalid();
    }

    @Test(expected = ProjectFDRuntimeException.class)
    public void nullFoodShouldThrow() {
        c.setFood(null);
        c.throwIfInvalid();
    }

    @Test(expected = ProjectFDRuntimeException.class)
    public void emptyFoodShouldThrow() {
        c.setFood("");
        c.throwIfInvalid();
    }

    @Test(expected = ProjectFDRuntimeException.class)
    public void illegalNonNumericScoreValueShouldThrow() {
        c.setScore("abc");
        c.throwIfInvalid();
    }

}
