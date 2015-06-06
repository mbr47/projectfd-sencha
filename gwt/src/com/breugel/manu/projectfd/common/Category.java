package com.breugel.manu.projectfd.common;

import java.util.Arrays;
import java.util.List;

/**
 * Created by MBR on 3/15/2015.
 */
public enum Category {
    FRUIT(0),
    DAIRY(1),
    VEGETABLES(2),
    LEAN_MEATS_FISH(3),
    NUTS_SEEDS(4),
    WHOLE_GRAINS(5),

    REFINED_GRAINS(6),
    SWEETS(7),
    FRIED_FOODS(8),
    FATTY_PROTEINS(9),

    ALCOHOL(10),
    COFFEE(11),
    CHOCOLAT(12);

    private final int id;

    private Category(int id) {
        this.id = id;
    }

    public static Category make(String category) {
        category = category.toLowerCase();
        switch (category) {
            case "fruit": return FRUIT;
            case "dairy": return DAIRY;
            case "vegetables": return VEGETABLES;
            case "lean meats fish": return LEAN_MEATS_FISH;
            case "nuts seeds": return NUTS_SEEDS;
            case "whole grains": return WHOLE_GRAINS;
            case "refined grains": return REFINED_GRAINS;
            case "sweets": return SWEETS;
            case "fried foods": return FRIED_FOODS;
            case "fatty proteins": return FATTY_PROTEINS;
            case "alcohol": return ALCOHOL;
            case "coffee": return COFFEE;
            case "chocolat": return CHOCOLAT;
            default: throw new ProjectFDRuntimeException("Unknown category: " + category);
        }
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase().replace("_", " ");
    }

    public String getString() {
        return toString();
    }

    public int getId() {
        return id;
    }

    public static List<Category> all() {
        return Arrays.asList(FRUIT, DAIRY, VEGETABLES, LEAN_MEATS_FISH, NUTS_SEEDS, WHOLE_GRAINS, REFINED_GRAINS, SWEETS, FRIED_FOODS, FATTY_PROTEINS, ALCOHOL, COFFEE, CHOCOLAT );
    }

}
