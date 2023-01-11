package com.kalin.insurance.insurancerestapi.policy;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.client.Client;
import com.kalin.insurance.insurancerestapi.cover.Cover;
import com.kalin.insurance.insurancerestapi.model.BaseEntity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Entity holding data for a policy.
 */
@Table
@Entity(name = "policy")
public class Policy extends BaseEntity {

    @Column(name = "conclusion_date")
    @NotNull(message = "You have to provide a conclusion date")
    private Date conclusionDate;

    @Column(name = "ins_begin_date")
    @NotNull(message = "You have to provide an insurance begin date.")
    private Date insuranceBeginDate;

    @Column(name = "ins_end_date")
    @NotNull(message = "You have to provide an insurance end date.")
    private Date insuranceEndDate;

    @Column(name = "total_premium")
    private double totalPremium;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "client_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_policy_client"))
    @NotNull(message = "You have to provide a client.")
    @Valid
    private Client client;

    @OneToMany(mappedBy = "policy", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotNull(message = "You have to provide cars.")
    @Valid
    private List<Car> cars;

    /**
     * Calculates premium for a policy, based on cars and their covers.
     */
    @PrePersist
    @PreUpdate
    public void calculateTotalPremium() {
        double totalPremiumCalculated = 0;
        for (Car car : cars) {
            for (Cover cover : car.getCovers()) {
                cover.calculatePremium();
                totalPremiumCalculated += cover.getPremium();
            }
        }
        this.totalPremium = totalPremiumCalculated;
    }

    public Date getConclusionDate() {
        return conclusionDate;
    }

    public void setConclusionDate(Date conclusionDate) {
        this.conclusionDate = conclusionDate;
    }

    public Date getInsuranceBeginDate() {
        return insuranceBeginDate;
    }

    public void setInsuranceBeginDate(Date insuranceBeginDate) {
        this.insuranceBeginDate = insuranceBeginDate;
    }

    public Date getInsuranceEndDate() {
        return insuranceEndDate;
    }

    public void setInsuranceEndDate(Date insuranceEndDate) {
        this.insuranceEndDate = insuranceEndDate;
    }

    public double getTotalPremium() {
        return totalPremium;
    }

    public void setTotalPremium(double totalPremium) {
        this.totalPremium = totalPremium;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @JsonManagedReference
    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
