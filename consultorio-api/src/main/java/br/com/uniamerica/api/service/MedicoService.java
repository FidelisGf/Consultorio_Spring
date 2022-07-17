package br.com.uniamerica.api.service;

import br.com.uniamerica.api.entity.Medico;
import br.com.uniamerica.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    /**
     *
     * @param id
     * @return
     */
    public Optional<Medico> findById(Long id){
        return this.medicoRepository.findById(id);
    }

    /**
     *
     * @param pageable
     * @return
     */
    public Page<Medico> findAll(Pageable pageable){
        return this.medicoRepository.findAll(pageable);
    }

    /**
     *
     * @param id
     * @param medico
     */
    @Transactional
    public void update(Long id, Medico medico){
        if (id == medico.getId()) {
            this.medicoRepository.save(medico);
        }
        else {
            throw new RuntimeException();
        }
    }

    /**
     *
     * @param medico
     */
    @Transactional
    public void insert(Medico medico){
        this.medicoRepository.save(medico);
    }

    /**
     *
     * @param id
     * @param medico
     */
    @Transactional
    public void updateStatus(Long id, Medico medico){
        if (id == medico.getId()) {
            this.medicoRepository.desativar(medico.getId());
        }
        else {
            throw new RuntimeException();
        }
    }
}
