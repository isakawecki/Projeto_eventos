package com.participante.evento.DTO;

import com.participante.evento.entities.Participante;

public class ParticipanteResumoDTO {
    private Long id;
    private String nome;
    private String email;

    // Construtores
    public ParticipanteResumoDTO() {
    }

    public ParticipanteResumoDTO(Participante participante) {
        this.id = participante.getId();
        this.nome = participante.getNome();
        this.email = participante.getEmail();
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
}