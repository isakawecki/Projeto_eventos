package com.participante.evento.DTO;

public class InscricaoDTO {
    private Long eventoId;
    private Long participanteId;

    // Construtores
    public InscricaoDTO() {
    }

    public InscricaoDTO(Long eventoId, Long participanteId) {
        this.eventoId = eventoId;
        this.participanteId = participanteId;
    }

    // Getters e Setters
    public Long getEventoId() {
        return eventoId;
    }

    public void setEventoId(Long eventoId) {
        this.eventoId = eventoId;
    }

    public Long getParticipanteId() {
        return participanteId;
    }

    public void setParticipanteId(Long participanteId) {
        this.participanteId = participanteId;
    }
}