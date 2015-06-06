package com.breugel.manu.projectfd.client;

import com.breugel.manu.projectfd.common.Category;
import com.breugel.manu.projectfd.common.CategoryStatistics;
import com.breugel.manu.projectfd.common.Consumption;
import com.breugel.manu.projectfd.common.UnknownFoodException;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("projectfdService")
public interface projectfdService extends RemoteService {
    // Sample interface method of remote interface
    Consumption addConsumption(String foodType, Category value);
    List<Consumption> getTodaysConsumptions();
    List<Consumption> getPreviousDaysConsumptions();
    Category getCategoryFor(String foodType) throws UnknownFoodException;
    List<CategoryStatistics> getCategoryStatistics();

    /**
     * Utility/Convenience class.
     * Use projectfdService.App.getInstance() to access static instance of projectfdServiceAsync
     */
    public static class App {
        private static projectfdServiceAsync ourInstance = GWT.create(projectfdService.class);
        public static synchronized projectfdServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
