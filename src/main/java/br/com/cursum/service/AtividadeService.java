package br.com.cursum.service;

import br.com.cursum.dto.AtividadeDTO;
import br.com.cursum.model.Atividade;
import br.com.cursum.model.Aula;
import br.com.cursum.model.AtividadeTipo;
import br.com.cursum.repository.AtividadeRepository;
import br.com.cursum.repository.AulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AtividadeService {

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private AulaRepository aulaRepository;

    @Transactional
    public List<AtividadeDTO> buscarTodas() {
        return atividadeRepository.findAll().stream()
                .map(atividade -> new AtividadeDTO(
                        atividade.getId(),
                        atividade.getNumSequencia(),
                        atividade.getTitulo(),
                        atividade.getTipo().getDescricao(),
                        atividade.getAula().getId()))
                .collect(Collectors.toList());
    }

    @Transactional
    public AtividadeDTO buscarPorId(Long id) {
        Atividade atividade = atividadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atividade não encontrada"));

        return new AtividadeDTO(
                atividade.getId(),
                atividade.getNumSequencia(),
                atividade.getTitulo(),
                atividade.getTipo().getDescricao(),
                atividade.getAula().getId()
        );
    }

    public AtividadeDTO criarAtividade(AtividadeDTO atividadeDTO) {
        Aula aula = aulaRepository.findById(atividadeDTO.getAulaId())
                .orElseThrow(() -> new RuntimeException("Aula não encontrada para associar atividade"));

        Atividade atividade = new Atividade();
        atividade.setNumSequencia(atividadeDTO.getNumSequencia());
        atividade.setTitulo(atividadeDTO.getTitulo());
        atividade.setTipo(AtividadeTipo.fromString(atividadeDTO.getTipo()));
        atividade.setAula(aula);

        Atividade atividadeSalva = atividadeRepository.save(atividade);

        return new AtividadeDTO(
                atividadeSalva.getId(),
                atividadeSalva.getNumSequencia(),
                atividadeSalva.getTitulo(),
                atividadeSalva.getTipo().getDescricao(),
                atividadeSalva.getAula().getId()
        );
    }

    public AtividadeDTO atualizarAtividade(Long id, AtividadeDTO atividadeDTO) {
        Atividade atividadeExistente = atividadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atividade não encontrada para atualização"));

        Aula aula = aulaRepository.findById(atividadeDTO.getAulaId())
                .orElseThrow(() -> new RuntimeException("Aula não encontrada para associar atividade"));

        atividadeExistente.setNumSequencia(atividadeDTO.getNumSequencia());
        atividadeExistente.setTitulo(atividadeDTO.getTitulo());
        atividadeExistente.setTipo(AtividadeTipo.fromString(atividadeDTO.getTipo()));
        atividadeExistente.setAula(aula);

        Atividade atividadeAtualizada = atividadeRepository.save(atividadeExistente);

        return new AtividadeDTO(
                atividadeAtualizada.getId(),
                atividadeAtualizada.getNumSequencia(),
                atividadeAtualizada.getTitulo(),
                atividadeAtualizada.getTipo().getDescricao(),
                atividadeAtualizada.getAula().getId()
        );
    }

    public void excluirAtividade(Long id) {
        if (!atividadeRepository.existsById(id)) {
            throw new RuntimeException("Atividade não encontrada para exclusão");
        }
        atividadeRepository.deleteById(id);
    }
}
