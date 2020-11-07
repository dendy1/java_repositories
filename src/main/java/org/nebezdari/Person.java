package org.nebezdari;

import org.nebezdari.contracts.InternetContract;

import java.time.LocalDate;
import java.time.Period;

public class Person {
    /** Поле ID человека */
    private Long id;

    /** Поле Имя */
    private String firstName;

    /** Поле Фамилия */
    private String secondName;

    /** Поле Отчество */
    private String middleName;

    /** Поле Дата рождения */
    private LocalDate dateOfBirth;

    /** Поле Гендер (Пол) */
    private Gender gender;

    /** Поле Паспортные данные */
    private Long passportData;

    /**
     * Конструктор для создания объекта класса Человек
     * @param id ID человека
     * @param firstName Имя
     * @param secondName Фамилия
     * @param middleName Отчество
     * @param dateOfBirth Дата рождения
     * @param gender Гендер (Пол)
     * @param passportData Паспортные данные
     */
    public Person(Long id, String firstName, String secondName, String middleName, LocalDate dateOfBirth, Gender gender, Long passportData) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.passportData = passportData;
    }

    /**
     * Функция получения возраста человека
     * @return возвращает Возраст человека
     */
    public int getAge()
    {
        LocalDate current = LocalDate.now();
        return Period.between(dateOfBirth, current).getYears();
    }

    /**
     * Функция получения значения поля {@link Person#id}
     * @return возвращает ID человека
     */
    public Long getId() {
        return id;
    }

    /**
     * Функция определения ID человека {@link Person#id}
     * @param id ID человека
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Функция получения значения поля {@link Person#firstName}
     * @return возвращает Имя человека
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Функция определения Имя человека {@link Person#firstName}
     * @param firstName Имя человека
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Функция получения значения поля {@link Person#secondName}
     * @return возвращает Фамилию человека
     */
    public String getSecondName() {
        return secondName;
    }

    /**
     * Функция определения ID человека {@link Person#secondName}
     * @param secondName Фамилия человека
     */
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    /**
     * Функция получения значения поля {@link Person#middleName}
     * @return возвращает Отчество человка
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Функция определения Отчества человека {@link Person#middleName}
     * @param middleName Отчество человека
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * Функция получения значения поля {@link Person#dateOfBirth}
     * @return возвращает Дату рождения человека
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Функция определения Даты рождения человека {@link Person#dateOfBirth}
     * @param dateOfBirth Дата рождения человека
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Функция получения значения поля {@link Person#gender}
     * @return возвращает Гендер (Пол) человека
     * @see Gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Функция определения Гендера (Пола) человека {@link Person#gender}
     * @param gender Гендер (Пол) человека
     * @see Gender
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Функция получения значения поля {@link Person#passportData}
     * @return возвращает Паспортные данные человека
     */
    public Long getPassportData() {
        return passportData;
    }

    /**
     * Функция определения Паспортных данных человека {@link Person#passportData}
     * @param passportData Паспортные данные человека
     */
    public void setPassportData(Long passportData) {
        this.passportData = passportData;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                ", passportData='" + passportData + '\'' +
                '}';
    }
}
