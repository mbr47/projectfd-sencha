package com.breugel.manu.projectfd.common;

import java.io.Serializable;

/**
 * Created by MBR on 3/15/2015.
 */
public class Consumption implements Serializable {

    private int id;
    private String food;
    private Category category;
    private String score;
    private String date;

    public Consumption() {
    }

    public String getDateDay() {
        if (date != null) {
            return date.substring(0, 10);
        }   else {
            return "";
        }
    }

    /**
     * @throws com.breugel.manu.projectfd.common.ProjectFDRuntimeException if object is in a corrupt state
     */
    public void throwIfInvalid() {
        if (food == null || food.isEmpty()) {
            throw new ProjectFDRuntimeException("Illegal Consumption. Empty food is not allowed.");
        }
        if (category == null) {
            throw new ProjectFDRuntimeException("Illegal Consumption. Null category is not allowed");
        }
        if (score == null) {
            throw new ProjectFDRuntimeException("Illegal Consumption. Null score is not allowed.");
        }
        try {
            //noinspection ResultOfMethodCallIgnored
            Integer.valueOf(score);
        } catch (NumberFormatException nfe) {
           throw new ProjectFDRuntimeException("Illegal Consumption. Failed to parse integer from score: >" + score + "<", nfe);
        }
        if (date == null) {
            throw new ProjectFDRuntimeException();
        }
    }

    @Override
    public String toString() {
        return "Consumption{" +
                "food='" + food + '\'' +
                ", category='" + category + '\'' +
                ", score='" + score + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getFood() {
        return food;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScore() {
        return score;
    }

    @SuppressWarnings("unused") // Used by ConsumptionProperties.intScore
    public int getIntScore() {
        return Integer.valueOf(score);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @SuppressWarnings("unused") // Used by ConsumptionProperties.key
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
