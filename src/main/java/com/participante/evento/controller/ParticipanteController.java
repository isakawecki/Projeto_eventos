package com.participante.evento.controller;

import java.net.URI;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.participante.evento.DTO.EventoDTO;
import com.participante.evento.DTO.ParticipanteDTO;
import com.participante.evento.services.ParticipanteService;

@RestController
@RequestMapping("/api/participantes")
public class ParticipanteController {

    @Autowired
    private ParticipanteService participanteService;

    @GetMapping
    public ResponseEntity<List<ParticipanteDTO>> listarTodos() {
        List<ParticipanteDTO> participantes = participanteService.listarTodos();
        return ResponseEntity.ok(participantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipanteDTO> buscarPorId(@PathVariable Long id) {
        ParticipanteDTO participante = participanteService.buscarPorId(id);
        return ResponseEntity.ok(participante);
    }

    @PostMapping
    public ResponseEntity<ParticipanteDTO> criar(@RequestBody ParticipanteDTO participanteDTO) {
        ParticipanteDTO novoParticipante = participanteService.cadastrarParticipante(participanteDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoParticipante.getId())
                .toUri();
        return ResponseEntity.created(location).body(novoParticipante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParticipanteDTO> atualizar(
            @PathVariable Long id,
            @RequestBody ParticipanteDTO participanteDTO) {
        ParticipanteDTO participanteAtualizado = participanteService.atualizarParticipante(id, participanteDTO);
        return ResponseEntity.ok(participanteAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        participanteService.excluirParticipante(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{participanteId}/inscrever/{eventoId}")
    public ResponseEntity<Void> inscreverEmEvento(
            @PathVariable Long participanteId,
            @PathVariable Long eventoId) {
        participanteService.inscreverEmEvento(participanteId, eventoId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{participanteId}/cancelar/{eventoId}")
    public ResponseEntity<Void> cancelarInscricao(
            @PathVariable Long participanteId,
            @PathVariable Long eventoId) {
        participanteService.cancelarInscricao(participanteId, eventoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/eventos")
    public ResponseEntity<Set<EventoDTO>> listarEventosDoParticipante(@PathVariable Long id) {
        Set<EventoDTO> eventos = participanteService.listarEventosPorParticipante(id);
        return ResponseEntity.ok(eventos);
    }
}