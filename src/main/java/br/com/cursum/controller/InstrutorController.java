package br.com.cursum.controller;

import br.com.cursum.dto.InstrutorDTO;
import br.com.cursum.service.InstrutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instrutores")
public class InstrutorController {

    @Autowired
    private InstrutorService instrutorService;

    /**
     * Endpoint para buscar todos os instrutores.
     * @return Lista de InstrutorDTO.
     */
    @GetMapping
    public ResponseEntity<List<InstrutorDTO>> buscarTodos() {
        List<InstrutorDTO> instrutores = instrutorService.buscarTodos();
        return ResponseEntity.ok(instrutores);
    }

    /**
     * Endpoint para buscar um instrutor por ID.
     * @param id do instrutor.
     * @return InstrutorDTO do instrutor encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InstrutorDTO> buscarPorId(@PathVariable Long id) {
        InstrutorDTO instrutor = instrutorService.buscarPorId(id);
        return ResponseEntity.ok(instrutor);
    }

    /**
     * Endpoint para criar um novo instrutor.
     * @param instrutorDTO contendo os dados do instrutor a ser criado.
     * @return InstrutorDTO do instrutor criado.
     */
    @PostMapping
    public ResponseEntity<InstrutorDTO> criarInstrutor(@RequestBody InstrutorDTO instrutorDTO) {
        InstrutorDTO novoInstrutor = instrutorService.criarInstrutor(instrutorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoInstrutor);
    }

    /**
     * Endpoint para atualizar um instrutor existente.
     * @param id do instrutor a ser atualizado.
     * @param instrutorDTO contendo os novos dados do instrutor.
     * @return InstrutorDTO do instrutor atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InstrutorDTO> atualizarInstrutor(@PathVariable Long id, @RequestBody InstrutorDTO instrutorDTO) {
        InstrutorDTO instrutorAtualizado = instrutorService.atualizarInstrutor(id, instrutorDTO);
        return ResponseEntity.ok(instrutorAtualizado);
    }

    /**
     * Endpoint para excluir um instrutor por ID.
     * @param id do instrutor a ser excluído.
     * @return Resposta sem conteúdo.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirInstrutor(@PathVariable Long id) {
        instrutorService.excluirInstrutor(id);
        return ResponseEntity.noContent().build();
    }
}
