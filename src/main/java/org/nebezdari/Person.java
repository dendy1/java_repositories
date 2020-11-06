package org.nebezdari;

import java.time.LocalDate;
import java.time.Period;

public class Person {
    private Long id;
    private String firstName;
    private String secondName;
    private String middleName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private Long passportData;

    public Person(Long id, String firstName, String secondName, String middleName, LocalDate dateOfBirth, Gender gender, Long passportData) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.passportData = passportData;
    }

    public int getAge()
    {
        LocalDate current = LocalDate.now();
        return Period.between(dateOfBirth, current).getYears();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Long getPassportData() {
        return passportData;
    }

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
