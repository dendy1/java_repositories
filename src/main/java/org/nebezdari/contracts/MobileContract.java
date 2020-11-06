package org.nebezdari.contracts;

import org.nebezdari.Person;

import java.time.LocalDate;

public class MobileContract extends Contract {
    private Integer minutesTraffic;
    private Integer smsTraffic;
    private Float gbTraffic;

    public MobileContract(Long id, LocalDate startDate, LocalDate endDate, Long contractNumber, Person owner, Integer minutesTraffic, Integer smsTraffic, Float gbTraffic) {
        super(id, startDate, endDate, contractNumber, owner);
        this.minutesTraffic = minutesTraffic;
        this.smsTraffic = smsTraffic;
        this.gbTraffic = gbTraffic;
    }

    public Integer getMinutesTraffic() {
        return minutesTraffic;
    }

    public void setMinutesTraffic(Integer minutesTraffic) {
        this.minutesTraffic = minutesTraffic;
    }

    public Integer getSmsTraffic() {
        return smsTraffic;
    }

    public void setSmsTraffic(Integer smsTraffic) {
        this.smsTraffic = smsTraffic;
    }

    public Float getGbTraffic() {
        return gbTraffic;
    }

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
