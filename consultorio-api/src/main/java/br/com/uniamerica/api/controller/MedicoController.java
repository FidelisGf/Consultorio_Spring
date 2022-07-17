package br.com.uniamerica.api.controller;

import br.com.uniamerica.api.entity.Medico;
import br.com.uniamerica.api.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/api/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping("/{idMedico}")
    public ResponseEntity<Medico> findById(
            @PathVariable("idMedico") Long idMedico
    ){
        return ResponseEntity.ok().body(this.medicoService.findById(idMedico).get());
    }

    @GetMapping
    public ResponseEntity<Page<Medico>> listByAllPage(
            Pageable pageable
    ){
        return ResponseEntity.ok().body(this.medicoService.findAll(pageable));
    }

    @PutMapping("/{idMedico}")
    public ResponseEntity<?> update(
            @PathVariable Long idMedico,
            @RequestBody Medico medico
    ){
        try{
            this.medicoService.update(idMedico, medico);
            return ResponseEntity.ok().body("Medico Atualizado com sucesso!");
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> insert(
            @RequestBody Medico medico
    ){
        try{
            this.medicoService.insert(medico);
            return ResponseEntity.ok().body("Médico cadastrado com sucesso!");
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/status/{idMedico}")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long idMedico,
            @RequestBody Medico medico
    ){
        try{
            this.medicoService.updateStatus(idMedico, medico);
            return ResponseEntity.ok().body("Médico desativado com sucesso!");
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}