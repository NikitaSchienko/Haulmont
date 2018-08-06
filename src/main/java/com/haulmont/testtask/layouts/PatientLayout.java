package com.haulmont.testtask.layouts;

import com.haulmont.testtask.dao.DoctorDao;
import com.haulmont.testtask.dao.PatientDao;
import com.haulmont.testtask.dao.impl.DoctorDaoImpl;
import com.haulmont.testtask.dao.impl.PatientDaoImpl;
import com.haulmont.testtask.dao.pojo.Doctor;
import com.haulmont.testtask.dao.pojo.Patient;
import com.haulmont.testtask.windows.ModalWindowAddEditPatient;
import com.haulmont.testtask.windows.ModalWindowsAddDoctor;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.List;

public class PatientLayout extends VerticalLayout
{

    public PatientLayout() {


        HorizontalLayout horizontalLayoutForButtons = new HorizontalLayout();
        horizontalLayoutForButtons.setSpacing(true);



        List<Patient> patients = createTable();

        BeanItemContainer<Patient> container =
                new BeanItemContainer<Patient>(Patient.class, patients);

        //Create a grid bound to the container
        Grid grid = new Grid(container);
        grid.setCaption("Патиенты");
        grid.setSizeFull();

        grid.getColumn("id").setHidden(true);
        grid.getColumn("name").setHeaderCaption("Имя");
        grid.getColumn("surname").setHeaderCaption("Фамилия");
        grid.getColumn("patronymic").setHeaderCaption("Отчество");
        grid.getColumn("phone").setHeaderCaption("Телефон");


        Button buttonDelete = new Button("Удалить");
        Button buttonAdd = new Button("Добавить");
        Button buttonEdit = new Button("Редактировать");

        horizontalLayoutForButtons.addComponents(buttonAdd, buttonDelete, buttonEdit);


        buttonDelete.addClickListener(e -> {
            Object selected = ((Grid.SingleSelectionModel) grid.getSelectionModel()).getSelectedRow();
            Long idSelected = (Long) grid.getContainerDataSource().getItem(selected).getItemProperty("id").getValue();

            PatientDao patientDao = new PatientDaoImpl();

            try
            {
                patientDao.delete(idSelected);
                grid.getContainerDataSource().removeItem(selected);
                Notification.show("Пациент - успешно удален",
                        "",
                        Notification.Type.WARNING_MESSAGE);
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        });

        buttonAdd.addClickListener(e -> {
            ModalWindowAddEditPatient modalWindowAddEditPatient = new ModalWindowAddEditPatient(container);

            modalWindowAddEditPatient.setHeight("400px");
            modalWindowAddEditPatient.setWidth("500px");

            UI.getCurrent().addWindow(modalWindowAddEditPatient);
        });

        buttonEdit.addClickListener(e -> {
            ModalWindowAddEditPatient modalWindowAddEditPatient = new ModalWindowAddEditPatient(container);

            Patient selected = (Patient)((Grid.SingleSelectionModel) grid.getSelectionModel()).getSelectedRow();

            modalWindowAddEditPatient.setCurrentPatient(selected);

            modalWindowAddEditPatient.setHeight("400px");
            modalWindowAddEditPatient.setWidth("500px");

            // Add it to the root component
            UI.getCurrent().addWindow(modalWindowAddEditPatient);
        });


        this.setMargin(true);
        this.setSpacing(true);
        this.addComponent(grid);
        this.addComponent(horizontalLayoutForButtons);

    }

    private List<Patient> createTable()
    {
        List<Patient> patients = null;

        PatientDao patientDao = new PatientDaoImpl();
        try
        {
            patients = patientDao.findAll();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            return patients;
        }
    }
}
