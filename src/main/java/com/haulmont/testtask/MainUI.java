package com.haulmont.testtask;

import com.haulmont.testtask.dao.DoctorDao;
import com.haulmont.testtask.dao.database.Database;
import com.haulmont.testtask.dao.impl.DoctorDaoImpl;
import com.haulmont.testtask.pojo.Doctor;
import com.haulmont.testtask.layouts.DoctorLayout;
import com.haulmont.testtask.layouts.PatientLayout;
import com.haulmont.testtask.layouts.RecipeLayout;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    private VerticalLayout layout;
    private TabSheet tabSheet;

    private DoctorLayout doctorLayout;
    private PatientLayout patientLayout;
    private RecipeLayout recipeLayout;


    @Override
    protected void init(VaadinRequest request)
    {


        layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setMargin(true);

        tabSheet = new TabSheet();
        tabSheet.setCaptionAsHtml(true);
        tabSheet.setCaption("<h1>Медицинская система \"Haulmont\"</h1>");


        doctorLayout = new DoctorLayout();
        patientLayout = new PatientLayout();
        recipeLayout = new RecipeLayout();


        tabSheet.addTab(doctorLayout, "Врачи");
        tabSheet.addTab(patientLayout, "Пациенты");
        tabSheet.addTab(recipeLayout, "Рецепты");

        layout.addComponent(tabSheet);
        setContent(layout);

    }

}