package br.com.cursum.controller;

import br.com.cursum.dto.HabilidadeDTO;
import br.com.cursum.service.HabilidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habilidades")
public class HabilidadeController {

    @Autowired
    private HabilidadeService habilidadeService;

    /**
     * Endpoint para buscar todas as habilidades.
     * @return Lista de HabilidadeDTO.
     */
    @GetMapping
    public ResponseEntity<List<HabilidadeDTO>> buscarTodas() {
        List<HabilidadeDTO> habilidades = habilidadeService.buscarTodas();
        return ResponseEntity.ok(habilidades);
    }

    /**
     * Endpoint para buscar uma habilidade por ID.
     * @param id da habilidade.
     * @return HabilidadeDTO da habilidade encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HabilidadeDTO> buscarPorId(@PathVariable Long id) {
        HabilidadeDTO habilidade = habilidadeService.buscarPorId(id);
        return ResponseEntity.ok(habilidade);
    }

    /**
     * Endpoint para criar uma nova habilidade.
     * @param habilidadeDTO contendo os dados da habilidade a ser criada.
     * @return HabilidadeDTO da habilidade criada.
     */
    @PostMapping
    public ResponseEntity<HabilidadeDTO> criarHabilidade(@RequestBody HabilidadeDTO habilidadeDTO) {
        HabilidadeDTO novaHabilidade = habilidadeService.criarHabilidade(habilidadeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaHabilidade);
    }

    /**
     * Endpoint para atualizar uma habilidade existente.
     * @param id da habilidade a ser atualizada.
     * @param habilidadeDTO contendo os novos dados da habilidade.
     * @return HabilidadeDTO da habilidade atualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HabilidadeDTO> atualizarHabilidade(@PathVariable Long id, @RequestBody HabilidadeDTO habilidadeDTO) {
        HabilidadeDTO habilidadeAtualizada = habilidadeService.atualizarHabilidade(id, habilidadeDTO);
        return ResponseEntity.ok(habilidadeAtualizada);
    }

    /**
     * Endpoint para excluir uma habilidade por ID.
     * @param id da habilidade a ser excluída.
     * @return Resposta sem conteúdo.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirHabilidade(@PathVariable Long id) {
        habilidadeService.excluirHabilidade(id);
        return ResponseEntity.noContent().build();
    }
}
