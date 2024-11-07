package br.com.cursum.controller;

import br.com.cursum.dto.FormacaoDTO;
import br.com.cursum.service.FormacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/formacoes")
public class FormacaoController {

    @Autowired
    private FormacaoService formacaoService;

    /**
     * Endpoint para buscar todas as formações.
     * @return Lista de FormacaoDTO.
     */
    @GetMapping
    public ResponseEntity<List<FormacaoDTO>> buscarTodas() {
        List<FormacaoDTO> formacoes = formacaoService.buscarTodas();
        return ResponseEntity.ok(formacoes);
    }

    /**
     * Endpoint para buscar uma formação por ID.
     * @param id da formação.
     * @return FormacaoDTO da formação encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FormacaoDTO> buscarPorId(@PathVariable Long id) {
        FormacaoDTO formacao = formacaoService.buscarPorId(id);
        return ResponseEntity.ok(formacao);
    }

    /**
     * Endpoint para criar uma nova formação.
     * @param formacaoDTO contendo os dados da formação a ser criada.
     * @return FormacaoDTO da formação criada.
     */
    @PostMapping
    public ResponseEntity<FormacaoDTO> criarFormacao(@RequestBody FormacaoDTO formacaoDTO) {
        FormacaoDTO novaFormacao = formacaoService.criarFormacao(formacaoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaFormacao);
    }

    /**
     * Endpoint para atualizar uma formação existente.
     * @param id da formação a ser atualizada.
     * @param formacaoDTO contendo os novos dados da formação.
     * @return FormacaoDTO da formação atualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FormacaoDTO> atualizarFormacao(@PathVariable Long id, @RequestBody FormacaoDTO formacaoDTO) {
        FormacaoDTO formacaoAtualizada = formacaoService.atualizarFormacao(id, formacaoDTO);
        return ResponseEntity.ok(formacaoAtualizada);
    }

    /**
     * Endpoint para excluir uma formação por ID.
     * @param id da formação a ser excluída.
     * @return Resposta sem conteúdo.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirFormacao(@PathVariable Long id) {
        formacaoService.excluirFormacao(id);
        return ResponseEntity.noContent().build();
    }
}
