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
import com.participante.evento.entities.Participante;
import com.participante.evento.repository.EventoRepository;
import com.participante.evento.repository.ParticipanteRepository;

@Service
public class ParticipanteService {

    @Autowired
    private ParticipanteRepository participanteRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Transactional(readOnly = true)
    public List<ParticipanteDTO> listarTodos() {
        return participanteRepository.findAll().stream()
                .map(ParticipanteDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ParticipanteDTO buscarPorId(Long id) {
        Participante participante = participanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Participante não encontrado com ID: " + id));
        return new ParticipanteDTO(participante);
    }

    @Transactional
    public ParticipanteDTO cadastrarParticipante(ParticipanteDTO participanteDTO) {
        Participante participante = new Participante();
        participante.setNome(participanteDTO.getNome());
        participante.setEmail(participanteDTO.getEmail());
        participante.setTelefone(participanteDTO.getTelefone());
        
        Participante participanteSalvo = participanteRepository.save(participante);
        return new ParticipanteDTO(participanteSalvo);
    }

    @Transactional
    public ParticipanteDTO atualizarParticipante(Long id, ParticipanteDTO participanteDTO) {
        Participante participanteExistente = participanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Participante não encontrado com ID: " + id));
        
        participanteExistente.setNome(participanteDTO.getNome());
        participanteExistente.setEmail(participanteDTO.getEmail());
        participanteExistente.setTelefone(participanteDTO.getTelefone());
        
        Participante participanteAtualizado = participanteRepository.save(participanteExistente);
        return new ParticipanteDTO(participanteAtualizado);
    }

    @Transactional
    public void excluirParticipante(Long id) {
        Participante participante = participanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Participante não encontrado com ID: " + id));
        
        // Remove todas as associações com eventos
        participante.getEventos().forEach(e -> e.getParticipantes().remove(participante));
        participante.getEventos().clear();
        
        participanteRepository.delete(participante);
    }

    @Transactional
    public void inscreverEmEvento(Long participanteId, Long eventoId) {
        Participante participante = participanteRepository.findById(participanteId)
                .orElseThrow(() -> new RuntimeException("Participante não encontrado"));
        
        Evento evento = eventoRepository.findById(eventoId)
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
    
    @Transactional(readOnly = true)
    public Set<EventoDTO> listarEventosPorParticipante(Long participanteId) {
        Participante participante = participanteRepository.findById(participanteId)
                .orElseThrow(() -> new RuntimeException("Participante não encontrado"));

        return participante.getEventos().stream()
                .map(EventoDTO::new)
                .collect(Collectors.toSet());
    }

    @Transactional
    public void cancelarInscricao(Long participanteId, Long eventoId) {
        Participante participante = participanteRepository.findById(participanteId)
                .orElseThrow(() -> new RuntimeException("Participante não encontrado"));
        
        Evento evento = eventoRepository.findById(eventoId)
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

    @Transactional(readOnly = true)
    public Set<ParticipanteDTO> listarParticipantesPorEvento(Long eventoId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
        
        return evento.getParticipantes().stream()
                .map(ParticipanteDTO::new)
                .collect(Collectors.toSet());
    }
}