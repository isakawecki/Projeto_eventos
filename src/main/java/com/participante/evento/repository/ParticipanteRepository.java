package com.participante.evento.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.participante.evento.entities.Participante;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Long> {

	 @Query("SELECT p FROM Participante p JOIN p.eventos e WHERE e.id = :eventoId")
	    Set<Participante> findParticipantesByEventoId(@Param("eventoId") Long eventoId);
}
