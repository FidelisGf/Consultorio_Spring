package br.com.uniamerica.api.service;

import br.com.uniamerica.api.entity.Agenda;
import br.com.uniamerica.api.entity.Historico;
import br.com.uniamerica.api.repository.AgendaRepository;
import br.com.uniamerica.api.repository.HistoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class HistoricoService {
    @Autowired
    private HistoricoRepository historicoRepository;
    @Autowired
    private AgendaRepository agendaRepository;
    /**
     *
     * @param id
     * @return
     */
    public Optional<Historico> findById(Long id){
        return this.historicoRepository.findById(id);
    }

    /**
     *
     * @param pageable
     * @return
     */
    public Page<Historico> listAll(Pageable pageable){
        return this.historicoRepository.findAll(pageable);
    }

    /**
     *
     * @param id
     * @param historico
     */
    @Transactional
    public void update(Long id, Historico historico){
        if (id == historico.getId()) {
            this.historicoRepository.save(historico);
        }
        else {
            throw new RuntimeException();
        }
    }

    /**
     *
     * @param historico
     */
    @Transactional
    public void insert(Historico historico){
        this.historicoRepository.save(historico);
    }
}
