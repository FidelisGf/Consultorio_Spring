package br.com.uniamerica.api.service;

import br.com.uniamerica.api.entity.Agenda;
import br.com.uniamerica.api.entity.StatusAgenda;
import br.com.uniamerica.api.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;

    /**
     *
     * @param id
     * @return
     */
    public Agenda findById(Long id){
        return this.agendaRepository.findById(id).orElse(new Agenda());
    }

    @Transactional
    public Integer getLastId(){return this.agendaRepository.pegaUltimoId();}
    /**
     *
     * @param pageable
     * @return
     */
    public Page<Agenda> listAll(Pageable pageable){
        return this.agendaRepository.findAll(pageable);
    }

    public Page<Agenda> listAllByDate(Pageable pageable){
        List<Agenda> list = new ArrayList<Agenda>();
        for (Agenda item: agendaRepository.findAll()
             ) {    if( item.getDataDe().getDayOfMonth() == LocalDateTime.now().getDayOfMonth()){
                 list.add(item);

        }

        }
        Page<Agenda> page = new PageImpl<>(list);
        return page;
    }

    /**
     *
     * @param agenda
     */
    public void insert(final Agenda agenda){
        this.realizarValidacoes(agenda);
        this.saveTransaction(agenda);

    }
    /**
     *
     * @param idAgenda
     * @param agenda
     */
    public void update(final Long idAgenda, final Agenda agenda){

        this.realizarValidacoes(agenda);

        if (!agenda.getEncaixe()) {
            Assert.isTrue(this.agendaRepository.agendasConflitoHorariosMedico(
                    agenda,
                    agenda.getDataDe(),
                    agenda.getDataAte(),
                    agenda.getMedico(),
                    agenda.getPaciente()
            ).size() == 0, "Error: Gerou um conflito entre horários, sugira outro horário.");
        }

        this.saveTransaction(agenda);
    }

    /**
     *
     * @param agenda
     */
    public void realizarValidacoes(final Agenda agenda){

        Assert.isTrue(this.dataNoFuturo(agenda.getDataAte()),
                "Error: Data Até é menor que a data atual.");
        Assert.isTrue(this.dataNoFuturo(agenda.getDataDe()),
                "Error: Data De é menor que a data atual.");


        Assert.isTrue(this.dataDeMaiorQueDataAte(agenda.getDataDe(), agenda.getDataAte()),
                "Error: Data Até é maior que a Data De.");


        Assert.isTrue(this.horarioComercial(agenda.getDataAte()),
                "Error: A Data De está fora do horário comercial. (8 as 12 e 14 as 18)");
        Assert.isTrue(this.horarioComercial(agenda.getDataDe()),
                "Error: A Data Até está fora do horário comercial. (8 as 12 e 14 as 18)");


        Assert.isTrue(this.finalSemana(agenda.getDataDe()),
                "Error: A Data De informada será em um final de semana.");
        Assert.isTrue(this.finalSemana(agenda.getDataAte()),
                "Error: A Data Até informada será em um final de semana.");
    }

    /**
     *
     * @param agenda
     */
    @Transactional
    public void saveTransaction(final Agenda agenda){
        this.agendaRepository.save(agenda);
    }

    /**
     *
     * @param id
     * @param agenda
     */
    @Transactional
    public void desativar(final Long id, final Agenda agenda) {
        if (id == agenda.getId()) {
            this.agendaRepository.desativar(agenda.getId());
        } else {
            throw new RuntimeException("Error: Informações inconsistente, tente novamento mais tarde;");
        }
    }

    /**
     *
     * @param localDateTime
     * @return
     */
    private boolean dataNoFuturo(final LocalDateTime localDateTime){
        return localDateTime.isAfter(LocalDateTime.now());
    }

    /**
     *
     * @param localDateTimeDe
     * @param localDateTimeAte
     * @return
     */
    private boolean dataDeMaiorQueDataAte(final LocalDateTime localDateTimeDe, final LocalDateTime localDateTimeAte){
        return localDateTimeDe.isBefore(localDateTimeAte) ? true : false;
    }

    /**
     *
     * @param localDateTime
     * @return
     */
    private boolean horarioComercial(final LocalDateTime localDateTime){
        return (localDateTime.getHour() >= 8  && localDateTime.getHour() < 12)
                || (localDateTime.getHour() >= 14 && localDateTime.getHour() < 18) ? true : false;
    }

    /**
     *
     * @param localDateTime
     * @return
     */
    private boolean finalSemana(final LocalDateTime localDateTime){
        return localDateTime.getDayOfWeek().equals(DayOfWeek.SUNDAY)
                || localDateTime.getDayOfWeek().equals(DayOfWeek.SATURDAY) ? false : true;
    }
}