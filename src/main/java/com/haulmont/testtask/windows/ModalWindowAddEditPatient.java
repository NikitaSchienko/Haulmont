package com.haulmont.testtask.windows;

import com.haulmont.testtask.dao.DoctorDao;
import com.haulmont.testtask.dao.PatientDao;
import com.haulmont.testtask.dao.impl.DoctorDaoImpl;
import com.haulmont.testtask.dao.impl.PatientDaoImpl;
import com.haulmont.testtask.dao.pojo.Patient;
import com.haulmont.testtask.dao.validators.ValidatorName;
import com.haulmont.testtask.dao.validators.ValidatorPhone;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

import java.sql.SQLException;

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
        textFieldSurname.addValidator(new ValidatorName());

        textFieldPatronymic = new TextField("Введите отчество");
        textFieldPatronymic.setCaption("Отчество");
        textFieldPatronymic.setSizeFull();
        textFieldPatronymic.addValidator(new ValidatorName());

        textFieldPhone = new TextField("Введите номер");
        textFieldPhone.setCaption("Номер телефона");
        textFieldPhone.setSizeFull();
        textFieldPhone.addValidator(new ValidatorPhone());

        verticalLayout.addComponents(textFieldName, textFieldSurname, textFieldPatronymic, textFieldPhone);
        verticalLayout.addComponent(buttonOk);
        center();

        buttonOk.addClickListener(event ->
        {
            PatientDao patientDao = new PatientDaoImpl();

            try {
                if (currentPatient != null) {
                    container.getContainerProperty(currentPatient, "name").setValue(textFieldName.getValue());
                    container.getContainerProperty(currentPatient, "surname").setValue(textFieldSurname.getValue());
                    container.getContainerProperty(currentPatient, "patronymic").setValue(textFieldPatronymic.getValue());
                    container.getContainerProperty(currentPatient, "phone").setValue(textFieldPhone.getValue());

                    patientDao.update(currentPatient.getId(), currentPatient);
                }
                else {
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

                this.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        });
    }
}
