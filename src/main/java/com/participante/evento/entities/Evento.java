package com.participante.evento.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(columnDefinition = "TEXT")
    private String descricao;
    
    @Column(nullable = false)
    private LocalDate data;
    
    @Column(nullable = false)
    private String local;
    
    @Column(nullable = false)
    private Integer vagas;
    
    @Column(nullable = false)
    private Integer vagasDisponiveis;

    @ManyToMany
    @JoinTable(name = "tb_evento_participante",
            joinColumns = @JoinColumn(name = "evento_id"),
            inverseJoinColumns = @JoinColumn(name = "participante_id"))
    private Set<Participante> participantes = new HashSet<>();

    // Construtores
    public Evento() {
    }

    public Evento(Long id, String nome, String descricao, LocalDate data, 
                 String local, Integer vagas) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.local = local;
        this.vagas = vagas;
        this.vagasDisponiveis = vagas;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Integer getVagas() {
        return vagas;
    }

    public void setVagas(Integer vagas) {
        this.vagas = vagas;
        if (this.vagasDisponiveis == null) {
            this.vagasDisponiveis = vagas;
        }
    }

    public Integer getVagasDisponiveis() {
        return vagasDisponiveis;
    }

    public void setVagasDisponiveis(Integer vagasDisponiveis) {
        this.vagasDisponiveis = vagasDisponiveis;
    }

    public Set<Participante> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(Set<Participante> participantes) {
        this.participantes = participantes;
    }

    // Métodos auxiliares
    public int getQuantidadeParticipantes() {
        return this.participantes != null ? this.participantes.size() : 0;
    }

    public boolean inscreverParticipante(Participante participante) {
        if (this.vagasDisponiveis != null && this.vagasDisponiveis > 0 && 
            !participantes.contains(participante)) {
            participantes.add(participante);
            participante.getEventos().add(this);
            this.vagasDisponiveis -= 1;
            return true;
        }
        return false;
    }

    public boolean cancelarInscricao(Participante participante) {
        if (participantes.remove(participante)) {
            participante.getEventos().remove(this);
            this.vagasDisponiveis += 1;
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Evento evento)) return false;
        return getId().equals(evento.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}