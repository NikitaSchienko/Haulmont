package com.haulmont.testtask.dao.pojo;

public class Patient
{
    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private long phone;


    public Patient(Long id, String name, String surname, String patronymic, long phone) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.phone = phone;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public String getPatronymic()
    {
        return patronymic;
    }

    public void setPatronymic(String patronymic)
    {
        this.patronymic = patronymic;
    }

    public Long getPhone()
    {
        return phone;
    }

    public void setPhone(long phone)
    {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Пациент: " + surname + " "+name +" "+ patronymic;
    }
}
