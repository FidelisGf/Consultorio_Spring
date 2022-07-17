package br.com.uniamerica.api.repository;
import java.time.LocalDateTime;
import br.com.uniamerica.api.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    @Modifying
    @Query("UPDATE Paciente paciente SET paciente.ativo = false WHERE paciente.id = :idPaciente")
    public void desativar(@Param("idPaciente") Long idPaciente);
}
