package com.haulmont.testtask.windows;

import com.haulmont.testtask.dao.PatientDao;
import com.haulmont.testtask.dao.RecipeDao;
import com.haulmont.testtask.dao.impl.PatientDaoImpl;
import com.haulmont.testtask.dao.impl.RecipeDaoImpl;
import com.haulmont.testtask.dao.validators.ValidatorPatronymic;
import com.haulmont.testtask.dao.validators.ValidatorSurname;
import com.haulmont.testtask.decorator.Record;
import com.haulmont.testtask.pojo.Patient;
import com.haulmont.testtask.dao.validators.ValidatorName;
import com.haulmont.testtask.dao.validators.ValidatorPhone;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.List;

public class ModalWindowAddEditPatient extends Window
{
    private Patient currentPatient;
    private TextField textFieldName;
    private TextField textFieldSurname;
    private TextField textFieldPatronymic;
    private TextField textFieldPhone;
    private Button buttonOk;


    public void setCurrentPatient(Patient currentPatient)
    {
        this.currentPatient = currentPatient;

        textFieldName.setValue(currentPatient.getName());
        textFieldSurname.setValue(currentPatient.getSurname());
        textFieldPatronymic.setValue(currentPatient.getPatronymic());
        textFieldPhone.setValue(String.valueOf(currentPatient.getPhone()));
    }

    public ModalWindowAddEditPatient(BeanItemContainer<Patient> container)
    {
        VerticalLayout verticalLayout = new VerticalLayout();

        setContent(verticalLayout);
        verticalLayout.setMargin(true);
        verticalLayout.setSpacing(true);

        buttonOk = new Button("Сохранить");
        buttonOk.setSizeFull();


        verticalLayout.addComponent(new Label("Добавить нового пациента"));

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

        textFieldPhone = new TextField("Введите номер");
        textFieldPhone.setCaption("Номер телефона");
        textFieldPhone.setSizeFull();
        textFieldPhone.addValidator(new ValidatorPhone());

        verticalLayout.addComponents(textFieldName, textFieldSurname, textFieldPatronymic, textFieldPhone);
        verticalLayout.addComponent(buttonOk);
        center();

        buttonOk.addClickListener(event ->
        {

            if(textFieldPhone.isValid() &&
                    textFieldPatronymic.isValid() &&
                    textFieldSurname.isValid() &&
                    textFieldName.isValid())
            {
                PatientDao patientDao = new PatientDaoImpl();

                try {
                    if (currentPatient != null) {
                        container.getContainerProperty(currentPatient, "name").setValue(textFieldName.getValue());
                        container.getContainerProperty(currentPatient, "surname").setValue(textFieldSurname.getValue());
                        container.getContainerProperty(currentPatient, "patronymic").setValue(textFieldPatronymic.getValue());
                        container.getContainerProperty(currentPatient, "phone").setValue(Long.valueOf(textFieldPhone.getValue()));

                        patientDao.update(currentPatient.getId(), currentPatient);
                    } else {
                        currentPatient = new Patient(
                                null,
                                textFieldName.getValue(),
                                textFieldSurname.getValue(),
                                textFieldPatronymic.getValue(),
                                Long.valueOf(textFieldPhone.getValue())
                        );
                        patientDao.insert(currentPatient);
                        container.addBean(currentPatient);
                    }

                    updateGrid(container);
                    this.close();
                } catch (SQLException e) {
                    e.printStackTrace();
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

    private void updateGrid(BeanItemContainer<Patient> container) {
        List<Patient> newPatientList = null;
        PatientDao patientDao = new PatientDaoImpl();
        try
        {
            newPatientList = patientDao.findAll();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        container.removeAllItems();
        container.addAll(newPatientList);
    }
}
