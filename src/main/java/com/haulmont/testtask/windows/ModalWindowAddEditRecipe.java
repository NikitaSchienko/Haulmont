package com.haulmont.testtask.windows;

import com.haulmont.testtask.dao.DoctorDao;
import com.haulmont.testtask.dao.PatientDao;
import com.haulmont.testtask.dao.RecipeDao;
import com.haulmont.testtask.dao.impl.DoctorDaoImpl;
import com.haulmont.testtask.dao.impl.PatientDaoImpl;
import com.haulmont.testtask.dao.impl.RecipeDaoImpl;
import com.haulmont.testtask.dao.validators.ValidatorComboBox;
import com.haulmont.testtask.dao.validators.ValidatorDate;
import com.haulmont.testtask.dao.validators.ValidatorTextArea;
import com.haulmont.testtask.pojo.Doctor;
import com.haulmont.testtask.pojo.Patient;
import com.haulmont.testtask.pojo.Recipe;
import com.haulmont.testtask.decorator.Decorator;
import com.haulmont.testtask.decorator.RecipeDecorator;
import com.haulmont.testtask.decorator.Record;
import com.vaadin.data.util.AbstractBeanContainer;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class ModalWindowAddEditRecipe extends Window
{

    RecipeDao recipeDao = new RecipeDaoImpl();

    private Record recipe;

    private TextArea textAreaDescription;
    private ComboBox comboBoxPatient;
    private ComboBox comboBoxDoctor;
    private ComboBox comboBoxPriority;
    private DateField dateFieldStart;
    private DateField dateFieldEnd;
    private VerticalLayout verticalLayout;


    public void setRecipe(Record recipe) {
        this.recipe = recipe;


        Record record = new Decorator(recipe);

        textAreaDescription.setValue(record.getDescription());
        dateFieldEnd.setValue(new Date((Long)record.getDateEnd()));
        dateFieldStart.setValue(new Date((Long) record.getDateStart()));
        comboBoxPriority.select(record.getPriority());
        comboBoxPatient.select(record.getPatient());
        comboBoxDoctor.select(record.getDoctor());
    }

    public ModalWindowAddEditRecipe(BeanItemContainer<Record> container)
    {

        verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(true);
        verticalLayout.setSpacing(true);

        Button buttonOk = new Button("Сохранить");

        buttonOk.setSizeFull();
        setContent(verticalLayout);

        textAreaDescription = new TextArea("Введите описание");
        textAreaDescription.setCaption("Описание");
        textAreaDescription.setSizeFull();
        textAreaDescription.addValidator(new ValidatorTextArea());

        comboBoxPatient = new ComboBox("Выберите пациента");
        comboBoxPatient.setCaption("Пациент");
        comboBoxPatient.setSizeFull();
        comboBoxPatient.addValidator(new ValidatorComboBox());

        PatientDao patientDao = new PatientDaoImpl();

        try {
            for(Patient patient: patientDao.findAll())
            {
                comboBoxPatient.addItem(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        comboBoxDoctor = new ComboBox("Выберите доктора");
        comboBoxDoctor.setCaption("Доктор");
        comboBoxDoctor.setSizeFull();
        comboBoxDoctor.addValidator(new ValidatorComboBox());

        DoctorDao doctorDao = new DoctorDaoImpl();

        try {
            for(Doctor doctor: doctorDao.findAll())
            {
                comboBoxDoctor.addItem(doctor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        dateFieldStart = new DateField("Выберите дату выдачи рецепта");
        dateFieldStart.setCaption("Выберите дату выдачи рецепта");
        dateFieldStart.setSizeFull();
        dateFieldStart.addValidator(new ValidatorDate());

        dateFieldEnd = new DateField("Выберите срок");
        dateFieldEnd.setCaption("Выберите срок");
        dateFieldEnd.setSizeFull();
        dateFieldEnd.addValidator(new ValidatorDate());

        comboBoxPriority = new ComboBox("Выберите приоритет");
        comboBoxPriority.setCaption("Выберите приоритет");
        comboBoxPriority.setSizeFull();
        comboBoxPriority.addValidator(new ValidatorComboBox());

        comboBoxPriority.addItems("Нормальный","Срочный","Немедленный");

        verticalLayout.addComponents(textAreaDescription,comboBoxPatient,comboBoxDoctor,dateFieldStart,dateFieldEnd,comboBoxPriority);

        verticalLayout.addComponent(buttonOk);
        center();

        buttonOk.addClickListener(event -> {

            if(textAreaDescription.isValid() &&
                    comboBoxDoctor.isValid() &&
                    comboBoxPatient.isValid() &&
                    comboBoxPriority.isValid() &&
                    dateFieldStart.isValid() &&
                    dateFieldEnd.isValid()
                    ) {
                if(isCorrectDateEnd(dateFieldStart.getValue(),dateFieldEnd.getValue())) {
                    try {
                        if (recipe != null) {

                            container.getContainerProperty(recipe, "description").setValue(textAreaDescription.getValue());
                            container.getContainerProperty(recipe, "patient").setValue(comboBoxPatient.getValue());
                            container.getContainerProperty(recipe, "doctor").setValue(comboBoxDoctor.getValue());
                            container.getContainerProperty(recipe, "dateStart").setValue(dateFieldStart.getValue());
                            container.getContainerProperty(recipe, "dateEnd").setValue(dateFieldEnd.getValue());
                            container.getContainerProperty(recipe, "priority").setValue(comboBoxPriority.getValue());

                            recipeDao.update(recipe.getId(), recipe);
                        } else {
                            recipe = new Recipe(null,
                                    textAreaDescription.getValue(),
                                    ((Patient) comboBoxPatient.getValue()).getId(),
                                    ((Doctor) comboBoxDoctor.getValue()).getId(),
                                    dateFieldStart.getValue().getTime(),
                                    dateFieldEnd.getValue().getTime(),
                                    comboBoxPriority.getValue().toString());

                            recipeDao.insert((Recipe) recipe);
                            container.addBean(new RecipeDecorator(recipe));
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    updateGrid(container);
                    this.close();
                }
                else
                {
                    Notification.show("Неверная дата, дата конца рецепта должна быть позже даты выдачи!",
                            "",
                            Notification.Type.WARNING_MESSAGE);
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

    private void updateGrid(BeanItemContainer<Record> container) {
        List<Record> newRecordList = null;
        RecipeDao recipeDao = new RecipeDaoImpl();
        try
        {
            newRecordList = recipeDao.findAll();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        container.removeAllItems();
        container.addAll(newRecordList);
    }


    private boolean isCorrectDateEnd(Date dateStart, Date dateEnd)
    {
        return (dateEnd.getTime() - dateStart.getTime()) > 0;
    }

}
