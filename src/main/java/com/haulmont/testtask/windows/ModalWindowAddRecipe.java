package com.haulmont.testtask.windows;

import com.haulmont.testtask.dao.DoctorDao;
import com.haulmont.testtask.dao.PatientDao;
import com.haulmont.testtask.dao.RecipeDao;
import com.haulmont.testtask.dao.impl.DoctorDaoImpl;
import com.haulmont.testtask.dao.impl.PatientDaoImpl;
import com.haulmont.testtask.dao.impl.RecipeDaoImpl;
import com.haulmont.testtask.dao.pojo.Doctor;
import com.haulmont.testtask.dao.pojo.Patient;
import com.haulmont.testtask.dao.pojo.Recipe;
import com.haulmont.testtask.decorator.Decorator;
import com.haulmont.testtask.decorator.RecipeDecorator;
import com.haulmont.testtask.decorator.Record;
import com.sun.org.apache.xpath.internal.SourceTree;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

import javax.print.Doc;
import java.sql.SQLException;
import java.util.Date;

public class ModalWindowAddRecipe extends Window
{

    RecipeDao recipeDao = new RecipeDaoImpl();

    private Record recipe;

    private TextArea textAreaDescription;
    private ComboBox comboBoxPatient;
    private ComboBox comboBoxDoctor;
    private ComboBox comboBoxPriority;
    private DateField dateFieldStart;
    private DateField dateFieldEnd;


    public void setRecipe(Record recipe) {
        this.recipe = recipe;


        Record record = new Decorator(recipe);
        System.out.println(record);

        textAreaDescription.setValue(record.getDescription());
        //comboBoxPatient.setValue(recipe.getPatient());
        dateFieldStart.setValue(new Date((Long) record.getDateStart()));
    }

    public ModalWindowAddRecipe(BeanItemContainer<Record> container)
    {

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(true);
        verticalLayout.setSpacing(true);

        Button buttonOk = new Button("Добавить");

        buttonOk.setSizeFull();
        setContent(verticalLayout);

        textAreaDescription = new TextArea("Введите описание");
        textAreaDescription.setCaption("Описание");
        textAreaDescription.setSizeFull();

        comboBoxPatient = new ComboBox("Выберите пациента");
        comboBoxPatient.setCaption("Пациент");
        comboBoxPatient.setSizeFull();

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

        DoctorDao doctorDao = new DoctorDaoImpl();

        try {
            for(Doctor doctor: doctorDao.findAll())
            {
                comboBoxDoctor.addItem(doctor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        dateFieldStart = new DateField("Выберите дату начала");
        dateFieldStart.setCaption("Выберите дату начала");
        dateFieldStart.setSizeFull();

        dateFieldEnd = new DateField("Выберите дату конца");
        dateFieldEnd.setCaption("Выберите дату конца");
        dateFieldEnd.setSizeFull();

        comboBoxPriority = new ComboBox("Выберите приоритет");
        comboBoxPriority.setCaption("Выберите приоритет");
        comboBoxPriority.setSizeFull();

        comboBoxPriority.addItems("Нормальный","Срочный","Немедленный");

        verticalLayout.addComponents(textAreaDescription,comboBoxPatient,comboBoxDoctor,dateFieldStart,dateFieldEnd,comboBoxPriority);

        verticalLayout.addComponent(buttonOk);
        center();

        buttonOk.addClickListener(event -> {

            try {
                if (recipe != null) {


                    container.getContainerProperty(recipe, "description").setValue(textAreaDescription.getValue());
                    container.getContainerProperty(recipe, "patient").setValue(comboBoxPatient.getValue());
                    container.getContainerProperty(recipe, "doctor").setValue(comboBoxDoctor.getValue());
                    container.getContainerProperty(recipe, "dateStart").setValue(dateFieldStart.getValue());
                    container.getContainerProperty(recipe, "dateEnd").setValue(dateFieldEnd.getValue());
                    container.getContainerProperty(recipe, "priority").setValue(comboBoxPriority.getValue());


                    recipeDao.update(((Patient)recipe).getId(), (Recipe) recipe);
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

            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }

            this.close();
        });
    }
}
