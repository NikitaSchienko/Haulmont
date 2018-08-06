package com.haulmont.testtask.layouts;

import com.haulmont.testtask.dao.PatientDao;
import com.haulmont.testtask.dao.RecipeDao;
import com.haulmont.testtask.dao.impl.PatientDaoImpl;
import com.haulmont.testtask.dao.impl.RecipeDaoImpl;
import com.haulmont.testtask.dao.pojo.Patient;
import com.haulmont.testtask.dao.pojo.Recipe;
import com.haulmont.testtask.decorator.Record;
import com.haulmont.testtask.windows.ModalWindowAddEditPatient;
import com.haulmont.testtask.windows.ModalWindowAddRecipe;
import com.haulmont.testtask.windows.ModalWindowsAddDoctor;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeLayout extends VerticalLayout {

    public RecipeLayout()
    {
        HorizontalLayout horizontalLayoutForSearch = new HorizontalLayout();

        TextField textFieldSearch = new TextField();
        ComboBox comboBoxTypeSearch = new ComboBox();


        comboBoxTypeSearch.addItem("Описание");
        comboBoxTypeSearch.addItem("Врач");
        comboBoxTypeSearch.addItem("Патиент");
        comboBoxTypeSearch.addItem("Дата начала");
        comboBoxTypeSearch.addItem("Дата конца");
        comboBoxTypeSearch.addItem("Приоритет");


        Button buttonSearch = new Button("Поиск");

        horizontalLayoutForSearch.setSpacing(true);
        horizontalLayoutForSearch.addComponents(textFieldSearch, comboBoxTypeSearch, buttonSearch);

        HorizontalLayout horizontalLayoutForButtons = new HorizontalLayout();
        horizontalLayoutForButtons.setSpacing(true);

        List<Record> recipes = createTable();

        BeanItemContainer<Record> container =
                new BeanItemContainer<Record>(Record.class, recipes);

        Grid grid = new Grid(container);

        grid.setCaption("Рецепты");
        grid.setSizeFull();

        grid.getColumn("id").setHidden(true);
//        grid.getColumn("description").setHeaderCaption("Описание");
//        grid.getColumn("patient").setHeaderCaption("Фамилия");
//        grid.getColumn("doctor").setHeaderCaption("Отчество");
//        grid.getColumn("dateStart").setHeaderCaption("Дата начала");
//        grid.getColumn("dateLength").setHeaderCaption("Дата конца");
//        grid.getColumn("priority").setHeaderCaption("Приоритет");


        Button buttonDelete = new Button("Удалить");
        Button buttonAdd = new Button("Добавить");
        Button buttonEdit = new Button("Редактировать");


        buttonDelete.addClickListener(e -> {
            Object selected = ((Grid.SingleSelectionModel) grid.getSelectionModel()).getSelectedRow();
            Long idSelected = (Long) grid.getContainerDataSource().getItem(selected).getItemProperty("id").getValue();

            RecipeDao recipeDao = new RecipeDaoImpl();

            try
            {
                recipeDao.delete(idSelected);
                grid.getContainerDataSource().removeItem(selected);
                Notification.show("Рецепт - успешно удален",
                        "",
                        Notification.Type.WARNING_MESSAGE);
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        });

        buttonAdd.addClickListener(e -> {
            ModalWindowAddRecipe modalWindowAddRecipe = new ModalWindowAddRecipe(container);

            modalWindowAddRecipe.setHeight("700px");
            modalWindowAddRecipe.setWidth("500px");

            UI.getCurrent().addWindow(modalWindowAddRecipe);
        });

        buttonEdit.addClickListener(e -> {
            ModalWindowAddRecipe modalWindowAddRecipe = new ModalWindowAddRecipe(container);

            Record selected = (Record) ((Grid.SingleSelectionModel) grid.getSelectionModel()).getSelectedRow();

            modalWindowAddRecipe.setRecipe(selected);

            modalWindowAddRecipe.setHeight("700px");
            modalWindowAddRecipe.setWidth("500px");

            // Add it to the root component
            UI.getCurrent().addWindow(modalWindowAddRecipe);
        });



        horizontalLayoutForButtons.addComponents(buttonAdd,buttonDelete,buttonEdit);


        this.setMargin(true);
        this.setSpacing(true);
        this.addComponent(horizontalLayoutForSearch);
        this.addComponent(grid);
        this.addComponent(horizontalLayoutForButtons);
    }

    private List<Record> createTable()
    {
        List<Record> recipes = null;

        RecipeDao recipeDao = new RecipeDaoImpl();
        try
        {
            recipes = recipeDao.findAll();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            return recipes;
        }
    }
}
