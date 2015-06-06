package com.breugel.manu.projectfd.server;

import com.breugel.manu.projectfd.common.Category;
import com.breugel.manu.projectfd.common.Consumption;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by MBR on 3/15/2015.
 */
public class TestDqs {

    private ConsumptionFactory cf;
    private Dqs uut;

    @Before
    public void setUp() {
        HashMap<String, Category> cats = new HashMap<>();
        cats.put("apple", Category.FRUIT);
        cats.put("wine", Category.ALCOHOL);
        cats.put("cava", Category.ALCOHOL);
        cats.put("mandarin", Category.FRUIT);
        cats.put("banana", Category.FRUIT);

        cf = new ConsumptionFactory(cats);
        uut = new Dqs();
    }

    @Test
    public void alcoholFirstConsumptionZero(){
        Consumption c = cf.create("cava");
        int score = uut.calculate(c, Arrays.asList(cf.create("apple")));

        Assert.assertEquals(0, score);
    }

    @Test
    public void alcoholSecondConsumptionNegative2() {
        Consumption c = cf.create("cava");
        List<Consumption> previousConsumptions =  Arrays.asList(cf.create("wine"));
        int score = uut.calculate(c, previousConsumptions);

        Assert.assertEquals(-2, score);
    }

    @Test
    public void fruitFirstConsumptionTwo() {
        Consumption c = cf.create("apple");
        int score = uut.calculate(c, Collections.<Consumption>emptyList());

        Assert.assertEquals(2, score);
    }

    @Test
    public void fruitSecondConsumptionTwo() {
        Consumption c = cf.create("mandarin");
        int score = uut.calculate(c, Arrays.asList(cf.create("apple")));

        Assert.assertEquals(2, score);
    }

    @Test
    public void fruitThirdConsumptionTwo() {
        Consumption c = cf.create("mandarin");
        int score = uut.calculate(c, Arrays.asList(cf.create("apple"), cf.create("banana")));

        Assert.assertEquals(2, score);
    }


    @Test
    public void fruitFourthConsumptionOne() {
        Consumption c = cf.create("mandarin");
        int score = uut.calculate(c, Arrays.asList(cf.create("apple"), cf.create("banana"), cf.create("banana")));

        Assert.assertEquals(1, score);
    }

    @Test
    public void fruitFifthConsumptionZero() {
        Consumption c = cf.create("mandarin");
        int score = uut.calculate(c, Arrays.asList(cf.create("apple"), cf.create("banana"), cf.create("banana"), cf.create("apple")));

        Assert.assertEquals(0, score);
    }

    @Test
    public void fruitSixthConsumptionZero() {
        Consumption c = cf.create("mandarin");
        int score = uut.calculate(c, Arrays.asList(cf.create("apple"), cf.create("banana"), cf.create("banana"), cf.create("apple"), cf.create("banana")));

        Assert.assertEquals(0, score);
    }

    @Test
    public void fruitSeventhConsumptionZero() {
        Consumption c = cf.create("mandarin");
        int score = uut.calculate(c, Arrays.asList(cf.create("apple"), cf.create("banana"), cf.create("banana"), cf.create("apple"), cf.create("banana"), cf.create("apple")));

        Assert.assertEquals(0, score);
    }

    @Test
    public void fruitEightConsumptionZero() {
        Consumption c = cf.create("mandarin");
        int score = uut.calculate(c, Arrays.asList(cf.create("apple"), cf.create("banana"), cf.create("banana"), cf.create("apple"), cf.create("banana"), cf.create("apple"), cf.create("mandarin")));

        Assert.assertEquals(0, score);
    }

    @Test
    public void ignoreNullCategory() {
        Consumption c = cf.create("cava");

        Consumption a = cf.create("apple");
        a.setCategory(null);

        int score = uut.calculate(c, Arrays.asList(a));
        Assert.assertEquals(0, score);
    }

}
