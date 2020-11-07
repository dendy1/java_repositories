package org.nebezdari.contracts;

import org.nebezdari.Person;

import java.time.LocalDate;

/** Абстрактный класс, содержащий общую информацию для всех контрактов. */
public abstract class Contract {
    /** Поле ID */
    private Long id;

    /** Поле Дата начала контракта */
    private LocalDate startDate;

    /** Поле Дата окончания контракта */
    private LocalDate endDate;

    /** Поле Номер контракта */
    private Long contractNumber;

    /** Поле Владелец контракта */
    private Person owner;

    /**
     * Конструктор для создания объекта класса Контракт
     * @param id ID контракта
     * @param startDate Дата начала контракта
     * @param endDate Дата окончания контракта
     * @param contractNumber Номер контракта
     * @param owner Владелец контракта {@link Person}
     */
    public Contract(Long id, LocalDate startDate, LocalDate endDate, Long contractNumber, Person owner) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contractNumber = contractNumber;
        this.owner = owner;
    }

    /**
     * Функция получения значения поля {@link Contract#id}
     * @return возвращает ID контракта
     */
    public Long getId() {
        return id;
    }

    /**
     * Функция определения ID контракта {@link Contract#id}
     * @param id ID контракта
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Функция получения значения поля {@link Contract#startDate}
     * @return возвращает Дату начала контракта
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Функция определения Даты начала контракта {@link Contract#startDate}
     * @param startDate Дата начала контракта
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Функция получения значения поля {@link Contract#endDate}
     * @return возвращает Дату окончания контракта
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Функция определения Даты окончания контракта {@link Contract#endDate}
     * @param endDate Дата окончания контракта
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Функция получения значения поля {@link Contract#contractNumber}
     * @return возвращает Номер контракта
     */
    public Long getContractNumber() {
        return contractNumber;
    }

    /**
     * Функция определения Номера контракта {@link Contract#contractNumber}
     * @param contractNumber Номер контракта
     */
    public void setContractNumber(Long contractNumber) {
        this.contractNumber = contractNumber;
    }

    /**
     * Функция получения значения поля {@link Contract#owner}
     * @return возвращает Владельца контракта
     */
    public Person getOwner() {
        return owner;
    }

    /**
     * Функция определения Владельца контракта {@link Contract#owner}
     * @param owner Владелец контракта
     */
    public void setOwner(Person owner) {
        this.owner = owner;
    }


    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", contractNumber=" + contractNumber +
                ", owner=" + owner +
                '}';
    }
}
