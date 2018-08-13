package com.haulmont.testtask.layouts;

import com.haulmont.testtask.dao.DoctorDao;
import com.haulmont.testtask.dao.impl.DoctorDaoImpl;
import com.haulmont.testtask.pojo.Doctor;
import com.haulmont.testtask.windows.ModalWindowChartDoctor;
import com.haulmont.testtask.windows.ModalWindowsAddEditDoctor;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class DoctorLayout extends VerticalLayout
{
    private Grid grid;

    private HorizontalLayout horizontalLayoutForButtons;

    private Button buttonDelete;
    private Button buttonAdd;
    private Button buttonEdit;
    private Button buttonStatistics;

    private ModalWindowsAddEditDoctor modalWindowsAddEditDoctor;

    public DoctorLayout()
    {

        horizontalLayoutForButtons = new HorizontalLayout();
        horizontalLayoutForButtons.setSpacing(true);


        List<Doctor> doctors = createTable();

        BeanItemContainer<Doctor> container =
                new BeanItemContainer<Doctor>(Doctor.class, doctors);


        grid = new Grid(container);
        grid.setCaption("Врачи");
        grid.setSizeFull();

        grid.setColumnOrder( new Object[] {"id", "surname", "name","patronymic","specialization"} );

        grid.getColumn("id").setHidden(true);
        grid.getColumn("name").setHeaderCaption("Имя");
        grid.getColumn("surname").setHeaderCaption("Фамилия");
        grid.getColumn("patronymic").setHeaderCaption("Отчество");
        grid.getColumn("specialization").setHeaderCaption("Специализация");



        buttonDelete = new Button("Удалить");
        buttonAdd = new Button("Добавить");
        buttonEdit = new Button("Редактировать");
        buttonStatistics = new Button("Показать статистику");

        horizontalLayoutForButtons.addComponents(buttonAdd,buttonDelete,buttonEdit, buttonStatistics);

        buttonDelete.addClickListener(e -> {
            Object selected = ((Grid.SingleSelectionModel) grid.getSelectionModel()).getSelectedRow();
            if(selected != null) {
                Long idSelected = (Long) grid.getContainerDataSource().getItem(selected).getItemProperty("id").getValue();

                DoctorDao doctorDao = new DoctorDaoImpl();

                try {
                    doctorDao.delete(idSelected);
                    grid.getContainerDataSource().removeItem(selected);
                    Notification.show("Врач - успешно удален",
                            "",
                            Notification.Type.WARNING_MESSAGE);
                } catch (SQLIntegrityConstraintViolationException mysqlex) {
                    Notification.show("Невозможно - удалить",
                            "",
                            Notification.Type.WARNING_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            else
            {
                Notification.show("Выберите строку для удаления!",
                        "",
                        Notification.Type.WARNING_MESSAGE);
            }

        });


        buttonStatistics.addClickListener(e -> {
            ModalWindowChartDoctor modalWindowChartDoctor = new ModalWindowChartDoctor();

            modalWindowChartDoctor.setHeight("550px");
            modalWindowChartDoctor.setWidth("700px");

            UI.getCurrent().addWindow(modalWindowChartDoctor);
        });

        buttonAdd.addClickListener(e -> {
            modalWindowsAddEditDoctor = new ModalWindowsAddEditDoctor(container);

            modalWindowsAddEditDoctor.setHeight("400px");
            modalWindowsAddEditDoctor.setWidth("500px");

            UI.getCurrent().addWindow(modalWindowsAddEditDoctor);
        });

        buttonEdit.addClickListener(e -> {
            modalWindowsAddEditDoctor = new ModalWindowsAddEditDoctor(container);

            Doctor selected = (Doctor)((Grid.SingleSelectionModel) grid.getSelectionModel()).getSelectedRow();

            if(selected != null) {
                modalWindowsAddEditDoctor.setCurrentDoctor(selected);

                modalWindowsAddEditDoctor.setHeight("400px");
                modalWindowsAddEditDoctor.setWidth("500px");

                // Add it to the root component
                UI.getCurrent().addWindow(modalWindowsAddEditDoctor);
            }
            else
            {
                Notification.show("Выберите строку для редактирования!",
                        "",
                        Notification.Type.WARNING_MESSAGE);
            }
        });



        this.setMargin(true);
        this.setSpacing(true);
        this.addComponent(grid);
        this.addComponent(horizontalLayoutForButtons);


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
