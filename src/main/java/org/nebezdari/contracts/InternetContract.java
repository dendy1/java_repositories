package org.nebezdari.contracts;

import org.nebezdari.Person;

import java.time.LocalDate;

public class InternetContract extends Contract {
    private Float bps;

    public InternetContract(Long id, LocalDate startDate, LocalDate endDate, Long contractNumber, Person owner, Float bps) {
        super(id, startDate, endDate, contractNumber, owner);
        this.bps = bps;
    }

    public static InternetContract fromKbps(Long id, LocalDate startDate, LocalDate endDate, Long contractNumber, Person owner, Float kbps) {
        return new InternetContract(id, startDate, endDate, contractNumber, owner, kbps * 1024);
    }

    public static InternetContract fromMbps(Long id, LocalDate startDate, LocalDate endDate, Long contractNumber, Person owner, Float mbps) {
        return InternetContract.fromKbps(id, startDate, endDate, contractNumber, owner, mbps * 1024);
    }

    public static InternetContract fromGbps(Long id, LocalDate startDate, LocalDate endDate, Long contractNumber, Person owner, Float gbps) {
        return InternetContract.fromMbps(id, startDate, endDate, contractNumber, owner, gbps * 1024);
    }

    public Float getBps() {
        return bps;
    }

    public void setBps(Float bps) {
        this.bps = bps;
    }

    public Float getKbps() {
        return bps / 1024;
    }

    public void setKbps(Float kbps) {
        setBps(kbps * 1024);
    }

    public Float getMbps() {
        return getKbps() / 1024;
    }

    public void setMbps(Float mbps) {
        setKbps(mbps * 1024);
    }

    public Float getGbps() {
        return getMbps() / 1024;
    }

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
