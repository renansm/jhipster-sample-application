package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Paper.
 */
@Entity
@Table(name = "paper")
public class Paper implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "market_value")
    private Long marketValue;

    @OneToMany(mappedBy = "paper")
    private Set<Position> positions = new HashSet<>();

    @OneToMany(mappedBy = "paper")
    private Set<Transaction> transactions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Paper code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Paper name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMarketValue() {
        return marketValue;
    }

    public Paper marketValue(Long marketValue) {
        this.marketValue = marketValue;
        return this;
    }

    public void setMarketValue(Long marketValue) {
        this.marketValue = marketValue;
    }

    public Set<Position> getPositions() {
        return positions;
    }

    public Paper positions(Set<Position> positions) {
        this.positions = positions;
        return this;
    }

    public Paper addPosition(Position position) {
        this.positions.add(position);
        position.setPaper(this);
        return this;
    }

    public Paper removePosition(Position position) {
        this.positions.remove(position);
        position.setPaper(null);
        return this;
    }

    public void setPositions(Set<Position> positions) {
        this.positions = positions;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public Paper transactions(Set<Transaction> transactions) {
        this.transactions = transactions;
        return this;
    }

    public Paper addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        transaction.setPaper(this);
        return this;
    }

    public Paper removeTransaction(Transaction transaction) {
        this.transactions.remove(transaction);
        transaction.setPaper(null);
        return this;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paper)) {
            return false;
        }
        return id != null && id.equals(((Paper) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Paper{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", marketValue=" + getMarketValue() +
            "}";
    }
}
