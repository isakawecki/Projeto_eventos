package com.participante.evento.DTO;

import com.participante.evento.entities.Evento;
import java.time.LocalDate;

public class EventoResumoDTO {
    private Long id;
    private String nome;
    private LocalDate data;
    private String local;

    // Construtores
    public EventoResumoDTO() {
    }

    public EventoResumoDTO(Evento evento) {
        this.id = evento.getId();
        this.nome = evento.getNome();
        this.data = evento.getData();
        this.local = evento.getLocal();
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
}