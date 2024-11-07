package br.com.cursum.controller;

import br.com.cursum.dto.AtividadeDTO;
import br.com.cursum.service.AtividadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atividades")
public class AtividadeController {

    @Autowired
    private AtividadeService atividadeService;

    /**
     * Endpoint para buscar todas as atividades.
     * @return Lista de AtividadeDTO.
     */
    @GetMapping
    public ResponseEntity<List<AtividadeDTO>> buscarTodas() {
        List<AtividadeDTO> atividades = atividadeService.buscarTodas();
        return ResponseEntity.ok(atividades);
    }

    /**
     * Endpoint para buscar uma atividade por ID.
     * @param id da atividade.
     * @return AtividadeDTO da atividade encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AtividadeDTO> buscarPorId(@PathVariable Long id) {
        AtividadeDTO atividade = atividadeService.buscarPorId(id);
        return ResponseEntity.ok(atividade);
    }

    /**
     * Endpoint para criar uma nova atividade.
     * @param atividadeDTO contendo os dados da atividade a ser criada.
     * @return AtividadeDTO da atividade criada.
     */
    @PostMapping
    public ResponseEntity<AtividadeDTO> criarAtividade(@RequestBody AtividadeDTO atividadeDTO) {
        AtividadeDTO novaAtividade = atividadeService.criarAtividade(atividadeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAtividade);
    }

    /**
     * Endpoint para atualizar uma atividade existente.
     * @param id da atividade a ser atualizada.
     * @param atividadeDTO contendo os novos dados da atividade.
     * @return AtividadeDTO da atividade atualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AtividadeDTO> atualizarAtividade(@PathVariable Long id, @RequestBody AtividadeDTO atividadeDTO) {
        AtividadeDTO atividadeAtualizada = atividadeService.atualizarAtividade(id, atividadeDTO);
        return ResponseEntity.ok(atividadeAtualizada);
    }

    /**
     * Endpoint para excluir uma atividade por ID.
     * @param id da atividade a ser excluída.
     * @return Resposta sem conteúdo.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAtividade(@PathVariable Long id) {
        atividadeService.excluirAtividade(id);
        return ResponseEntity.noContent().build();
    }
}
