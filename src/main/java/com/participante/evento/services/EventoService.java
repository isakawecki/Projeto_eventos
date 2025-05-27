package com.participante.evento.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.participante.evento.DTO.EventoDTO;
import com.participante.evento.DTO.ParticipanteDTO;
import com.participante.evento.entities.Evento;
import com.participante.evento.repository.EventoRepository;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Transactional(readOnly = true)
    public List<EventoDTO> listarTodos() {
        return eventoRepository.findAll().stream()
                .map(EventoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EventoDTO buscarPorId(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado com ID: " + id));
        return new EventoDTO(evento);
    }

    @Transactional(readOnly = true)
    public Set<ParticipanteDTO> listarParticipantesPorEvento(Long eventoId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
        
        return evento.getParticipantes().stream()
                .map(ParticipanteDTO::new)
                .collect(Collectors.toSet());
    }
    
    @Transactional
    public EventoDTO criarEvento(EventoDTO eventoDTO) {
        Evento evento = new Evento();
        evento.setNome(eventoDTO.getNome());
        evento.setDescricao(eventoDTO.getDescricao());
        evento.setData(eventoDTO.getData());
        evento.setLocal(eventoDTO.getLocal());
        evento.setVagas(eventoDTO.getVagas());
        
        Evento eventoSalvo = eventoRepository.save(evento);
        return new EventoDTO(eventoSalvo);
    }

    @Transactional
    public EventoDTO atualizarEvento(Long id, EventoDTO eventoDTO) {
        Evento eventoExistente = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado com ID: " + id));
        
        eventoExistente.setNome(eventoDTO.getNome());
        eventoExistente.setDescricao(eventoDTO.getDescricao());
        eventoExistente.setData(eventoDTO.getData());
        eventoExistente.setLocal(eventoDTO.getLocal());
        eventoExistente.setVagas(eventoDTO.getVagas());
        
        Evento eventoAtualizado = eventoRepository.save(eventoExistente);
        return new EventoDTO(eventoAtualizado);
    }

    @Transactional
    public void excluirEvento(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado com ID: " + id));
        
        // Remove todas as associações com participantes
        evento.getParticipantes().forEach(p -> p.getEventos().remove(evento));
        evento.getParticipantes().clear();
        
        eventoRepository.delete(evento);
    }

    @Transactional(readOnly = true)
    public List<EventoDTO> listarEventosDisponiveis() {
        return eventoRepository.findByVagasDisponiveisGreaterThan(0).stream()
                .map(EventoDTO::new)
                .collect(Collectors.toList());
    }
}