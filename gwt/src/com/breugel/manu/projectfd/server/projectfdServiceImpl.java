package com.breugel.manu.projectfd.server;

import com.breugel.manu.projectfd.common.*;
import com.breugel.manu.projectfd.server.csv.ConsumptionCSV;
import com.breugel.manu.projectfd.server.csv.FoodCSV;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.breugel.manu.projectfd.client.projectfdService;

import java.util.*;

public class projectfdServiceImpl extends RemoteServiceServlet implements projectfdService {

    @Override
    public Consumption addConsumption(String food, Category value) {
        List<Consumption> previousConsumptions = readAllPreviousConsumptions();
        List<Consumption> todaysConsumptions = getTodaysConsumptions(previousConsumptions);

        ConsumptionFactory cf = new ConsumptionFactory((new FoodCSV()).read());
        Consumption c;

        Category storedCat = cf.isKnownFood(food);
        if (storedCat == null) {
            FoodCSV csv = new FoodCSV();
            csv.write(food, value);
            c = cf.create(food, value);
        } else {
            c = cf.create(food);
            if (storedCat != value) {
                c.setCategory(value);
            }
        }
        int foodScore = (new Dqs()).calculate(c, todaysConsumptions);
        String timestamp = Util.formatDate(new Date());

        c.setScore(String.valueOf(foodScore));
        c.setDate(timestamp);

        c.throwIfInvalid();

        ConsumptionCSV csv = new ConsumptionCSV();
        csv.write(c);

        return c;
    }

    @Override
    public List<Consumption> getTodaysConsumptions() {
        List<Consumption> previousConsumptions = readAllPreviousConsumptions();
        return getTodaysConsumptions(previousConsumptions);
    }

    @Override
    public List<Consumption> getPreviousDaysConsumptions() {
        List<Consumption> previousConsumptions = readAllPreviousConsumptions();
        List<Consumption> results = new ArrayList<>();
        for (Consumption c : previousConsumptions) {
            for (int i=1; i<8; i++) {  // result is from 7 days max ago
                if (Util.isPreviousDay(c.getDate(), i)){
                    results.add(c);
                    break;
                }
            }
        }
        return results;
    }

    @Override
    public Category getCategoryFor(String food) throws UnknownFoodException {
        ConsumptionFactory cf = new ConsumptionFactory((new FoodCSV()).read());
        Consumption c = cf.create(food);
        return c.getCategory();
    }

    @Override
    public List<CategoryStatistics> getCategoryStatistics() {
        List<Consumption> previousConsumptions = readAllPreviousConsumptions();
        List<Consumption> consumptionsInPastMonth = new ArrayList<>();
        for (Consumption c : previousConsumptions) {
            for (int i=0; i<31; i++) {  // result is from the last month
                if (Util.isPreviousDay(c.getDate(), i)){
                    consumptionsInPastMonth.add(c);
                    break;
                }
            }
        }


        Map<Category, CategoryStatistics> result = new HashMap<>();
        for (Category c : Category.all()) {
            result.put(c, new CategoryStatistics(c));
        }

        for (Consumption c : consumptionsInPastMonth) {
            result.get(c.getCategory()).incrementCount();
        }

        return new ArrayList(result.values());
    }

    private List<Consumption> getTodaysConsumptions(Iterable<Consumption> previousConsumptions) {
        List<Consumption> result = new ArrayList<>();
        for (Consumption c : previousConsumptions) {
            if (Util.isToday(c.getDate())) {
                result.add(c);
            }
        }
        return result;
    }

    public List<Consumption> readAllPreviousConsumptions() {
        ConsumptionCSV csv = new ConsumptionCSV();
        return csv.read();
    }

}