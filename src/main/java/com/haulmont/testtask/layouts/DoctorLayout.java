package com.haulmont.testtask.layouts;

import com.haulmont.testtask.dao.DoctorDao;
import com.haulmont.testtask.dao.impl.DoctorDaoImpl;
import com.haulmont.testtask.dao.pojo.Doctor;
import com.haulmont.testtask.windows.ModalWindowsAddDoctor;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorLayout extends VerticalLayout
{
    private Grid grid;

    public DoctorLayout()
    {

        HorizontalLayout horizontalLayoutForButtons = new HorizontalLayout();
        horizontalLayoutForButtons.setSpacing(true);

        grid = new Grid();


        grid.setCaption("Врачи");


        List<Doctor> doctors = createTable();

        BeanItemContainer<Doctor> container =
                new BeanItemContainer<Doctor>(Doctor.class, doctors);

        //Create a grid bound to the container
        grid = new Grid(container);
        grid.setSizeFull();

        grid.getColumn("id").setHidden(true);
        grid.getColumn("name").setHeaderCaption("Имя");
        grid.getColumn("surname").setHeaderCaption("Фамилия");
        grid.getColumn("patronymic").setHeaderCaption("Отчество");
        grid.getColumn("specialization").setHeaderCaption("Специализация");



        Button buttonDelete = new Button("Удалить");
        Button buttonAdd = new Button("Добавить");
        Button buttonEdit = new Button("Редактировать");
        Button buttonStatistics = new Button("Показать статистику");

        horizontalLayoutForButtons.addComponents(buttonAdd,buttonDelete,buttonEdit, buttonStatistics);

        buttonDelete.addClickListener(e -> {
            Object selected = ((Grid.SingleSelectionModel) grid.getSelectionModel()).getSelectedRow();
            Long idSelected = (Long) grid.getContainerDataSource().getItem(selected).getItemProperty("id").getValue();

            DoctorDao doctorDao = new DoctorDaoImpl();

            try
            {
                doctorDao.delete(idSelected);
                grid.getContainerDataSource().removeItem(selected);
                Notification.show("Врач - успешно удален",
                        "",
                        Notification.Type.WARNING_MESSAGE);
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }

        });

        buttonAdd.addClickListener(e -> {
            ModalWindowsAddDoctor modalWindowsAddDoctor = new ModalWindowsAddDoctor(container);

            modalWindowsAddDoctor.setHeight("400px");
            modalWindowsAddDoctor.setWidth("500px");

            UI.getCurrent().addWindow(modalWindowsAddDoctor);
        });

        buttonEdit.addClickListener(e -> {
            ModalWindowsAddDoctor modalWindowsAddDoctor = new ModalWindowsAddDoctor(container);

            Doctor selected = (Doctor)((Grid.SingleSelectionModel) grid.getSelectionModel()).getSelectedRow();

            modalWindowsAddDoctor.setCurrentDoctor(selected);

            modalWindowsAddDoctor.setHeight("400px");
            modalWindowsAddDoctor.setWidth("500px");

            // Add it to the root component
            UI.getCurrent().addWindow(modalWindowsAddDoctor);
        });

        this.setMargin(true);
        this.setSpacing(true);
        this.addComponent(grid);
        this.addComponent(horizontalLayoutForButtons);


    }

    private void updateDateTable()
    {
        grid.clearSortOrder();
        //grid.setColumnOrder("name", "born");
//        for(int i = 0; i < doctors.size(); i++)
//        {
//            grid.addRow(
//                    doctors.get(i).getName(),
//                    doctors.get(i).getSurname(),
//                    doctors.get(i).getPatronymic(),
//                    doctors.get(i).getSpecialization(),
//                    doctors.get(i).getId()
//            );
//        }
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
