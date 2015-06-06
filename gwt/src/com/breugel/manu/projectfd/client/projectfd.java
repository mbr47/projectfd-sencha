package com.breugel.manu.projectfd.client;

import com.breugel.manu.projectfd.common.Category;
import com.breugel.manu.projectfd.common.CategoryStatistics;
import com.breugel.manu.projectfd.common.Consumption;
import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.axis.CategoryAxis;
import com.sencha.gxt.chart.client.chart.axis.NumericAxis;
import com.sencha.gxt.chart.client.chart.series.BarSeries;
import com.sencha.gxt.chart.client.chart.series.Series;
import com.sencha.gxt.chart.client.chart.series.SeriesLabelConfig;
import com.sencha.gxt.chart.client.chart.series.SeriesRenderer;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.sprite.Sprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.Format;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.*;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.grid.*;
import com.sencha.gxt.widget.core.client.info.Info;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
class projectfd implements EntryPoint {

    private TextField consumptionBox;
    private final ListStore<Consumption> todaysConsumptions = new ListStore<>(props.key());
    private final ListStore<Consumption> previousConsumptions = new ListStore<>(props.key());
    private final Label todayDQS = new Label();

    private static final ConsumptionProperties props = GWT.create(ConsumptionProperties.class);
    private ComboBox<Category> categoryComboBox;
    private ListStore<CategoryStatistics> chartCategoryStats;

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        HorizontalPanel hp = new HorizontalPanel();

        VerticalPanel leftPanel = createAddConsumptionForm();
        hp.add(leftPanel);

        FramedPanel todaysConsumptions = createTodaysConsumptionGrid();
        hp.add(todaysConsumptions);
        projectfdService.App.getInstance().getTodaysConsumptions(new TodaysConsAsyncCallback());

        FramedPanel previousConsumptions = createPreviousConsumptionGrid();
        hp.add(previousConsumptions);
        projectfdService.App.getInstance().getPreviousDaysConsumptions(new PreviousDaysConsAsyncCallback());

        FramedPanel chart = createMonthlyConsumptionChart();
        hp.add(chart);


        projectfdService.App.getInstance().getCategoryStatistics(new CategoryStatisticsAsyncCallback());

