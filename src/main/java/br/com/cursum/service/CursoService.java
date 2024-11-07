package br.com.cursum.service;

import br.com.cursum.dto.CursoDTO;
import br.com.cursum.dto.HabilidadeDTO;
import br.com.cursum.dto.InstrutorDTO;
import br.com.cursum.model.Curso;
import br.com.cursum.model.Escola;
import br.com.cursum.model.Habilidade;
import br.com.cursum.model.Instrutor;
import br.com.cursum.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;
    private Curso curso = new Curso();

    @Transactional
    public List<CursoDTO> buscarTodos() {
        return cursoRepository.findAll().stream()
                .map(curso -> new CursoDTO(
                        curso.getId(),
                        curso.getNome(),
                        curso.getDataCriacao().toString(),
                        curso.getDuracao(),
                        curso.getIcone(),
                        curso.getAvaliacao(),
                        curso.getEscola().getNome(),
                        curso.toHabilidadeDTOList(),
                        curso.toInstrutorDTOList(),
                        curso.toAulaDTOList()

                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public CursoDTO buscarPorId(Long id) {
        this.curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        return new CursoDTO(
                this.curso.getId(),
                this.curso.getNome(),
                this.curso.getDataCriacao().toString(),
                this.curso.getDuracao(),
                this.curso.getIcone(),
                this.curso.getAvaliacao(),
                this.curso.getEscola().getNome(),
                this.curso.toHabilidadeDTOList(),
                this.curso.toInstrutorDTOList(),
                this.curso.toAulaDTOList()
        );
    }

    @Override
    public String toString() {
        return this.curso.toString();
    }

    public CursoDTO criarCurso(CursoDTO cursoDTO) {
        this.curso = new Curso();
        this.curso.setNome(cursoDTO.getNome());
        this.curso.setDuracao(cursoDTO.getDuracao());
        this.curso.setIcone(cursoDTO.getIcone());
        this.curso.setEscola(Escola.fromString(cursoDTO.getEscola().split(",")[0].trim()));

        try {
            this.curso.setDataCriacao(LocalDate.parse(cursoDTO.getDataCriacao()));
        } catch (DateTimeParseException ex) {
            this.curso.setDataCriacao(null);
        }

        try {
            this.curso.setAvaliacao(cursoDTO.getAvaliacao());
        } catch (NumberFormatException ex) {
            this.curso.setAvaliacao(0.0);
        }
        //OptionalDouble.of(Double.valueOf(dadosCurso.avaliacao())).orElse(0);

        if (cursoDTO.getHabilidades() != null && ! cursoDTO.getHabilidades().isEmpty()) {
            for (HabilidadeDTO habilidadeDTO : cursoDTO.getHabilidades()) {
                this.curso.getHabilidades().add(new Habilidade(null, curso, habilidadeDTO.getDescricao()));
            }
        }

        if (cursoDTO.getInstrutores() != null && ! cursoDTO.getInstrutores().isEmpty()) {
            for (InstrutorDTO intrutorDTO : cursoDTO.getInstrutores()) {
                this.curso.getInstrutores().add(new Instrutor(intrutorDTO.getNome(), intrutorDTO.getDescricao()));
            }
        }

        // Salva o curso e retorna o DTO do curso salvo
        Curso cursoSalvo = cursoRepository.save(curso);

        return new CursoDTO(
                cursoSalvo.getId(),
                cursoSalvo.getNome(),
                cursoSalvo.getDataCriacao().toString(),
                cursoSalvo.getDuracao(),
                cursoSalvo.getIcone(),
                cursoSalvo.getAvaliacao(),
                cursoSalvo.getEscola().getNome(),
                cursoSalvo.toHabilidadeDTOList(),
                cursoSalvo.toInstrutorDTOList(),
                cursoSalvo.toAulaDTOList()
        );
    }

    public CursoDTO atualizarCurso(Long id, CursoDTO cursoDTO) {
        Curso cursoExistente = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado para atualização"));

        cursoExistente.setNome(cursoDTO.getNome());
        cursoExistente.setDuracao(cursoDTO.getDuracao());
        cursoExistente.setIcone(cursoDTO.getIcone());
        cursoExistente.setAvaliacao(cursoDTO.getAvaliacao());
        cursoExistente.setEscola(Escola.fromString(cursoDTO.getEscola()));

        // Salva as alterações e retorna o DTO do curso atualizado
        Curso cursoAtualizado = cursoRepository.save(cursoExistente);

        return new CursoDTO(
                cursoAtualizado.getId(),
                cursoAtualizado.getNome(),
                cursoAtualizado.getDataCriacao().toString(),
                cursoAtualizado.getDuracao(),
                cursoAtualizado.getIcone(),
                cursoAtualizado.getAvaliacao(),
                cursoAtualizado.getEscola().getNome(),
                cursoAtualizado.toHabilidadeDTOList(),
                cursoAtualizado.toInstrutorDTOList(),
                cursoAtualizado.toAulaDTOList()
        );
    }

    public void excluirCurso(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new RuntimeException("Curso não encontrado para exclusão");
        }
        cursoRepository.deleteById(id);
    }
}