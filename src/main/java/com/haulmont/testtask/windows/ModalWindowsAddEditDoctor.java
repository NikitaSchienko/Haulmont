package com.haulmont.testtask.windows;

import com.haulmont.testtask.dao.DoctorDao;
import com.haulmont.testtask.dao.PatientDao;
import com.haulmont.testtask.dao.impl.DoctorDaoImpl;
import com.haulmont.testtask.dao.impl.PatientDaoImpl;
import com.haulmont.testtask.dao.validators.*;
import com.haulmont.testtask.pojo.Doctor;
import com.haulmont.testtask.pojo.Patient;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.List;

public class ModalWindowsAddEditDoctor extends Window
{

    private Doctor currentDoctor;
    private TextField textFieldName;
    private TextField textFieldSurname;
    private TextField textFieldPatronymic;
    private TextField textFieldSpecialization;

    private Button buttonOk;

    private VerticalLayout verticalLayout;

    public void setCurrentDoctor(Doctor currentDoctor)
    {
        this.currentDoctor = currentDoctor;

        textFieldName.setValue(currentDoctor.getName());
        textFieldSurname.setValue(currentDoctor.getSurname());
        textFieldPatronymic.setValue(currentDoctor.getPatronymic());
        textFieldSpecialization.setValue(currentDoctor.getSpecialization());

    }


    public ModalWindowsAddEditDoctor(BeanItemContainer<Doctor> container)
    {


        verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(true);
        verticalLayout.setSpacing(true);
        buttonOk = new Button("Сохранить");

        buttonOk.setSizeFull();

        setContent(verticalLayout);

        verticalLayout.addComponent(new Label("Добавить нового врача"));
        textFieldName = new TextField("Введите имя");
        textFieldName.setCaption("Имя");
        textFieldName.setSizeFull();

        textFieldName.addValidator(new ValidatorName());

        textFieldSurname = new TextField("Введите фамилию");
        textFieldSurname.setCaption("Фамилия");
        textFieldSurname.setSizeFull();
        textFieldSurname.addValidator(new ValidatorSurname());

        textFieldPatronymic = new TextField("Введите отчество");
        textFieldPatronymic.setCaption("Отчество");
        textFieldPatronymic.setSizeFull();
        textFieldPatronymic.addValidator(new ValidatorPatronymic());

        textFieldSpecialization = new TextField("Введите специализацию");
        textFieldSpecialization.setCaption("Специализация");
        textFieldSpecialization.setSizeFull();
        textFieldSpecialization.addValidator(new ValidatorSpecialization());

        verticalLayout.addComponents(textFieldName, textFieldSurname, textFieldPatronymic, textFieldSpecialization);

        verticalLayout.addComponent(buttonOk);

        center();

        buttonOk.addClickListener(e ->
        {
            if(textFieldName.isValid() &&
                    textFieldSurname.isValid() &&
                    textFieldPatronymic.isValid() &&
                    textFieldSpecialization.isValid()) {
                DoctorDao doctorDao = new DoctorDaoImpl();

                try {
                    if (currentDoctor != null) {
                        container.getContainerProperty(currentDoctor, "name").setValue(textFieldName.getValue());
                        container.getContainerProperty(currentDoctor, "surname").setValue(textFieldSurname.getValue());
                        container.getContainerProperty(currentDoctor, "patronymic").setValue(textFieldPatronymic.getValue());
                        container.getContainerProperty(currentDoctor, "specialization").setValue(textFieldSpecialization.getValue());

                        doctorDao.update(currentDoctor.getId(), currentDoctor);
                    } else {
                        currentDoctor = new Doctor(
                                null,
                                textFieldName.getValue(),
                                textFieldSurname.getValue(),
                                textFieldPatronymic.getValue(),
                                textFieldSpecialization.getValue()
                        );
                        doctorDao.insert(currentDoctor);
                        container.addBean(currentDoctor);
                    }

                    updateGrid(container);
                    this.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            else
            {
                Notification.show("Неверный ввод поля!",
                        "",
                        Notification.Type.WARNING_MESSAGE);
            }
        });
    }

    private void updateGrid(BeanItemContainer<Doctor> container) {
        List<Doctor> newDoctorList = null;
        DoctorDao doctorDao = new DoctorDaoImpl();
        try
        {
            newDoctorList = doctorDao.findAll();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        container.removeAllItems();
        container.addAll(newDoctorList);
    }
}

