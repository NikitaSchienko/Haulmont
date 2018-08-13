package com.haulmont.testtask.pojo;

public class Doctor
{
    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private String specialization;

    public Doctor(Long id, String name, String surname, String patronymic, String specialization)
    {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.specialization = specialization;
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

    public String getSpecialization()
    {
        return specialization;
    }

    public void setSpecialization(String specialization)
    {
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return specialization + ": \""+surname + " " + name + " " + patronymic+"\"";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Doctor doctor = (Doctor) o;

        if (id != null ? !id.equals(doctor.id) : doctor.id != null) return false;
        if (name != null ? !name.equals(doctor.name) : doctor.name != null) return false;
        if (surname != null ? !surname.equals(doctor.surname) : doctor.surname != null) return false;
        if (patronymic != null ? !patronymic.equals(doctor.patronymic) : doctor.patronymic != null) return false;
        return specialization != null ? specialization.equals(doctor.specialization) : doctor.specialization == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
