package br.com.uniamerica.api.service;

import br.com.uniamerica.api.entity.Secretaria;
import br.com.uniamerica.api.repository.SecretariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class SecretariaService {

    @Autowired
    private SecretariaRepository secretariaRepository;

    /**
     *
     * @param id
     * @return
     */
    public Optional<Secretaria> findById(Long id){
        return this.secretariaRepository.findById(id);
    }

    /**
     *
     * @param pageable
     * @return
     */
    public Page<Secretaria> listAll(Pageable pageable){
        return this.secretariaRepository.findAll(pageable);
    }

    /**
     *
     * @param id
     * @param secretaria
     */
    @Transactional
    public void update(Long id, Secretaria secretaria){
        if (id == secretaria.getId()) {
            this.secretariaRepository.save(secretaria);
        }
        else {
            throw new RuntimeException();
        }
    }

    /**
     *
     * @param secretaria
     */
    @Transactional
    public void insert(Secretaria secretaria){
        this.secretariaRepository.save(secretaria);
    }

    /**
     *
     * @param id
     * @param secretaria
     */
    @Transactional
    public void updateStatus(Long id, Secretaria secretaria){
        if (id == secretaria.getId()) {
            this.secretariaRepository.desativar(secretaria.getId());
        }
        else {
            throw new RuntimeException();
        }
    }
}
