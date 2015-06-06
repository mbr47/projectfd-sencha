package com.breugel.manu.projectfd.client;

import com.breugel.manu.projectfd.common.CategoryStatistics;
import com.google.gwt.editor.client.Editor;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * Created by MBR on 3/22/2015.
 */
public interface CategoryStatisticsProperties extends PropertyAccess<CategoryStatistics> {

    @Editor.Path("category.string")
    ValueProvider<CategoryStatistics, String> categoryString();

    ValueProvider<CategoryStatistics, Integer> count();

    ModelKeyProvider<CategoryStatistics> key();
}
