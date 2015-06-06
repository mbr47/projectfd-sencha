package com.breugel.manu.projectfd.server;

import com.breugel.manu.projectfd.common.Category;
import com.breugel.manu.projectfd.common.Consumption;
import com.breugel.manu.projectfd.common.UnknownFoodException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by MBR on 3/16/2015.
 */
public class TestConsumptionFactory {

    private ConsumptionFactory uut;

    @Before
    public void setUp() {
        HashMap<String, Category> cats = new HashMap<>();
        cats.put("appel", Category.FRUIT);
        cats.put("cava", Category.ALCOHOL);

        uut = new ConsumptionFactory(cats);
    }

    @Test
    public void appelIsFruit() {
        Consumption c = uut.create("appel");

        Assert.assertEquals(Category.FRUIT, c.getCategory());
        Assert.assertEquals(null, c.getScore());
        Assert.assertEquals("appel", c.getFood());
        Assert.assertEquals(null, c.getDate());
    }

    @Test
    public void cavaIsAlcoholUppercaseDontMatter() {
        Consumption c = uut.create("CAVA");

        Assert.assertEquals(Category.ALCOHOL, c.getCategory());
        Assert.assertEquals(null, c.getScore());
        Assert.assertEquals("cava", c.getFood());
        Assert.assertEquals(null, c.getDate());
    }

    @Test(expected = UnknownFoodException.class)
    public void unkownFoodShouldThrow() {
        uut.create("foobar");
    }

}
