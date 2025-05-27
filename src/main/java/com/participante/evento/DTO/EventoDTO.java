package com.participante.evento.DTO;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import com.participante.evento.entities.Evento;

public class EventoDTO {
    private Long id;
    private String nome;
    private String descricao;
    private LocalDate data;
    private String local;
    private Integer vagas;
    private Integer vagasDisponiveis;
    private Integer quantidadeParticipantes;
    private Set<ParticipanteResumoDTO> participantes;

    // Construtores
    public EventoDTO() {
    }

    public EventoDTO(Evento evento) {
        this.id = evento.getId();
        this.nome = evento.getNome();
        this.descricao = evento.getDescricao();
        this.data = evento.getData();
        this.local = evento.getLocal();
        this.vagas = evento.getVagas();
        this.vagasDisponiveis = evento.getVagasDisponiveis();
        this.quantidadeParticipantes = evento.getQuantidadeParticipantes();
        
        if(evento.getParticipantes() != null) {
            this.participantes = evento.getParticipantes().stream()
                    .map(ParticipanteResumoDTO::new)
                    .collect(Collectors.toSet());
        }
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
    }

    public Integer getVagasDisponiveis() {
        return vagasDisponiveis;
    }

    public void setVagasDisponiveis(Integer vagasDisponiveis) {
        this.vagasDisponiveis = vagasDisponiveis;
    }

    public Integer getQuantidadeParticipantes() {
        return quantidadeParticipantes;
    }

    public void setQuantidadeParticipantes(Integer quantidadeParticipantes) {
        this.quantidadeParticipantes = quantidadeParticipantes;
    }

    public Set<ParticipanteResumoDTO> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(Set<ParticipanteResumoDTO> participantes) {
        this.participantes = participantes;
    }
}