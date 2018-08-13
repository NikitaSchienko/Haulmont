package com.haulmont.testtask.layouts;

import com.haulmont.testtask.constants.Constant;
import com.haulmont.testtask.dao.RecipeDao;
import com.haulmont.testtask.dao.impl.RecipeDaoImpl;
import com.haulmont.testtask.dao.validators.ValidatorComboBox;
import com.haulmont.testtask.decorator.Record;
import com.haulmont.testtask.pojo.SearchType;
import com.haulmont.testtask.windows.ModalWindowAddEditRecipe;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecipeLayout extends VerticalLayout {

    private Grid grid;
    private Button buttonDelete;
    private Button buttonAdd;
    private Button buttonEdit;

    private TextField textFieldSearch;
    private HorizontalLayout horizontalLayoutForSearch;
    private HorizontalLayout horizontalLayoutForButtons;
    private ComboBox comboBoxTypeSearch;
    private Button buttonSearch;
    private Button buttonClear;

    public RecipeLayout()
    {
        horizontalLayoutForSearch = new HorizontalLayout();
        textFieldSearch = new TextField();

        BeanItemContainer<SearchType> containerComboBoxSearch =
                new BeanItemContainer<SearchType>(
                        SearchType.class);


        containerComboBoxSearch.addItem(new SearchType(Constant.DESCRIPTION,"Описание"));
        containerComboBoxSearch.addItem(new SearchType(Constant.DOCTOR,"Доктор"));
        containerComboBoxSearch.addItem(new SearchType(Constant.PATIENT,"Пациент"));
        containerComboBoxSearch.addItem(new SearchType(Constant.PRIORITY,"Приоритет"));

        comboBoxTypeSearch = new ComboBox(null,containerComboBoxSearch);

        comboBoxTypeSearch.setItemCaptionPropertyId("type");
        comboBoxTypeSearch.select(containerComboBoxSearch.getIdByIndex(1));
        comboBoxTypeSearch.addValidator(new ValidatorComboBox());

        buttonSearch = new Button("Поиск");
        buttonClear = new Button("Очистить");

        horizontalLayoutForSearch.setSpacing(true);
        horizontalLayoutForSearch.addComponents(textFieldSearch, comboBoxTypeSearch, buttonSearch,buttonClear);

        horizontalLayoutForButtons = new HorizontalLayout();
        horizontalLayoutForButtons.setSpacing(true);

        List<Record> recipes = createTable();
        BeanItemContainer<Record> container = new BeanItemContainer<Record>(Record.class, recipes);

        grid = new Grid(container);

        grid.setCaption("Рецепты");
        grid.setSizeFull();
        grid.setColumnOrder( new Object[] {"id", "description","patient","doctor","dateStart","dateEnd","priority"} );
        grid.getColumn("id").setHidden(true);
        grid.getColumn("description").setHeaderCaption("Описание");
        grid.getColumn("patient").setHeaderCaption("Пациент");
        grid.getColumn("doctor").setHeaderCaption("Доктор");
        grid.getColumn("dateStart").setHeaderCaption("Дата выдачи рецепта");
        grid.getColumn("dateEnd").setHeaderCaption("Срок");
        grid.getColumn("priority").setHeaderCaption("Приоритет");


        buttonDelete = new Button("Удалить");
        buttonAdd = new Button("Добавить");
        buttonEdit = new Button("Редактировать");


        buttonDelete.addClickListener(e -> {
            Object selected = ((Grid.SingleSelectionModel) grid.getSelectionModel()).getSelectedRow();

            if(selected != null) {
                Long idSelected = (Long) grid.getContainerDataSource().getItem(selected).getItemProperty("id").getValue();

                RecipeDao recipeDao = new RecipeDaoImpl();

                try {
                    recipeDao.delete(idSelected);
                    grid.getContainerDataSource().removeItem(selected);
                    Notification.show("Рецепт - успешно удален",
                            "",
                            Notification.Type.WARNING_MESSAGE);
                } catch (SQLException ex) {
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

        buttonClear.addClickListener(e -> {
            textFieldSearch.setValue("");
            buttonSearch.click();
        });

        buttonAdd.addClickListener(e -> {
            ModalWindowAddEditRecipe modalWindowAddEditRecipe = new ModalWindowAddEditRecipe(container);

            modalWindowAddEditRecipe.setHeight("600px");
            modalWindowAddEditRecipe.setWidth("500px");

            UI.getCurrent().addWindow(modalWindowAddEditRecipe);
        });

        buttonEdit.addClickListener(e -> {
            ModalWindowAddEditRecipe modalWindowAddEditRecipe = new ModalWindowAddEditRecipe(container);

            Record selected = (Record) ((Grid.SingleSelectionModel) grid.getSelectionModel()).getSelectedRow();

            if(selected != null) {
                modalWindowAddEditRecipe.setRecipe(selected);

                modalWindowAddEditRecipe.setHeight("600px");
                modalWindowAddEditRecipe.setWidth("500px");

                // Add it to the root component
                UI.getCurrent().addWindow(modalWindowAddEditRecipe);
            }
            else
            {
                Notification.show("Выберите строку для редактирования",
                        "",
                        Notification.Type.WARNING_MESSAGE);
            }
        });

        buttonSearch.addClickListener(e -> {

            if(comboBoxTypeSearch.isValid())
            {
                Integer selectComboBoxTypeSearch = ((SearchType)comboBoxTypeSearch.getValue()).getId();
                search(selectComboBoxTypeSearch, textFieldSearch.getValue(), container);
            }
            else
            {
                Notification.show("Выберите колонку для поиска",
                    "",
                    Notification.Type.WARNING_MESSAGE);
            }

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



    private void search(Integer id, String search, BeanItemContainer<Record> container)
    {
        search = search.toLowerCase();
        List<Record> searchList = createTable();

        container.removeAllItems();

        if(search.length() == 0)
        {
            container.addAll(searchList);
            return ;
        }
        Pattern p = Pattern.compile(search);
        Matcher m = null;

        switch (id)
        {
            case 1006:
            {
                for(Record record: searchList)
                {
                    m = p.matcher((String)record.getPatient().toString().toLowerCase());
                    if(m.find())
                        container.addBean(record);
                }
            }
            break;
            case 1005:
            {
                for(Record record: searchList)
                {
                    m = p.matcher((String)record.getDoctor().toString().toLowerCase());
                    if(m.find())
                        container.addBean(record);
                }
            }
            break;
            case 1002:
            {
                for(Record record: searchList)
                {
                    m = p.matcher(record.getDescription().toLowerCase());
                    if(m.find())
                    {

                        container.addBean(record);
                    }
                }
            }
            break;
            case 1001:
            {
                for(Record record: searchList)
                {
                    m = p.matcher(record.getPriority().toLowerCase());
                    if(m.find())
                        container.addBean(record);
                }
            }
            break;
        }
    }


}
