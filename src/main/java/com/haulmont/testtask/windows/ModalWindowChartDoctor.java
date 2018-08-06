package com.haulmont.testtask.windows;

import com.vaadin.ui.Window;

import javax.security.auth.login.Configuration;

public class ModalWindowChartDoctor extends Window
{
    public ModalWindowChartDoctor()
    {
        

        Configuration configuration = chart.getConfiguration();
        configuration.setTitle("Example Column Chart");
        chart.getConfiguration().getChart().setType(ChartType.COLUMN);

        configuration.addSeries(new ListSeries("Tokyo", 20, 12, 34, 23, 65, 8, 4, 7, 76, 19, 20, 8));
        configuration.addSeries(new ListSeries("Miami", 34, 29, 23, 65, 8, 4, 7, 7, 59, 8, 9, 19));

        XAxis x = new XAxis();
        x.setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
                "Sep", "Oct", "Nov", "Dec");
        configuration.addxAxis(x);

        YAxis y = new YAxis();
        y.setMin(0);
        y.setTitle("Rainfall (mm)");
        configuration.addyAxis(y);

        add(chart);
    }
}
