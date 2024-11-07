package br.com.cursum.controller;

import br.com.cursum.dto.AtividadeDTO;
import br.com.cursum.service.AtividadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atividades")
public class AtividadeController {

    @Autowired
    private AtividadeService atividadeService;

    @GetMapping
    public ResponseEntity<List<AtividadeDTO>> buscarTodas() {
        List<AtividadeDTO> atividades = atividadeService.buscarTodas();
        return ResponseEntity.ok(atividades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtividadeDTO> buscarPorId(@PathVariable Long id) {
        AtividadeDTO atividade = atividadeService.buscarPorId(id);
        return ResponseEntity.ok(atividade);
    }

//    @PostMapping
//    public ResponseEntity<AtividadeDTO> criarAtividade(@RequestBody AtividadeDTO atividadeDTO) {
//        AtividadeDTO novaAtividade = atividadeService.criarAtividade(atividadeDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).body(novaAtividade);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<AtividadeDTO> atualizarAtividade(@PathVariable Long id, @RequestBody AtividadeDTO atividadeDTO) {
//        AtividadeDTO atividadeAtualizada = atividadeService.atualizarAtividade(id, atividadeDTO);
//        return ResponseEntity.ok(atividadeAtualizada);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> excluirAtividade(@PathVariable Long id) {
//        atividadeService.excluirAtividade(id);
//        return ResponseEntity.noContent().build();
//    }
}
