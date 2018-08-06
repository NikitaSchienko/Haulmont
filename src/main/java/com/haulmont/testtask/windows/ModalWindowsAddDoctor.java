package com.haulmont.testtask.windows;

import com.haulmont.testtask.dao.DoctorDao;
import com.haulmont.testtask.dao.impl.DoctorDaoImpl;
import com.haulmont.testtask.dao.pojo.Doctor;
import com.haulmont.testtask.dao.validators.ValidatorName;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.List;

public class ModalWindowsAddDoctor extends Window
{

    private Doctor currentDoctor;
    private TextField textFieldName;
    private TextField textFieldSurname;
    private TextField textFieldPatronymic;
    private TextField textFieldSpecialization;

    public void setCurrentDoctor(Doctor currentDoctor)
    {
        this.currentDoctor = currentDoctor;

        textFieldName.setValue(currentDoctor.getName());
        textFieldSurname.setValue(currentDoctor.getSurname());
        textFieldPatronymic.setValue(currentDoctor.getPatronymic());
        textFieldSpecialization.setValue(currentDoctor.getSpecialization());

    }


    public ModalWindowsAddDoctor(BeanItemContainer<Doctor> container)
    {


        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(true);
        verticalLayout.setSpacing(true);
        Button buttonOk = new Button("Сохранить");

        buttonOk.setSizeFull();


        setContent(verticalLayout);

        // Put some components in it
        verticalLayout.addComponent(new Label("Добавить нового врача"));
        textFieldName = new TextField("Введите имя");
        textFieldName.setCaption("Имя");
        textFieldName.setSizeFull();

        textFieldName.addValidator(new ValidatorName());


        textFieldSurname = new TextField("Введите фамилию");
        textFieldSurname.setCaption("Фамилия");
        textFieldSurname.setSizeFull();

        textFieldPatronymic = new TextField("Введите отчество");
        textFieldPatronymic.setCaption("Отчество");
        textFieldPatronymic.setSizeFull();

        textFieldSpecialization = new TextField("Введите специализацию");
        textFieldSpecialization.setCaption("Специализация");
        textFieldSpecialization.setSizeFull();

        verticalLayout.addComponents(textFieldName, textFieldSurname, textFieldPatronymic, textFieldSpecialization);

        verticalLayout.addComponent(buttonOk);

        center();

        buttonOk.addClickListener(event ->
        {
            DoctorDao doctorDao = new DoctorDaoImpl();

            try {
                if (currentDoctor != null) {
                    container.getContainerProperty(currentDoctor, "name").setValue(textFieldName.getValue());
                    container.getContainerProperty(currentDoctor, "surname").setValue(textFieldSurname.getValue());
                    container.getContainerProperty(currentDoctor, "patronymic").setValue(textFieldPatronymic.getValue());
                    container.getContainerProperty(currentDoctor, "specialization").setValue(textFieldSpecialization.getValue());

                    doctorDao.update(currentDoctor.getId(), currentDoctor);
                }
                else {
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

                this.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        });
    }
}

