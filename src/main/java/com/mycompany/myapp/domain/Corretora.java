package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Corretora.
 */
@Entity
@Table(name = "corretora")
public class Corretora implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @OneToMany(mappedBy = "corretora")
    private Set<NotaCorretagem> notaCorretagems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Corretora nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<NotaCorretagem> getNotaCorretagems() {
        return notaCorretagems;
    }

    public Corretora notaCorretagems(Set<NotaCorretagem> notaCorretagems) {
        this.notaCorretagems = notaCorretagems;
        return this;
    }

    public Corretora addNotaCorretagem(NotaCorretagem notaCorretagem) {
        this.notaCorretagems.add(notaCorretagem);
        notaCorretagem.setCorretora(this);
        return this;
    }

    public Corretora removeNotaCorretagem(NotaCorretagem notaCorretagem) {
        this.notaCorretagems.remove(notaCorretagem);
        notaCorretagem.setCorretora(null);
        return this;
    }

    public void setNotaCorretagems(Set<NotaCorretagem> notaCorretagems) {
        this.notaCorretagems = notaCorretagems;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Corretora)) {
            return false;
        }
        return id != null && id.equals(((Corretora) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Corretora{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
