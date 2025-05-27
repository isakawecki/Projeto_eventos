package com.participante.evento.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.participante.evento.entities.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

List<Evento> findByVagasDisponiveisGreaterThan(int vagas);
    
    @Query("SELECT e FROM Evento e JOIN e.participantes p WHERE p.id = :participanteId")
    Set<Evento> findEventosByParticipanteId(@Param("participanteId") Long participanteId);
}

