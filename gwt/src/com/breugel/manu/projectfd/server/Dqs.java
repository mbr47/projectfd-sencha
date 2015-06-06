package com.breugel.manu.projectfd.server;

import com.breugel.manu.projectfd.common.Category;
import com.breugel.manu.projectfd.common.Consumption;

import java.util.Collection;
import java.util.HashMap;

/**
 * Computes DQS Diet Quality Score based on category of food and previous consumptions of the same category.
 *
 * Created by MBR on 3/15/2015.
 */
public class Dqs {

    private static final HashMap<Category, int[]> dqs = new HashMap<>();

    static {
        dqs.put(Category.FRUIT, new int[]{2,2,2,1,0,0});
        dqs.put(Category.VEGETABLES, new int[]{2,2,2,1,0,0});
        dqs.put(Category.DAIRY, new int[]{1,1,1,0,-1,-2});
        dqs.put(Category.LEAN_MEATS_FISH, new int[]{2,2,1,0,0,-1});
        dqs.put(Category.WHOLE_GRAINS, new int[]{2,2,1,0,0,-1});
        dqs.put(Category.NUTS_SEEDS, new int[]{2,2,1,0,0,-1});

        dqs.put(Category.REFINED_GRAINS, new int[]{-1, -1, -2, -2, -2, -2});
        dqs.put(Category.FRIED_FOODS, new int[]{-2,-2,-2,-2,-2,-2});
        dqs.put(Category.FATTY_PROTEINS, new int[]{-1,-1,-2,-2,-2,-2});
        dqs.put(Category.SWEETS, new int[]{-2,-2,-2,-2,-2,-2});

        dqs.put(Category.COFFEE, new int[]{0,0,0,0,0,0});
        dqs.put(Category.ALCOHOL, new int[]{0,-2,-2,-2,-2,-2});
        dqs.put(Category.CHOCOLAT, new int[]{0,-2,-2,-2,-2,-2});
    }

    public int calculate(Consumption toCount, Collection<Consumption> previousConsumptions) {
        int count = countPreviousConsumptionsWithSameCategory(toCount, previousConsumptions);
        int[] scores = dqs.get(toCount.getCategory());
        if (count >= 5) {
            count = 5;
        }
        return scores[count];
    }

    private int countPreviousConsumptionsWithSameCategory(Consumption toCount, Collection<Consumption> previousCons) {
        int count = 0;
        for (Consumption c : previousCons) {
            if (c.getCategory() != null && c.getCategory().equals(toCount.getCategory())) {
                count++;
            }
        }
        return count;
    }

}
