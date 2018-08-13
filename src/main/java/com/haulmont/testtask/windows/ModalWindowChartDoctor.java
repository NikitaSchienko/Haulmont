package com.haulmont.testtask.windows;

import com.haulmont.testtask.dao.impl.StatisticDaoImpl;
import com.haulmont.testtask.pojo.Statistic;
import com.vaadin.ui.*;
import com.vaadin.data.util.BeanItemContainer;
import java.sql.SQLException;
import java.util.List;


public class ModalWindowChartDoctor extends Window
{

    private Grid grid;
    private Button buttonClose;
    private VerticalLayout verticalLayout;


    public ModalWindowChartDoctor()
    {

        setCaption("Статистика");
        verticalLayout = new VerticalLayout();
        buttonClose = new Button("Закрыть");
        verticalLayout.setMargin(true);
        verticalLayout.setSpacing(true);

        List<Statistic> statistics = createTable();

        BeanItemContainer<Statistic> container =
                new BeanItemContainer<Statistic>(Statistic.class, statistics);

        grid = new Grid(container);
        grid.setCaption("Статистика по выписанным рецептам");
        grid.setSizeFull();


        grid.setColumnOrder( new Object[] {"name", "surname","patronymic","specialization","count"} );
        grid.getColumn("count").setHeaderCaption("Кол-во рецептов");
        grid.getColumn("name").setHeaderCaption("Имя");
        grid.getColumn("surname").setHeaderCaption("Фамилия");
        grid.getColumn("patronymic").setHeaderCaption("Отчество");
        grid.getColumn("specialization").setHeaderCaption("Специализация");


        verticalLayout.addComponent(grid);
        verticalLayout.addComponent(buttonClose);
        verticalLayout.setComponentAlignment(buttonClose, Alignment.MIDDLE_RIGHT);
        setContent(verticalLayout);
        center();

        buttonClose.addClickListener(e -> {
            this.close();
        });

    }

    private List<Statistic> createTable() {
        List<Statistic> statistics = null;

        StatisticDaoImpl statisticDao = new StatisticDaoImpl();
        try {
            statistics = statisticDao.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return statistics;
        }
    }
}
