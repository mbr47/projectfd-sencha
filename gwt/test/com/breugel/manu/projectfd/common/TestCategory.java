package com.breugel.manu.projectfd.common;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by MBR on 3/16/2015.
 */
public class TestCategory {

    @Test
    public void testFruit() {
        Assert.assertEquals(Category.FRUIT, Category.make("fruit"));
        Assert.assertEquals(Category.FRUIT, Category.make("Fruit"));
        Assert.assertEquals(Category.FRUIT, Category.make("FRUIT"));
    }

    @Test
    public void testOthersSunny() {
        Assert.assertEquals(Category.ALCOHOL, Category.make("alcohol"));
        Assert.assertEquals(Category.COFFEE, Category.make("coffee"));
        Assert.assertEquals(Category.DAIRY, Category.make("dairy"));
        Assert.assertEquals(Category.FATTY_PROTEINS, Category.make("fatty proteins"));
        Assert.assertEquals(Category.FRIED_FOODS, Category.make("fried foods"));
        Assert.assertEquals(Category.LEAN_MEATS_FISH, Category.make("lean meats fish"));
        Assert.assertEquals(Category.NUTS_SEEDS, Category.make("nuts seeds"));
        Assert.assertEquals(Category.REFINED_GRAINS, Category.make("refined grains"));
        Assert.assertEquals(Category.SWEETS, Category.make("sweets"));
    }

    @Test(expected = ProjectFDRuntimeException.class)
    public void testUnknownCategoryShouldThrow() {
        Category.make("foobar");
    }

}
