package br.com.uniamerica.api.controller;

import br.com.uniamerica.api.entity.Secretaria;
import br.com.uniamerica.api.service.SecretariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/api/secretarias")
public class SecretariaController {

    @Autowired
    private SecretariaService secretariaService;

    @GetMapping("/{idSecretaria}")
    public ResponseEntity<Secretaria> findById(
            @PathVariable("idSecretaria") Long idSecretaria
    ){
        return ResponseEntity.ok().body(this.secretariaService.findById(idSecretaria).get());
    }

    @GetMapping
    public ResponseEntity<Page<Secretaria>> listByAllPage(
            Pageable pageable
    ){
        return ResponseEntity.ok().body(this.secretariaService.listAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> insert(
            @RequestBody Secretaria secretaria
    ){
        try {
            this.secretariaService.insert(secretaria);
            return ResponseEntity.ok().body("Secretario(a) Cadastrado com Sucesso.");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{idSecretaria}")
    public ResponseEntity<?> update(
            @RequestBody Secretaria secretaria,
            @PathVariable Long idSecretaria
    ){
        try {
            this.secretariaService.update(idSecretaria, secretaria);
            return ResponseEntity.ok().body("Secretario(a) Atualizado com Sucesso.");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/status/{idSecretaria}")
    public ResponseEntity<?> updateStatus(
            @RequestBody Secretaria secretaria,
            @PathVariable Long idSecretaria
    ){
        try {
            this.secretariaService.updateStatus(idSecretaria, secretaria);
            return ResponseEntity.ok().body("Secretario(a) Desativado com Sucesso.");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
