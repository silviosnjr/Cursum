package br.com.cursum.controller;

import br.com.cursum.dto.AulaDTO;
import br.com.cursum.service.AulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aulas")
public class AulaController {

    @Autowired
    private AulaService aulaService;

    @GetMapping
    public ResponseEntity<List<AulaDTO>> buscarTodas() {
        List<AulaDTO> aulas = aulaService.buscarTodas();
        return ResponseEntity.ok(aulas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AulaDTO> buscarPorId(@PathVariable Long id) {
        AulaDTO aula = aulaService.buscarPorId(id);
        return ResponseEntity.ok(aula);
    }

    @PostMapping
    public ResponseEntity<AulaDTO> criarAula(@RequestBody AulaDTO aulaDTO) {
        AulaDTO novaAula = aulaService.criarAula(aulaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAula);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AulaDTO> atualizarAula(@PathVariable Long id, @RequestBody AulaDTO aulaDTO) {
        AulaDTO aulaAtualizada = aulaService.atualizarAula(id, aulaDTO);
        return ResponseEntity.ok(aulaAtualizada);
    }

     @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAula(@PathVariable Long id) {
        aulaService.excluirAula(id);
        return ResponseEntity.noContent().build();
    }
}
