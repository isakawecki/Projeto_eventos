package com.participante.evento.controller;

import com.participante.evento.DTO.EventoDTO;
import com.participante.evento.DTO.ParticipanteDTO;
import com.participante.evento.services.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public ResponseEntity<List<EventoDTO>> listarTodos() {
        List<EventoDTO> eventos = eventoService.listarTodos();
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<EventoDTO>> listarDisponiveis() {
        List<EventoDTO> eventos = eventoService.listarEventosDisponiveis();
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> buscarPorId(@PathVariable Long id) {
        EventoDTO evento = eventoService.buscarPorId(id);
        return ResponseEntity.ok(evento);
    }

    @PostMapping
    public ResponseEntity<EventoDTO> criar(@RequestBody EventoDTO eventoDTO) {
        EventoDTO novoEvento = eventoService.criarEvento(eventoDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoEvento.getId())
                .toUri();
        return ResponseEntity.created(location).body(novoEvento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoDTO> atualizar(
            @PathVariable Long id, 
            @RequestBody EventoDTO eventoDTO) {
        EventoDTO eventoAtualizado = eventoService.atualizarEvento(id, eventoDTO);
        return ResponseEntity.ok(eventoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        eventoService.excluirEvento(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/participantes")
    public ResponseEntity<Set<ParticipanteDTO>> listarParticipantes(@PathVariable Long id) {
        Set<ParticipanteDTO> participantes = eventoService.listarParticipantesPorEvento(id);
        return ResponseEntity.ok(participantes);
    }
}