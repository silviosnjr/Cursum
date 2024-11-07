package br.com.cursum.service;

import br.com.cursum.dto.AulaDTO;
import br.com.cursum.model.Aula;
import br.com.cursum.model.Curso;
import br.com.cursum.repository.AulaRepository;
import br.com.cursum.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AulaService {

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Transactional
    public List<AulaDTO> buscarTodas() {
        return aulaRepository.findAll().stream()
                .map(aula -> new AulaDTO(
                        aula.getId(),
                        aula.getTitulo(),
                        aula.getNumSequencia(),
                        aula.getDuracao(),
                        aula.getCurso().getId(),
                        aula.toAtividadeDTOList()))
                .collect(Collectors.toList());
    }

    @Transactional
    public AulaDTO buscarPorId(Long id) {
        Aula aula = aulaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aula não encontrada"));

        return new AulaDTO(
                aula.getId(),
                aula.getTitulo(),
                aula.getNumSequencia(),
                aula.getDuracao(),
                aula.getCurso().getId(),
                aula.toAtividadeDTOList()
        );
    }

    public AulaDTO criarAula(AulaDTO aulaDTO) {
        Curso curso = cursoRepository.findById(aulaDTO.getCursoId())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado para associar aula"));

        Aula aula = new Aula();
        aula.setTitulo(aulaDTO.getTitulo());
        aula.setNumSequencia(aulaDTO.getNumSequencia());
        aula.setDuracao(aulaDTO.getDuracao());
        aula.setCurso(curso);

        Aula aulaSalva = aulaRepository.save(aula);

        return new AulaDTO(
                aulaSalva.getId(),
                aulaSalva.getTitulo(),
                aulaSalva.getNumSequencia(),
                aulaSalva.getDuracao(),
                aulaSalva.getCurso().getId(),
                aulaSalva.toAtividadeDTOList()
        );
    }

    public AulaDTO atualizarAula(Long id, AulaDTO aulaDTO) {
        Aula aulaExistente = aulaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aula não encontrada para atualização"));

        Curso curso = cursoRepository.findById(aulaDTO.getCursoId())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado para associar aula"));

        aulaExistente.setTitulo(aulaDTO.getTitulo());
        aulaExistente.setNumSequencia(aulaDTO.getNumSequencia());
        aulaExistente.setDuracao(aulaDTO.getDuracao());
        aulaExistente.setCurso(curso);

        Aula aulaAtualizada = aulaRepository.save(aulaExistente);

        return new AulaDTO(
                aulaAtualizada.getId(),
                aulaAtualizada.getTitulo(),
                aulaAtualizada.getNumSequencia(),
                aulaAtualizada.getDuracao(),
                aulaAtualizada.getCurso().getId(),
                aulaAtualizada.toAtividadeDTOList()
        );
    }

    public void excluirAula(Long id) {
        if (!aulaRepository.existsById(id)) {
            throw new RuntimeException("Aula não encontrada para exclusão");
        }
        aulaRepository.deleteById(id);
    }
}
