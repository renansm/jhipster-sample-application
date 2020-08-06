package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A BrokerageNote.
 */
@Entity
@Table(name = "brokerage_note")
public class BrokerageNote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "emoluments")
    private Long emoluments;

    @Column(name = "liquidation")
    private Long liquidation;

    @Column(name = "other_taxes")
    private Long otherTaxes;

    @Column(name = "value")
    private Long value;

    @OneToMany(mappedBy = "brokerageNote")
    private Set<Transaction> transactions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "brokerageNotes", allowSetters = true)
    private Broker broker;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public BrokerageNote number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getEmoluments() {
        return emoluments;
    }

    public BrokerageNote emoluments(Long emoluments) {
        this.emoluments = emoluments;
        return this;
    }

    public void setEmoluments(Long emoluments) {
        this.emoluments = emoluments;
    }

    public Long getLiquidation() {
        return liquidation;
    }

    public BrokerageNote liquidation(Long liquidation) {
        this.liquidation = liquidation;
        return this;
    }

    public void setLiquidation(Long liquidation) {
        this.liquidation = liquidation;
    }

    public Long getOtherTaxes() {
        return otherTaxes;
    }

    public BrokerageNote otherTaxes(Long otherTaxes) {
        this.otherTaxes = otherTaxes;
        return this;
    }

    public void setOtherTaxes(Long otherTaxes) {
        this.otherTaxes = otherTaxes;
    }

    public Long getValue() {
        return value;
    }

    public BrokerageNote value(Long value) {
        this.value = value;
        return this;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public BrokerageNote transactions(Set<Transaction> transactions) {
        this.transactions = transactions;
        return this;
    }

    public BrokerageNote addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        transaction.setBrokerageNote(this);
        return this;
    }

    public BrokerageNote removeTransaction(Transaction transaction) {
        this.transactions.remove(transaction);
        transaction.setBrokerageNote(null);
        return this;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Broker getBroker() {
        return broker;
    }

    public BrokerageNote broker(Broker broker) {
        this.broker = broker;
        return this;
    }

    public void setBroker(Broker broker) {
        this.broker = broker;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BrokerageNote)) {
            return false;
        }
        return id != null && id.equals(((BrokerageNote) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BrokerageNote{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", emoluments=" + getEmoluments() +
            ", liquidation=" + getLiquidation() +
            ", otherTaxes=" + getOtherTaxes() +
            ", value=" + getValue() +
            "}";
    }
}
