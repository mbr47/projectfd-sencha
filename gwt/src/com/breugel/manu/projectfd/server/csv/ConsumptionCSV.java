package com.breugel.manu.projectfd.server.csv;

import com.breugel.manu.projectfd.common.Category;
import com.breugel.manu.projectfd.common.Consumption;
import com.breugel.manu.projectfd.common.ProjectFDRuntimeException;
import com.breugel.manu.projectfd.server.ConsumptionFactory;
import com.breugel.manu.projectfd.server.Util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by MBR on 3/16/2015.
 */
public class ConsumptionCSV {

    public List<Consumption> read() {
        List<Consumption> result = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(Util.CONSUMPTIONS_DATA));
            scanner.useDelimiter(",");

            int i = 0;
            Consumption c = null;

            while (scanner.hasNext()) {
                String val = scanner.next();
                String newline = System.getProperty("line.separator");
                boolean hasNewline = val.contains(newline);

                if (i == 0) {
                    c = new Consumption();
                    c.setFood(val);
                } else if (i == 2) {
                    c.setCategory(Category.make(val));
                } else if (i == 1) {
                    c.setDate(val);
                } else if (hasNewline) {
                    String[] spl = val.split(newline);
                    c.setScore(spl[0]);
                    c.setId(ConsumptionFactory.nextId());
                    result.add(c);

                    if (spl.length == 1) {
                        continue;
                    }
                    c = new Consumption();
                    c.setFood(spl[1]);
                    i = 0;
                }
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void write(Consumption c) {
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(Util.CONSUMPTIONS_DATA, true)))) {
            out.println(c.getFood() + "," + c.getDate() + "," + c.getCategory() + "," + c.getScore());
        }catch (IOException e) {
            throw new ProjectFDRuntimeException("Failed to modify datastore file " + Util.CONSUMPTIONS_DATA);
        }
    }
}
