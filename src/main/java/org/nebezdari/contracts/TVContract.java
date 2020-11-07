package org.nebezdari.contracts;

import org.nebezdari.Person;

import java.time.LocalDate;

/**
 *  Класс описывающий Контракт на цифровое телевидение
 *  Наследуется от {@link Contract}.
 */
public class TVContract extends Contract {
    /** Поле Пакет телеканалов */
    private TVPackage tvPackage;

    /**
     * Конструктор для создания объекта класса Контракт на цифровое телевидение
     * @param id ID контракта
     * @param startDate Дата начала контракта
     * @param endDate Дата окончания контракта
     * @param contractNumber Номер контракта
     * @param owner Владелец контракта {@link Person}
     * @param tvPackage Пакет телеканалов {@link TVPackage}
     * @see Person
     * @see TVPackage
     */
    public TVContract(Long id, LocalDate startDate, LocalDate endDate, Long contractNumber, Person owner, TVPackage tvPackage) {
        super(id, startDate, endDate, contractNumber, owner);
        this.tvPackage = tvPackage;
    }

    /**
     * Функция получения значения поля {@link TVContract#tvPackage}
     * @return возвращает Пакет телеканалов
     * @see TVPackage
     */
    public TVPackage getTvPackage() {
        return tvPackage;
    }

    /**
     * Функция определения Пакетов телеканалов {@link TVContract#tvPackage}
     * @param tvPackage Пакет телеканалов
     * @see TVPackage
     */
    public void setTvPackage(TVPackage tvPackage) {
        this.tvPackage = tvPackage;
    }

    @Override
    public String toString() {
        return "TVContract{" +
                "id=" + getId() +
                ", startDate=" + getStartDate() +
                ", endDate=" + getEndDate() +
                ", contractNumber=" + getContractNumber() +
                ", owner=" + getOwner() +
                ", tvPackage=" + tvPackage +
                '}';
    }
}
