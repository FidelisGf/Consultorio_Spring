package br.com.uniamerica.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "historicos", schema = "public")
public class Historico extends AbstractEntity {

    @Getter @Setter
    @Column(name = "data", nullable = false)
    private LocalDateTime data;

    @Getter @Setter
    @Column(name = "observacao", columnDefinition = "TEXT")
    private String observacao;

    @Getter @Setter
    @JoinColumn(name = "id_secretatia")
    @ManyToOne(fetch = FetchType.EAGER)
    private Secretaria secretaria;

    @Getter @Setter
    @JoinColumn(name = "id_paciente")
    @ManyToOne(fetch = FetchType.EAGER)
    private Paciente paciente;

    @Getter @Setter
    @JoinColumn(name = "id_agenda")
    @ManyToOne(fetch = FetchType.EAGER)
    private Agenda agenda;

}
