package com.breugel.manu.projectfd.server;

import com.breugel.manu.projectfd.common.Category;
import com.breugel.manu.projectfd.common.Consumption;
import com.breugel.manu.projectfd.common.UnknownFoodException;

import java.util.*;

/**
 * Created by MBR on 3/15/2015.
 */
public class ConsumptionFactory {

    private Map<String, Category> map = null;

    private static int idCounter = 0;

    public ConsumptionFactory(Map<String, Category> map) {
        this.map = map;
    }

    public Consumption create(String food) {
        Consumption c = new Consumption();
        food = food.toLowerCase();
        c.setFood(food);
        if (!map.containsKey(food)) {
            throw new UnknownFoodException("Unknown food type " + food + ". Failed to make consumption");
        }

        Category cat = map.get(food);
        c.setCategory(cat);

        c.setId(idCounter++);

        return c;
    }

    public Category isKnownFood(String food) {
        return map.get(food);
    }

    public static int nextId() {
        return idCounter++;
    }

    public Consumption create(String food, Category value) {
        Consumption c = new Consumption();
        food = food.toLowerCase();
        c.setFood(food);
        c.setCategory(value);
        c.setId(idCounter++);
        return c;
    }
}