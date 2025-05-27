package com.participante.evento.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.participante.evento.DTO.InscricaoDTO;
import com.participante.evento.entities.Evento;
import com.participante.evento.entities.Participante;
import com.participante.evento.repository.EventoRepository;
import com.participante.evento.repository.ParticipanteRepository;

@Service
public class InscricaoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ParticipanteRepository participanteRepository;

    @Transactional
    public void realizarInscricao(InscricaoDTO inscricaoDTO) {
        Participante participante = participanteRepository.findById(inscricaoDTO.getParticipanteId())
                .orElseThrow(() -> new RuntimeException("Participante não encontrado"));
        
        Evento evento = eventoRepository.findById(inscricaoDTO.getEventoId())
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
        
        if (evento.getParticipantes().contains(participante)) {
            throw new RuntimeException("Participante já está inscrito neste evento");
        }
        
        if (evento.getVagasDisponiveis() <= 0) {
            throw new RuntimeException("Não há vagas disponíveis para este evento");
        }
        
        evento.getParticipantes().add(participante);
        participante.getEventos().add(evento);
        evento.setVagasDisponiveis(evento.getVagasDisponiveis() - 1);
        
        eventoRepository.save(evento);
        participanteRepository.save(participante);
    }

    @Transactional
    public void cancelarInscricao(InscricaoDTO inscricaoDTO) {
        Participante participante = participanteRepository.findById(inscricaoDTO.getParticipanteId())
                .orElseThrow(() -> new RuntimeException("Participante não encontrado"));
        
        Evento evento = eventoRepository.findById(inscricaoDTO.getEventoId())
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
        
        if (!evento.getParticipantes().contains(participante)) {
            throw new RuntimeException("Participante não está inscrito neste evento");
        }
        
        evento.getParticipantes().remove(participante);
        participante.getEventos().remove(evento);
        evento.setVagasDisponiveis(evento.getVagasDisponiveis() + 1);
        
        eventoRepository.save(evento);
        participanteRepository.save(participante);
    }
}