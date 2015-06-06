package com.breugel.manu.projectfd.client;

import com.breugel.manu.projectfd.common.Category;
import com.breugel.manu.projectfd.common.CategoryStatistics;
import com.breugel.manu.projectfd.common.Consumption;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

@SuppressWarnings("ALL")
public interface projectfdServiceAsync {
    void addConsumption(String foodType, Category value, AsyncCallback<Consumption> async);
    void getTodaysConsumptions(AsyncCallback<List<Consumption>> async);
    void getPreviousDaysConsumptions(AsyncCallback<List<Consumption>> async);
    void getCategoryFor(String foodType, AsyncCallback<Category> async);
    void getCategoryStatistics(AsyncCallback<List<CategoryStatistics>> async);
}
