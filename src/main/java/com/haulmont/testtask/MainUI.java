package com.haulmont.testtask;

import com.haulmont.testtask.dao.DoctorDao;
import com.haulmont.testtask.dao.impl.DoctorDaoImpl;
import com.haulmont.testtask.dao.pojo.Doctor;
import com.haulmont.testtask.layouts.DoctorLayout;
import com.haulmont.testtask.layouts.PatientLayout;
import com.haulmont.testtask.layouts.RecipeLayout;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;
import java.util.List;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    @Override
    protected void init(VaadinRequest request)
    {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setMargin(true);

        TabSheet tabSheet = new TabSheet();
        tabSheet.setCaptionAsHtml(true);
        tabSheet.setCaption("<h1>Медицинская система \"Haulmont\"</h1>");


        DoctorLayout doctorLayout = new DoctorLayout();
        PatientLayout patientLayout = new PatientLayout();
        RecipeLayout recipeLayout = new RecipeLayout();


        tabSheet.addTab(doctorLayout, "Врачи");
        tabSheet.addTab(patientLayout, "Пациенты");
        tabSheet.addTab(recipeLayout, "Рецепты");

        layout.addComponent(tabSheet);
        setContent(layout);

    }

    private List<Doctor> createTable()
    {
        List<Doctor> doctors = null;

        DoctorDao doctorDao = new DoctorDaoImpl();
        try
        {
            doctors = doctorDao.findAll();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            return doctors;
        }
    }
}