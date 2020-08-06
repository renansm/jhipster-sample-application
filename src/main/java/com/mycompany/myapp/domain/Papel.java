package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Papel.
 */
@Entity
@Table(name = "papel")
public class Papel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nome")
    private String nome;

    @Column(name = "preco_mercado")
    private Double precoMercado;

    @OneToMany(mappedBy = "papel")
    private Set<Posicao> posicaos = new HashSet<>();

    @OneToMany(mappedBy = "papel")
    private Set<Transacao> transacaos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public Papel codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public Papel nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPrecoMercado() {
        return precoMercado;
    }

    public Papel precoMercado(Double precoMercado) {
        this.precoMercado = precoMercado;
        return this;
    }

    public void setPrecoMercado(Double precoMercado) {
        this.precoMercado = precoMercado;
    }

    public Set<Posicao> getPosicaos() {
        return posicaos;
    }

    public Papel posicaos(Set<Posicao> posicaos) {
        this.posicaos = posicaos;
        return this;
    }

    public Papel addPosicao(Posicao posicao) {
        this.posicaos.add(posicao);
        posicao.setPapel(this);
        return this;
    }

    public Papel removePosicao(Posicao posicao) {
        this.posicaos.remove(posicao);
        posicao.setPapel(null);
        return this;
    }

    public void setPosicaos(Set<Posicao> posicaos) {
        this.posicaos = posicaos;
    }

    public Set<Transacao> getTransacaos() {
        return transacaos;
    }

    public Papel transacaos(Set<Transacao> transacaos) {
        this.transacaos = transacaos;
        return this;
    }

    public Papel addTransacao(Transacao transacao) {
        this.transacaos.add(transacao);
        transacao.setPapel(this);
        return this;
    }

    public Papel removeTransacao(Transacao transacao) {
        this.transacaos.remove(transacao);
        transacao.setPapel(null);
        return this;
    }

    public void setTransacaos(Set<Transacao> transacaos) {
        this.transacaos = transacaos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Papel)) {
            return false;
        }
        return id != null && id.equals(((Papel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Papel{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nome='" + getNome() + "'" +
            ", precoMercado=" + getPrecoMercado() +
            "}";
    }
}
