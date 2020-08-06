package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Posicao.
 */
@Entity
@Table(name = "posicao")
public class Posicao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "quantidade")
    private Long quantidade;

    @ManyToOne
    @JsonIgnoreProperties(value = "posicaos", allowSetters = true)
    private Papel papel;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public Posicao quantidade(Long quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    public Papel getPapel() {
        return papel;
    }

    public Posicao papel(Papel papel) {
        this.papel = papel;
        return this;
    }

    public void setPapel(Papel papel) {
        this.papel = papel;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Posicao)) {
            return false;
        }
        return id != null && id.equals(((Posicao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Posicao{" +
            "id=" + getId() +
            ", quantidade=" + getQuantidade() +
            "}";
    }
}
