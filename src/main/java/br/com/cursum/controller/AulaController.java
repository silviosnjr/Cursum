package br.com.cursum.controller;

import br.com.cursum.dto.AulaDTO;
import br.com.cursum.service.AulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aulas")
public class AulaController {

    @Autowired
    private AulaService aulaService;

    /**
     * Endpoint para buscar todas as aulas.
     * @return Lista de AulaDTO.
     */
    @GetMapping
    public ResponseEntity<List<AulaDTO>> buscarTodas() {
        List<AulaDTO> aulas = aulaService.buscarTodas();
        return ResponseEntity.ok(aulas);
    }

    /**
     * Endpoint para buscar uma aula por ID.
     * @param id da aula.
     * @return AulaDTO da aula encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AulaDTO> buscarPorId(@PathVariable Long id) {
        AulaDTO aula = aulaService.buscarPorId(id);
        return ResponseEntity.ok(aula);
    }

    /**
     * Endpoint para criar uma nova aula.
     * @param aulaDTO contendo os dados da aula a ser criada.
     * @return AulaDTO da aula criada.
     */
    @PostMapping
    public ResponseEntity<AulaDTO> criarAula(@RequestBody AulaDTO aulaDTO) {
        AulaDTO novaAula = aulaService.criarAula(aulaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAula);
    }

    /**
     * Endpoint para atualizar uma aula existente.
     * @param id da aula a ser atualizada.
     * @param aulaDTO contendo os novos dados da aula.
     * @return AulaDTO da aula atualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AulaDTO> atualizarAula(@PathVariable Long id, @RequestBody AulaDTO aulaDTO) {
        AulaDTO aulaAtualizada = aulaService.atualizarAula(id, aulaDTO);
        return ResponseEntity.ok(aulaAtualizada);
    }

    /**
     * Endpoint para excluir uma aula por ID.
     * @param id da aula a ser excluída.
     * @return Resposta sem conteúdo.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAula(@PathVariable Long id) {
        aulaService.excluirAula(id);
        return ResponseEntity.noContent().build();
    }
}
