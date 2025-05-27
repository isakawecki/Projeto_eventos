package com.participante.evento.DTO;

import java.util.Set;
import java.util.stream.Collectors;

import com.participante.evento.entities.Participante;

public class ParticipanteDTO {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private Integer quantidadeEventos;
    private Set<EventoResumoDTO> eventos;

    // Construtores
    public ParticipanteDTO() {
    }

    public ParticipanteDTO(Participante participante) {
        this.id = participante.getId();
        this.nome = participante.getNome();
        this.email = participante.getEmail();
        this.telefone = participante.getTelefone();
        this.quantidadeEventos = participante.getQuantidadeEventos();
        
        if(participante.getEventos() != null) {
            this.eventos = participante.getEventos().stream()
                    .map(EventoResumoDTO::new)
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Integer getQuantidadeEventos() {
        return quantidadeEventos;
    }

    public void setQuantidadeEventos(Integer quantidadeEventos) {
        this.quantidadeEventos = quantidadeEventos;
    }

    public Set<EventoResumoDTO> getEventos() {
        return eventos;
    }

    public void setEventos(Set<EventoResumoDTO> eventos) {
        this.eventos = eventos;
    }
}