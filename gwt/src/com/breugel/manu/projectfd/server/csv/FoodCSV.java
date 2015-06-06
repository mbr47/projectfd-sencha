package com.breugel.manu.projectfd.server.csv;

import com.breugel.manu.projectfd.common.Category;
import com.breugel.manu.projectfd.common.ProjectFDRuntimeException;
import com.breugel.manu.projectfd.server.Util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by MBR on 3/16/2015.
 */
public class FoodCSV {

    public Map<String, Category> read() {
        Map<String, Category> result = new HashMap<>();
        try {
            Scanner scanner = new Scanner(new File(Util.FOOD_DATA));
            scanner.useDelimiter(",");

            int i = 0;
            String food = null;
            String categ;

            while (scanner.hasNext()) {
                String val = scanner.next();
                String newline = System.getProperty("line.separator");
                boolean hasNewline = val.contains(newline);

                if (i == 0) {
                    food = val;
                } else if (hasNewline) {
                    String[] spl = val.split(newline);
                    categ = spl[0];
                    result.put(food, Category.make(categ));

                    if (spl.length == 1) {
                        continue;
                    }
                    food = spl[1];
                    i = 0;
                }
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void write(String food, Category category) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(Util.FOOD_DATA, true)))) {
            out.println(food + "," + category);
        } catch (IOException e) {
            throw new ProjectFDRuntimeException("Failed to modify datastore file " + Util.FOOD_DATA);
        }
    }

    public static void main(String [] args) {
        FoodCSV reader = new FoodCSV();
        Map<String, Category> vals = reader.read();
        for (Map.Entry<String, Category> e : vals.entrySet()) {
            System.out.println(e.getKey() + "->" + e.getValue());
        }
    }

}
