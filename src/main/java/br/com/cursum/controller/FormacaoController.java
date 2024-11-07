package br.com.cursum.controller;

import br.com.cursum.dto.FormacaoDTO;
import br.com.cursum.service.FormacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/formacoes")
public class FormacaoController {

    @Autowired
    private FormacaoService formacaoService;

    @GetMapping
    public ResponseEntity<List<FormacaoDTO>> buscarTodas() {
        List<FormacaoDTO> formacoes = formacaoService.buscarTodas();
        return ResponseEntity.ok(formacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormacaoDTO> buscarPorId(@PathVariable Long id) {
        FormacaoDTO formacao = formacaoService.buscarPorId(id);
        return ResponseEntity.ok(formacao);
    }

//    @PostMapping
//    public ResponseEntity<FormacaoDTO> criarFormacao(@RequestBody FormacaoDTO formacaoDTO) {
//        FormacaoDTO novaFormacao = formacaoService.criarFormacao(formacaoDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).body(novaFormacao);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<FormacaoDTO> atualizarFormacao(@PathVariable Long id, @RequestBody FormacaoDTO formacaoDTO) {
//        FormacaoDTO formacaoAtualizada = formacaoService.atualizarFormacao(id, formacaoDTO);
//        return ResponseEntity.ok(formacaoAtualizada);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> excluirFormacao(@PathVariable Long id) {
//        formacaoService.excluirFormacao(id);
//        return ResponseEntity.noContent().build();
//    }
}
