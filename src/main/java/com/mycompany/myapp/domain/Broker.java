package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Broker.
 */
@Entity
@Table(name = "broker")
public class Broker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "broker")
    private Set<BrokerageNote> brokerageNotes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Broker name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<BrokerageNote> getBrokerageNotes() {
        return brokerageNotes;
    }

    public Broker brokerageNotes(Set<BrokerageNote> brokerageNotes) {
        this.brokerageNotes = brokerageNotes;
        return this;
    }

    public Broker addBrokerageNote(BrokerageNote brokerageNote) {
        this.brokerageNotes.add(brokerageNote);
        brokerageNote.setBroker(this);
        return this;
    }

    public Broker removeBrokerageNote(BrokerageNote brokerageNote) {
        this.brokerageNotes.remove(brokerageNote);
        brokerageNote.setBroker(null);
        return this;
    }

    public void setBrokerageNotes(Set<BrokerageNote> brokerageNotes) {
        this.brokerageNotes = brokerageNotes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Broker)) {
            return false;
        }
        return id != null && id.equals(((Broker) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Broker{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
