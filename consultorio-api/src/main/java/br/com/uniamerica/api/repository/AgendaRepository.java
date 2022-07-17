package br.com.uniamerica.api.repository;

import br.com.uniamerica.api.entity.Agenda;
import br.com.uniamerica.api.entity.Medico;
import br.com.uniamerica.api.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    @Modifying
    @Query("UPDATE Agenda agenda SET agenda.ativo = false WHERE agenda.id = :idAgenda")
    public void desativar(@Param("idAgenda") Long idAgenda);

    @Modifying
    @Query("FROM Agenda agenda " +
            "WHERE 1=1 " +
            "   AND (" +
            "       :dataDe BETWEEN agenda.dataDe AND agenda.dataAte " +
            "       OR " +
            "       :dataAte BETWEEN agenda.dataDe AND agenda.dataAte" +
            "   ) " +
            "   AND (" +
            "       :medico = agenda.medico " +
            "       OR " +
            "       :paciente = agenda.paciente" +
            "   ) " +
            "   AND :agenda <> agenda " +
            "   AND agenda.ativo = true ")
    public List<Agenda> agendasConflitoHorariosMedico(
            @Param("agenda") Agenda agenda,
            @Param("dataDe") LocalDateTime localDateTimeDe,
            @Param("dataAte") LocalDateTime localDateTimeAte,
            @Param("medico") Medico medico,
            @Param("paciente") Paciente paciente);

    @Query("SELECT coalesce(max(ch.id), 0) FROM Agenda ch")
    public Integer pegaUltimoId(
    );

}