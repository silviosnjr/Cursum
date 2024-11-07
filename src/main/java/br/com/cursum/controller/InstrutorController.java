package br.com.cursum.controller;

import br.com.cursum.dto.InstrutorDTO;
import br.com.cursum.service.InstrutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instrutores")
public class InstrutorController {

    @Autowired
    private InstrutorService instrutorService;

    @GetMapping
    public ResponseEntity<List<InstrutorDTO>> buscarTodos() {
        List<InstrutorDTO> instrutores = instrutorService.buscarTodos();
        return ResponseEntity.ok(instrutores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstrutorDTO> buscarPorId(@PathVariable Long id) {
        InstrutorDTO instrutor = instrutorService.buscarPorId(id);
        return ResponseEntity.ok(instrutor);
    }

    @PostMapping
    public ResponseEntity<InstrutorDTO> criarInstrutor(@RequestBody InstrutorDTO instrutorDTO) {
        InstrutorDTO novoInstrutor = instrutorService.criarInstrutor(instrutorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoInstrutor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstrutorDTO> atualizarInstrutor(@PathVariable Long id, @RequestBody InstrutorDTO instrutorDTO) {
        InstrutorDTO instrutorAtualizado = instrutorService.atualizarInstrutor(id, instrutorDTO);
        return ResponseEntity.ok(instrutorAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirInstrutor(@PathVariable Long id) {
        instrutorService.excluirInstrutor(id);
        return ResponseEntity.noContent().build();
    }
}
