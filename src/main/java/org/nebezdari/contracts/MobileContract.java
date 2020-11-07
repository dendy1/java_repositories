package org.nebezdari.contracts;

import org.nebezdari.Person;

import java.time.LocalDate;

/**
 *  Класс описывающий Контракт на мобильную связь
 *  Наследуется от {@link Contract}.
 */
public class MobileContract extends Contract {
    /** Поле Количество минут */
    private Integer minutesTraffic;

    /** Поле Количество смс */
    private Integer smsTraffic;

    /** Поле Количество гигабайт */
    private Float gbTraffic;

    /**
     * Конструктор для создания объекта класса Контракт на мобильную связь
     * @param id ID контракта
     * @param startDate Дата начала контракта
     * @param endDate Дата окончания контракта
     * @param contractNumber Номер контракта
     * @param owner Владелец контракта {@link Person}
     * @param minutesTraffic Количество минут
     * @param smsTraffic Количество смс
     * @param gbTraffic Количество гигабайт
     */
    public MobileContract(Long id, LocalDate startDate, LocalDate endDate, Long contractNumber, Person owner, Integer minutesTraffic, Integer smsTraffic, Float gbTraffic) {
        super(id, startDate, endDate, contractNumber, owner);
        this.minutesTraffic = minutesTraffic;
        this.smsTraffic = smsTraffic;
        this.gbTraffic = gbTraffic;
    }

    /**
     * Функция получения значения поля {@link MobileContract#minutesTraffic}
     * @return возвращает Количество минут
     */
    public Integer getMinutesTraffic() {
        return minutesTraffic;
    }

    /**
     * Функция определения Количества минут {@link MobileContract#minutesTraffic}
     * @param minutesTraffic Количество минут
     */
    public void setMinutesTraffic(Integer minutesTraffic) {
        this.minutesTraffic = minutesTraffic;
    }

    /**
     * Функция получения значения поля {@link MobileContract#smsTraffic}
     * @return возвращает Количество смс
     */
    public Integer getSmsTraffic() {
        return smsTraffic;
    }

    /**
     * Функция определения Количества смс {@link MobileContract#smsTraffic}
     * @param smsTraffic Количество смс
     */
    public void setSmsTraffic(Integer smsTraffic) {
        this.smsTraffic = smsTraffic;
    }

    /**
     * Функция получения значения поля {@link MobileContract#gbTraffic}
     * @return возвращает Количество гигабайт
     */
    public Float getGbTraffic() {
        return gbTraffic;
    }

    /**
     * Функция определения Количества гигабайт {@link MobileContract#gbTraffic}
     * @param gbTraffic Количество гигабайт
     */
    public void setGbTraffic(Float gbTraffic) {
        this.gbTraffic = gbTraffic;
    }

    @Override
    public String toString() {
        return "MobileContract{" +
                "id=" + getId() +
                ", startDate=" + getStartDate() +
                ", endDate=" + getEndDate() +
                ", contractNumber=" + getContractNumber() +
                ", owner=" + getOwner() +
                ", minutesTraffic=" + minutesTraffic +
                ", smsTraffic=" + smsTraffic +
                ", gbTraffic=" + gbTraffic +
                '}';
    }
}
