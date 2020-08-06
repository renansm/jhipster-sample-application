package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A NotaCorretagem.
 */
@Entity
@Table(name = "nota_corretagem")
public class NotaCorretagem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero")
    private Long numero;

    @Column(name = "emolumentos")
    private Double emolumentos;

    @Column(name = "liquidacao")
    private Double liquidacao;

    @Column(name = "outras_taxas")
    private Double outrasTaxas;

    @Column(name = "valor")
    private Double valor;

    @OneToMany(mappedBy = "notaCorretagem")
    private Set<Transacao> transacaos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "notaCorretagems", allowSetters = true)
    private Corretora corretora;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumero() {
        return numero;
    }

    public NotaCorretagem numero(Long numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Double getEmolumentos() {
        return emolumentos;
    }

    public NotaCorretagem emolumentos(Double emolumentos) {
        this.emolumentos = emolumentos;
        return this;
    }

    public void setEmolumentos(Double emolumentos) {
        this.emolumentos = emolumentos;
    }

    public Double getLiquidacao() {
        return liquidacao;
    }

    public NotaCorretagem liquidacao(Double liquidacao) {
        this.liquidacao = liquidacao;
        return this;
    }

    public void setLiquidacao(Double liquidacao) {
        this.liquidacao = liquidacao;
    }

    public Double getOutrasTaxas() {
        return outrasTaxas;
    }

    public NotaCorretagem outrasTaxas(Double outrasTaxas) {
        this.outrasTaxas = outrasTaxas;
        return this;
    }

    public void setOutrasTaxas(Double outrasTaxas) {
        this.outrasTaxas = outrasTaxas;
    }

    public Double getValor() {
        return valor;
    }

    public NotaCorretagem valor(Double valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Set<Transacao> getTransacaos() {
        return transacaos;
    }

    public NotaCorretagem transacaos(Set<Transacao> transacaos) {
        this.transacaos = transacaos;
        return this;
    }

    public NotaCorretagem addTransacao(Transacao transacao) {
        this.transacaos.add(transacao);
        transacao.setNotaCorretagem(this);
        return this;
    }

    public NotaCorretagem removeTransacao(Transacao transacao) {
        this.transacaos.remove(transacao);
        transacao.setNotaCorretagem(null);
        return this;
    }

    public void setTransacaos(Set<Transacao> transacaos) {
        this.transacaos = transacaos;
    }

    public Corretora getCorretora() {
        return corretora;
    }

    public NotaCorretagem corretora(Corretora corretora) {
        this.corretora = corretora;
        return this;
    }

    public void setCorretora(Corretora corretora) {
        this.corretora = corretora;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotaCorretagem)) {
            return false;
        }
        return id != null && id.equals(((NotaCorretagem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotaCorretagem{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", emolumentos=" + getEmolumentos() +
            ", liquidacao=" + getLiquidacao() +
            ", outrasTaxas=" + getOutrasTaxas() +
            ", valor=" + getValor() +
            "}";
    }
}
