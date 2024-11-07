package br.com.cursum.controller;

import br.com.cursum.dto.HabilidadeDTO;
import br.com.cursum.service.HabilidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habilidades")
public class HabilidadeController {

    @Autowired
    private HabilidadeService habilidadeService;

    @GetMapping
    public ResponseEntity<List<HabilidadeDTO>> buscarTodas() {
        List<HabilidadeDTO> habilidades = habilidadeService.buscarTodas();
        return ResponseEntity.ok(habilidades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabilidadeDTO> buscarPorId(@PathVariable Long id) {
        HabilidadeDTO habilidade = habilidadeService.buscarPorId(id);
        return ResponseEntity.ok(habilidade);
    }

//    @PostMapping
//    public ResponseEntity<HabilidadeDTO> criarHabilidade(@RequestBody HabilidadeDTO habilidadeDTO) {
//        HabilidadeDTO novaHabilidade = habilidadeService.criarHabilidade(habilidadeDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).body(novaHabilidade);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<HabilidadeDTO> atualizarHabilidade(@PathVariable Long id, @RequestBody HabilidadeDTO habilidadeDTO) {
//        HabilidadeDTO habilidadeAtualizada = habilidadeService.atualizarHabilidade(id, habilidadeDTO);
//        return ResponseEntity.ok(habilidadeAtualizada);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> excluirHabilidade(@PathVariable Long id) {
//        habilidadeService.excluirHabilidade(id);
//        return ResponseEntity.noContent().build();
//    }
}
