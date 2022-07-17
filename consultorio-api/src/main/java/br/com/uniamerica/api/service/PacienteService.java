package br.com.uniamerica.api.service;

import br.com.uniamerica.api.entity.Paciente;
import br.com.uniamerica.api.entity.TipoAtendimento;
import br.com.uniamerica.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    /**
     *
     * @param id
     * @return
     */
    public Optional<Paciente> findById(Long id){
        return this.pacienteRepository.findById(id);
    }

    /**
     *
     * @param pageable
     * @return
     */
    public Page<Paciente> findAll(Pageable pageable){
        return this.pacienteRepository.findAll(pageable);
    }

    /**
     *
     * @param id
     * @param paciente
     */
    public void update(Long id, Paciente paciente){
        validarFormulario(paciente);
        saveTransactional(paciente);
    }

    public void insert(Paciente paciente){
        validarFormulario(paciente);
        saveTransactional(paciente);
    }

    /**
     *
     * @param paciente
     */
    @Transactional
    public void saveTransactional(Paciente paciente){
        this.pacienteRepository.save(paciente);
    }

    /**
     *
     * @param id
     * @param paciente
     */
    @Transactional
    public void updateStatus(Long id, Paciente paciente){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dataNow = LocalDateTime.parse(dtf.format(LocalDateTime.now()));
        if (id == paciente.getId()) {
            this.pacienteRepository.desativar(paciente.getId());
        }
        else {
            throw new RuntimeException();
        }
    }

    public void validarFormulario(Paciente paciente){
        if(paciente.getTipoAtendimento().equals(TipoAtendimento.convenio) ){
            if(paciente.getConvenio() == null || paciente.getConvenio().getId() == null){
                throw new RuntimeException("Convenio não informado");
            }
            if(paciente.getDataVencimento() == null){
                throw new RuntimeException("Data de vencimento não informada");
            }
            if(paciente.getNumeroCartaoConvenio() == null){
                throw new RuntimeException("Cartão convênio não informado");
            }
            if(paciente.getDataVencimento().compareTo(LocalDateTime.now()) <= 0){
                throw new RuntimeException("Data de vencimento do cartão está expirada");
            }
        }
        if(paciente.getTipoAtendimento().equals(TipoAtendimento.particular)){
            paciente.setConvenio(null);
            paciente.setNumeroCartaoConvenio(null);
            paciente.setDataVencimento(null);
        }
    }
}
