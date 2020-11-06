package org.nebezdari.contracts;

import org.nebezdari.Person;

import java.time.LocalDate;

public abstract class Contract {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long contractNumber;
    private Person owner;

    public Contract(Long id, LocalDate startDate, LocalDate endDate, Long contractNumber, Person owner) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contractNumber = contractNumber;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(Long contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Person getOwner() {
        return owner;
    }

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
