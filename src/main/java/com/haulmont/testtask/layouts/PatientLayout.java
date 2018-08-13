package com.haulmont.testtask.layouts;

import com.haulmont.testtask.dao.PatientDao;
import com.haulmont.testtask.dao.impl.PatientDaoImpl;
import com.haulmont.testtask.pojo.Patient;
import com.haulmont.testtask.windows.ModalWindowAddEditPatient;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class PatientLayout extends VerticalLayout
{
    private HorizontalLayout horizontalLayoutForButtons;
    private Grid grid;

    private Button buttonDelete;
    private Button buttonAdd;
    private Button buttonEdit;

    private ModalWindowAddEditPatient modalWindowAddEditPatient;

    public PatientLayout() {


        horizontalLayoutForButtons = new HorizontalLayout();
        horizontalLayoutForButtons.setSpacing(true);



        List<Patient> patients = createTable();

        BeanItemContainer<Patient> container =
                new BeanItemContainer<Patient>(Patient.class, patients);

        grid = new Grid(container);
        grid.setCaption("Патиенты");
        grid.setSizeFull();

        grid.setColumnOrder( new Object[] {"id", "surname", "name","patronymic","phone"} );

        grid.getColumn("id").setHidden(true);
        grid.getColumn("name").setHeaderCaption("Имя");
        grid.getColumn("surname").setHeaderCaption("Фамилия");
        grid.getColumn("patronymic").setHeaderCaption("Отчество");
        grid.getColumn("phone").setHeaderCaption("Телефон");


        buttonDelete = new Button("Удалить");
        buttonAdd = new Button("Добавить");
        buttonEdit = new Button("Редактировать");

        horizontalLayoutForButtons.addComponents(buttonAdd, buttonDelete, buttonEdit);


        buttonDelete.addClickListener(e -> {
            Object selected = ((Grid.SingleSelectionModel) grid.getSelectionModel()).getSelectedRow();
            if(selected != null) {
                Long idSelected = (Long) grid.getContainerDataSource().getItem(selected).getItemProperty("id").getValue();

                PatientDao patientDao = new PatientDaoImpl();

                try {
                    patientDao.delete(idSelected);
                    grid.getContainerDataSource().removeItem(selected);
                    Notification.show("Пациент - успешно удален",
                            "",
                            Notification.Type.WARNING_MESSAGE);
                } catch (SQLIntegrityConstraintViolationException ex) {
                    Notification.show("Удаление не возможно",
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

        buttonAdd.addClickListener(e -> {
            modalWindowAddEditPatient = new ModalWindowAddEditPatient(container);

            modalWindowAddEditPatient.setHeight("400px");
            modalWindowAddEditPatient.setWidth("500px");

            UI.getCurrent().addWindow(modalWindowAddEditPatient);
        });

        buttonEdit.addClickListener(e -> {
            modalWindowAddEditPatient = new ModalWindowAddEditPatient(container);

            Patient selected = (Patient)((Grid.SingleSelectionModel) grid.getSelectionModel()).getSelectedRow();

            if(selected != null)
            {
                modalWindowAddEditPatient.setCurrentPatient(selected);

                modalWindowAddEditPatient.setHeight("400px");
                modalWindowAddEditPatient.setWidth("500px");


                UI.getCurrent().addWindow(modalWindowAddEditPatient);
            }
            else
            {
                Notification.show("Выберите строку для редактирования",
                        "",
                        Notification.Type.WARNING_MESSAGE);
            }
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
