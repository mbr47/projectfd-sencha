package com.breugel.manu.projectfd.common;

import com.google.gwt.user.client.rpc.IsSerializable;


/**
 * Created by MBR on 3/22/2015.
 */
public class CategoryStatistics implements IsSerializable, Comparable<CategoryStatistics> {

    private Category category;

    private int count;

    private int key;

    public CategoryStatistics() {

    }

    public CategoryStatistics(Category c) {
        category = c;
        count = 0;
        key = c.getId();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void incrementCount() {
        count++;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    @Override
    public int compareTo(CategoryStatistics o) {
        return count - o.count;
    }
}
