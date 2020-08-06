package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.mycompany.myapp.domain.enumeration.TipoTransacao;

/**
 * A Transacao.
 */
@Entity
@Table(name = "transacao")
public class Transacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "quantidade")
    private Long quantidade;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "data")
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoTransacao tipo;

    @ManyToOne
    @JsonIgnoreProperties(value = "transacaos", allowSetters = true)
    private Papel papel;

    @ManyToOne
    @JsonIgnoreProperties(value = "transacaos", allowSetters = true)
    private NotaCorretagem notaCorretagem;

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

    public Transacao quantidade(Long quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValor() {
        return valor;
    }

    public Transacao valor(Double valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public Transacao data(LocalDate data) {
        this.data = data;
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public Transacao tipo(TipoTransacao tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }

    public Papel getPapel() {
        return papel;
    }

    public Transacao papel(Papel papel) {
        this.papel = papel;
        return this;
    }

    public void setPapel(Papel papel) {
        this.papel = papel;
    }

    public NotaCorretagem getNotaCorretagem() {
        return notaCorretagem;
    }

    public Transacao notaCorretagem(NotaCorretagem notaCorretagem) {
        this.notaCorretagem = notaCorretagem;
        return this;
    }

    public void setNotaCorretagem(NotaCorretagem notaCorretagem) {
        this.notaCorretagem = notaCorretagem;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transacao)) {
            return false;
        }
        return id != null && id.equals(((Transacao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transacao{" +
            "id=" + getId() +
            ", quantidade=" + getQuantidade() +
            ", valor=" + getValor() +
            ", data='" + getData() + "'" +
            ", tipo='" + getTipo() + "'" +
            "}";
    }
}
