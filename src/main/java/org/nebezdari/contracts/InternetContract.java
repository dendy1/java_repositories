package org.nebezdari.contracts;

import org.nebezdari.Person;

import java.time.LocalDate;

/**
 *  Класс описывающий Контракт на проводной интернет
 *  Наследуется от {@link Contract}.
 */
public class InternetContract extends Contract {
    /** Поле Скорость соединения в бит/с */
    private Float bps;

    /**
     * Конструктор для создания объекта класса Контракт на проводной интернет
     * @param id ID контракта
     * @param startDate Дата начала контракта
     * @param endDate Дата окончания контракта
     * @param contractNumber Номер контракта
     * @param owner Владелец контракта {@link Person}
     * @param bps Скорость соединения в бит/с
     */
    public InternetContract(Long id, LocalDate startDate, LocalDate endDate, Long contractNumber, Person owner, Float bps) {
        super(id, startDate, endDate, contractNumber, owner);
        this.bps = bps;
    }

    /**
     * Функция для создания объекта класса Контракт на проводной интернет
     * @param id ID контракта
     * @param startDate Дата начала контракта
     * @param endDate Дата окончания контракта
     * @param contractNumber Номер контракта
     * @param owner Владелец контракта {@link Person}
     * @param kbps Скорость соединения в кбит/с
     */
    public static InternetContract fromKbps(Long id, LocalDate startDate, LocalDate endDate, Long contractNumber, Person owner, Float kbps) {
        return new InternetContract(id, startDate, endDate, contractNumber, owner, kbps * 1024);
    }

    /**
     * Функция для создания объекта класса Контракт на проводной интернет
     * @param id ID контракта
     * @param startDate Дата начала контракта
     * @param endDate Дата окончания контракта
     * @param contractNumber Номер контракта
     * @param owner Владелец контракта {@link Person}
     * @param mbps Скорость соединения в мбит/с
     */
    public static InternetContract fromMbps(Long id, LocalDate startDate, LocalDate endDate, Long contractNumber, Person owner, Float mbps) {
        return InternetContract.fromKbps(id, startDate, endDate, contractNumber, owner, mbps * 1024);
    }

    /**
     * Функция для создания объекта класса Контракт на проводной интернет
     * @param id ID контракта
     * @param startDate Дата начала контракта
     * @param endDate Дата окончания контракта
     * @param contractNumber Номер контракта
     * @param owner Владелец контракта {@link Person}
     * @param gbps Скорость соединения в гбит/с
     */
    public static InternetContract fromGbps(Long id, LocalDate startDate, LocalDate endDate, Long contractNumber, Person owner, Float gbps) {
        return InternetContract.fromMbps(id, startDate, endDate, contractNumber, owner, gbps * 1024);
    }

    /**
     * Функция получения значения поля {@link InternetContract#bps}
     * @return возвращает Скорость соединения в бит/с
     */
    public Float getBps() {
        return bps;
    }

    /**
     * Функция определения Скорости соединения {@link InternetContract#bps}
     * @param bps Скорость соединения в бит/с
     */
    public void setBps(Float bps) {
        this.bps = bps;
    }

    /**
     * Функция получения значения поля {@link InternetContract#bps}
     * @return возвращает Скорость соединения в кбит/с
     */
    public Float getKbps() {
        return bps / 1024;
    }

    /**
     * Функция определения Скорости соединения {@link InternetContract#bps}
     * @param kbps Скорость соединения в кбит/с
     */
    public void setKbps(Float kbps) {
        setBps(kbps * 1024);
    }

    /**
     * Функция получения значения поля {@link InternetContract#bps}
     * @return возвращает Скорость соединения в мбит/с
     */
    public Float getMbps() {
        return getKbps() / 1024;
    }

    /**
     * Функция определения Скорости соединения {@link InternetContract#bps}
     * @param mbps Скорость соединения в мбит/с
     */
    public void setMbps(Float mbps) {
        setKbps(mbps * 1024);
    }

    /**
     * Функция получения значения поля {@link InternetContract#bps}
     * @return возвращает Скорость соединения в гбит/с
     */
    public Float getGbps() {
        return getMbps() / 1024;
    }

    /**
     * Функция определения Скорости соединения {@link InternetContract#bps}
     * @param gbps Скорость соединения в гбит/с
     */
    public void setGbps(Float gbps) {
        setMbps(gbps * 1024);
    }

    @Override
    public String toString() {
        return "InternetContract{" +
                "id=" + getId() +
                ", startDate=" + getStartDate() +
                ", endDate=" + getEndDate() +
                ", contractNumber=" + getContractNumber() +
                ", owner=" + getOwner() +
                ", bps=" + bps +
                '}';
    }
}