        RootPanel.get().add(hp);
    }

    private FramedPanel createMonthlyConsumptionChart() {

        final CategoryStatisticsProperties props = GWT.create(CategoryStatisticsProperties.class);
        chartCategoryStats = new ListStore<>(props.key());


        TextSprite titleConsumptions = new TextSprite("# Consumptions");
        titleConsumptions.setFontSize(18);

        NumericAxis<CategoryStatistics> axis = new NumericAxis<>();
        axis.setPosition(Chart.Position.BOTTOM);
        axis.addField(props.count());
        axis.setTitleConfig(titleConsumptions);
        axis.setDisplayGrid(true);
        axis.setMinimum(0);
        axis.setMaximum(60);

        TextSprite titleCategories = new TextSprite("Categories");
        titleCategories.setFontSize(18);

        CategoryAxis<CategoryStatistics, String> catAxis = new CategoryAxis<>();
        catAxis.setPosition(Chart.Position.LEFT);
        catAxis.setField(props.categoryString());
        catAxis.setTitleConfig(titleCategories);

        SeriesLabelConfig<CategoryStatistics> labelConfig = new SeriesLabelConfig<>();
        labelConfig.setLabelPosition(Series.LabelPosition.OUTSIDE);

        final RGB[] colors = {
                new RGB(213, 70, 121), new RGB(44, 153, 201), new RGB(146, 6, 157), new RGB(49, 149, 0), new RGB(249, 153, 0)};

        final BarSeries<CategoryStatistics> bar = new BarSeries<>();
        bar.setYAxisPosition(Chart.Position.BOTTOM);
        bar.addYField(props.count());
        bar.addColor(RGB.GREEN);
        bar.setLabelConfig(labelConfig);
        bar.setRenderer(new SeriesRenderer<CategoryStatistics>() {
            @Override
            public void spriteRenderer(Sprite sprite, int index, ListStore<CategoryStatistics> store) {
                double value = props.count().getValue(store.get(index));
                sprite.setFill(colors[(int) Math.round(value) % 5]);
                sprite.redraw();
            }
        });

        final Chart<CategoryStatistics> chart = new Chart<>();
        chart.bindStore(chartCategoryStats);
        chart.setShadowChart(false);
        chart.addAxis(axis);
        chart.addAxis(catAxis);
        chart.addSeries(bar);
        chart.setDefaultInsets(20);

        FramedPanel panel = new FramedPanel();
        panel.setHeadingText("Category Statistics");
        panel.setLayoutData(new MarginData(10));
        panel.setPixelSize(620, 500);
        panel.setBodyBorder(true);
        panel.add(chart, new MarginData(15));


        TextButton submit = new TextButton("Refresh");

        submit.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                projectfdService.App.getInstance().getCategoryStatistics(new CategoryStatisticsAsyncCallback());
            }
        });
        panel.addButton(submit);

        return panel;

    }


    private FramedPanel createPreviousConsumptionGrid() {
        previousConsumptions.setAutoCommit(true);

        SummaryColumnConfig<Consumption, String> foodCol = new SummaryColumnConfig<>(props.food(), 100, SafeHtmlUtils.fromTrustedString("<b>Food</b>"));
        SummaryColumnConfig<Consumption, Category> categoryCol = new SummaryColumnConfig<>(props.category(), 100, "Category");
        SummaryColumnConfig<Consumption, Integer> scoreCol = new SummaryColumnConfig<>(props.intScore(), 75, "Score");
        SummaryColumnConfig<Consumption, String> dateCol = new SummaryColumnConfig<>(props.dateDay(), 125, "Date");

        scoreCol.setSummaryType(new SummaryType.SumSummaryType<Integer>());
        scoreCol.setSummaryRenderer(new SummaryRenderer<Consumption>() {
            @Override
            public SafeHtml render(Number value, Map<ValueProvider<? super Consumption, ?>, Number> data) {
                return SafeHtmlUtils.fromTrustedString("DQS " + value.intValue());
            }
        });

        List<ColumnConfig<Consumption, ?>> columns = new ArrayList<>();
        columns.add(foodCol);
        columns.add(categoryCol);
        columns.add(scoreCol);
        columns.add(dateCol);

        ColumnModel<Consumption> cm = new ColumnModel<>(columns);

        final GroupSummaryView<Consumption> groupingView = new GroupSummaryView<>();

        groupingView.setShowGroupedColumn(true);
        groupingView.setForceFit(true);
        groupingView.groupBy(dateCol);
        groupingView.setStartCollapsed(true);

        com.sencha.gxt.widget.core.client.grid.Grid<Consumption> grid = new com.sencha.gxt.widget.core.client.grid.Grid<>(previousConsumptions, cm, groupingView);

        FramedPanel panel = new FramedPanel();
        panel.setHeadingText("Previous Consumptions");
        panel.add(grid, new MarginData(15));
        return panel;
    }

    private FramedPanel createTodaysConsumptionGrid() {
        ColumnConfig<Consumption, String> foodCol = new ColumnConfig<>(props.food(), 100, SafeHtmlUtils.fromTrustedString("<b>Food</b>"));
        ColumnConfig<Consumption, Category> categoryCol = new ColumnConfig<>(props.category(), 100, "Category");
        ColumnConfig<Consumption, String> scoreCol = new ColumnConfig<>(props.score(), 50, "Score");
        ColumnConfig<Consumption, String> dateCol = new ColumnConfig<>(props.date(), 125, "Date");

        List<ColumnConfig<Consumption, ?>> columns = new ArrayList<>();
        columns.add(foodCol);
        columns.add(categoryCol);
        columns.add(scoreCol);
        columns.add(dateCol);
        ColumnModel<Consumption> cm = new ColumnModel<>(columns);

        final com.sencha.gxt.widget.core.client.grid.Grid<Consumption> grid = new com.sencha.gxt.widget.core.client.grid.Grid<>(todaysConsumptions, cm);

        FramedPanel gridPanel = new FramedPanel();
        gridPanel.setHeadingText("Todays Consumptions");
        gridPanel.add(grid, new MarginData(15));
        return gridPanel;
    }

    private VerticalPanel createAddConsumptionForm() {
        int columnWidth = 150;

        FramedPanel today = new FramedPanel();
        today.setHeadingText("Todays DQS");
        today.setWidth(columnWidth + 50);
        today.add(todayDQS, new MarginData(15));

        consumptionBox = new TextField();
        consumptionBox.setAllowBlank(false);
        consumptionBox.setWidth(columnWidth);

        consumptionBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                projectfdService.App.getInstance().getCategoryFor(consumptionBox.getText(), new FillConsumptionAsyncCallback());
            }
        });

        CategoryProperties props = GWT.create(CategoryProperties.class);
        final ListStore<Category> categoryStore = new ListStore<>(props.id());
        categoryStore.addAll(Category.all());

        categoryComboBox = new ComboBox<>(categoryStore, props.string());
        categoryComboBox.setWidth(columnWidth);

        FocusPanel cb = new FocusPanel();
        cb.add(categoryComboBox);
        cb.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (consumptionBox.getText() == null || consumptionBox.getText().isEmpty() || categoryComboBox.getValue() == null) {
                    return;
                }
                if(event.getCharCode()==KeyCodes.KEY_ENTER) {
                    projectfdService.App.getInstance().addConsumption(consumptionBox.getText(), categoryComboBox.getValue(), new AddConsumptionAsyncCallback());
                }
            }
         });

        HtmlLayoutContainer hlc = new HtmlLayoutContainer(getTableMarkup());
        hlc.add(new FieldLabel(consumptionBox, "Consumption"), new AbstractHtmlLayoutContainer.HtmlData(".consumption"));
        hlc.add(cb, new AbstractHtmlLayoutContainer.HtmlData(".category"));

        FramedPanel fp = new FramedPanel();
        fp.setHeadingText("Enter Consumption");
        fp.setWidth(columnWidth+50);
        fp.add(hlc, new MarginData(15));

        TextButton submit = new TextButton("Submit");

        submit.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                if (consumptionBox.getText() == null || consumptionBox.getText().isEmpty() || categoryComboBox.getValue() == null) {
                    return;
                }
                projectfdService.App.getInstance().addConsumption(consumptionBox.getText(), categoryComboBox.getValue(), new AddConsumptionAsyncCallback());
            }
        });

        consumptionBox.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(final KeyPressEvent event) {
                if(event.getCharCode()==KeyCodes.KEY_ENTER) {
                    Timer t = new Timer() {
                        @Override
                        public void run() {
                            projectfdService.App.getInstance().addConsumption(consumptionBox.getText(), categoryComboBox.getValue(), new AddConsumptionAsyncCallback());
                        }
                    };
                    t.schedule(750);
                }
            }
        });

        fp.addButton(submit);

        List<FieldLabel> fl = FormPanelHelper.getFieldLabels(fp);
        for (FieldLabel f : fl) {
            f.setLabelAlign(FormPanel.LabelAlign.TOP);
        }

        VerticalPanel vp = new VerticalPanel();
        vp.setSpacing(10);
        vp.add(today);
        vp.add(fp);
        return vp;
    }

    private native String getTableMarkup() /*-{
        return [
            '<table width=100% cellpadding=0 cellspacing=0>',
            '<tr><td class="consumption" colspan=2></td></tr>',
            '<tr><td class="category" colspan=2></td></tr>',
            '</table>' ].join("");
    }-*/;


    private class AddConsumptionAsyncCallback implements AsyncCallback<Consumption> {

        public void onSuccess(Consumption result) {
            projectfdService.App.getInstance().getTodaysConsumptions(new TodaysConsAsyncCallback());

            String msg = Format.substitute("Successfully added {0} with score {1}", result.getFood(), result.getScore());
            Info.display("MessageBox", msg);

            consumptionBox.setText("");
            categoryComboBox.setValue(null);
        }

        public void onFailure(Throwable throwable) {
            String sb = "Failed to add new consumption: " + throwable.getMessage();
            RootPanel.get().add(new Label(sb));
        }
    }

    private class TodaysConsAsyncCallback implements AsyncCallback<List<Consumption>> {

        @Override
        public void onFailure(Throwable caught) {
            RootPanel.get().add(new Label("Failed to add new consumption"));
        }

        public void onSuccess(List<Consumption> result) {
            todaysConsumptions.clear();
            todaysConsumptions.addAll(result);

            int count = 0;
            for (Consumption c : result) {
                count += Integer.parseInt(c.getScore());
            }
            todayDQS.setText("DQS for today " + String.valueOf(count));
        }

    }

    private class PreviousDaysConsAsyncCallback implements AsyncCallback<List<Consumption>> {
        @Override
        public void onFailure(Throwable caught) {

        }

        @Override
        public void onSuccess(List<Consumption> result) {
            previousConsumptions.clear();
            previousConsumptions.addAll(result);
        }

    }

    private class FillConsumptionAsyncCallback implements AsyncCallback<Category> {
        @Override
        public void onFailure(Throwable caught) {

        }

        @Override
        public void onSuccess(Category result) {
            categoryComboBox.setValue(result);
        }
    }

    private class CategoryStatisticsAsyncCallback implements AsyncCallback<List<CategoryStatistics>> {
        @Override
        public void onFailure(Throwable caught) {

        }

        @Override
        public void onSuccess(List<CategoryStatistics> result) {
            Collections.sort(result);
            chartCategoryStats.clear();
            chartCategoryStats.addAll(result);
        }
    }
}
