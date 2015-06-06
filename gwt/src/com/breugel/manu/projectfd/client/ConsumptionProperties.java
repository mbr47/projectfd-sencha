package com.breugel.manu.projectfd.client;

import com.breugel.manu.projectfd.common.Category;
import com.breugel.manu.projectfd.common.Consumption;
import com.google.gwt.editor.client.Editor;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * Created by MBR on 3/20/2015.
 */
interface ConsumptionProperties extends PropertyAccess<Consumption> {

    @Editor.Path("id")
    ModelKeyProvider<Consumption> key();

    ValueProvider<Consumption, String> food();

    ValueProvider<Consumption, Category> category();

    ValueProvider<Consumption, String> score();

    ValueProvider<Consumption, Integer> intScore();

    ValueProvider<Consumption, String> date();

    ValueProvider<Consumption, String> dateDay();
}
