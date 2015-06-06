package com.breugel.manu.projectfd.client;

import com.breugel.manu.projectfd.common.Category;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * Created by MBR on 3/21/2015.
 */
interface CategoryProperties extends PropertyAccess<Category> {

    ModelKeyProvider<Category> id();

    LabelProvider<Category> string();

}
