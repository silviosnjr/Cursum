package br.com.cursum.service;

import br.com.cursum.dto.HabilidadeDTO;
import br.com.cursum.model.Curso;
import br.com.cursum.model.Habilidade;
import br.com.cursum.repository.CursoRepository;
import br.com.cursum.repository.HabilidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HabilidadeService {

    @Autowired
    private HabilidadeRepository habilidadeRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Transactional
    public List<HabilidadeDTO> buscarTodas() {
        return habilidadeRepository.findAll().stream()
                .map(habilidade -> new HabilidadeDTO(
                        habilidade.getId(),
                        habilidade.getDescricao(),
                        habilidade.getCurso().getId()))
                .collect(Collectors.toList());
    }

    @Transactional
    public HabilidadeDTO buscarPorId(Long id) {
        Habilidade habilidade = habilidadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Habilidade não encontrada"));

        return new HabilidadeDTO(
                habilidade.getId(),
                habilidade.getDescricao(),
                habilidade.getCurso().getId()
        );
    }

    public HabilidadeDTO criarHabilidade(HabilidadeDTO habilidadeDTO) {
        Curso curso = cursoRepository.findById(habilidadeDTO.getCursoId())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado para atribuir habilidade"));

        Habilidade habilidade = new Habilidade(null, curso, habilidadeDTO.getDescricao());
        Habilidade habilidadeSalva = habilidadeRepository.save(habilidade);

        return new HabilidadeDTO(
                habilidadeSalva.getId(),
                habilidadeSalva.getDescricao(),
                habilidadeSalva.getCurso().getId()
        );
    }

    public HabilidadeDTO atualizarHabilidade(Long id, HabilidadeDTO habilidadeDTO) {
        Habilidade habilidadeExistente = habilidadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Habilidade não encontrada para atualização"));

        Curso curso = cursoRepository.findById(habilidadeDTO.getCursoId())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado para atribuir habilidade"));

        habilidadeExistente.setDescricao(habilidadeDTO.getDescricao());
        habilidadeExistente.setCurso(curso);

        Habilidade habilidadeAtualizada = habilidadeRepository.save(habilidadeExistente);

        return new HabilidadeDTO(
                habilidadeAtualizada.getId(),
                habilidadeAtualizada.getDescricao(),
                habilidadeAtualizada.getCurso().getId()
        );
    }

    public void excluirHabilidade(Long id) {
        if (!habilidadeRepository.existsById(id)) {
            throw new RuntimeException("Habilidade não encontrada para exclusão");
        }
        habilidadeRepository.deleteById(id);
    }
}
