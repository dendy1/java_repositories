package org.nebezdari.contracts;

import org.nebezdari.Person;

import java.time.LocalDate;

public class TVContract extends Contract {
    private TVPackage tvPackage;

    public TVContract(Long id, LocalDate startDate, LocalDate endDate, Long contractNumber, Person owner, TVPackage tvPackage) {
        super(id, startDate, endDate, contractNumber, owner);
        this.tvPackage = tvPackage;
    }

    public TVPackage getTvPackage() {
        return tvPackage;
    }

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
