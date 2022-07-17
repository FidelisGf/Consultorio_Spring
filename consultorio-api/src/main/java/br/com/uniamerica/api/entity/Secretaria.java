package br.com.uniamerica.api.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "secretarias", schema = "public")
public class Secretaria extends Pessoa {

    @Getter @Setter
    @Digits(integer = 5, fraction = 3)
    @Column(name = "salario", precision = 5, scale = 3,nullable = false)
    private BigDecimal salario;

    @Getter @Setter
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataContratacao;

    @Getter @Setter
    @Column(name = "pis", nullable = false, length = 20, unique = true)
    private String pis;

}
