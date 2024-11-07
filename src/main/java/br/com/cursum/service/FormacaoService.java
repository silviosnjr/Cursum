package br.com.cursum.service;

import br.com.cursum.dto.CursoDTO;
import br.com.cursum.dto.FormacaoDTO;
import br.com.cursum.dto.HabilidadeDTO;
import br.com.cursum.dto.InstrutorDTO;
import br.com.cursum.model.*;
import br.com.cursum.repository.CursoRepository;
import br.com.cursum.repository.FormacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FormacaoService {

    @Autowired
    private FormacaoRepository formacaoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Transactional
    public List<FormacaoDTO> buscarTodas() {
        return formacaoRepository.findAll().stream()
                .map(formacao -> new FormacaoDTO(
                        formacao.getId(),
                        formacao.getNome(),
                        formacao.toCursoDTOList()))
                .collect(Collectors.toList());
    }

    @Transactional
    public FormacaoDTO buscarPorId(Long id) {
        Formacao formacao = formacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formação não encontrada"));

        return new FormacaoDTO(
                formacao.getId(),
                formacao.getNome(),
                formacao.toCursoDTOList()
        );
    }

    public FormacaoDTO criarFormacao(FormacaoDTO formacaoDTO) {
        Formacao formacao = new Formacao();
        formacao.setNome(formacaoDTO.getNome());

        if (formacaoDTO.getCursos() != null && ! formacaoDTO.getCursos().isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (CursoDTO cursoDTO : formacaoDTO.getCursos()) {
                Curso curso = new Curso();
                curso.setId(cursoDTO.getId());
                curso.setNome(cursoDTO.getNome());
                try{
                    curso.setDataCriacao(LocalDate.parse(cursoDTO.getDataCriacao(), formatter));
                } catch (DateTimeParseException ex) {
                    curso.setDataCriacao(null);
                }
                curso.setDuracao(cursoDTO.getDuracao());
                curso.setIcone(cursoDTO.getIcone());
                curso.setEscola(Escola.fromString(cursoDTO.getEscola().split(",")[0].trim()));
                if (cursoDTO.getHabilidades() != null && ! cursoDTO.getHabilidades().isEmpty()) {
                    for (HabilidadeDTO habilidadeDTO : cursoDTO.getHabilidades()) {
                        curso.getHabilidades().add(new Habilidade(null, curso, habilidadeDTO.getDescricao()));
                    }
                }
                try {
                    curso.setAvaliacao(cursoDTO.getAvaliacao());
                } catch (NumberFormatException ex) {
                    curso.setAvaliacao(0.0);
                }
                if (cursoDTO.getInstrutores() != null && ! cursoDTO.getInstrutores().isEmpty()) {
                    for (InstrutorDTO intrutorDTO : cursoDTO.getInstrutores()) {
                        curso.getInstrutores().add(new Instrutor(intrutorDTO.getNome(), intrutorDTO.getDescricao()));
                    }
                }

                formacao.getCursos().add(curso);
            }
        }

        Formacao formacaoSalva = formacaoRepository.save(formacao);

        return new FormacaoDTO(
                formacaoSalva.getId(),
                formacaoSalva.getNome(),
                formacao.toCursoDTOList()
        );
    }

    public FormacaoDTO atualizarFormacao(Long id, FormacaoDTO formacaoDTO) {
        Formacao formacaoExistente = formacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formação não encontrada para atualização"));

        formacaoExistente.setNome(formacaoDTO.getNome());

        if (formacaoDTO.getCursos() != null && ! formacaoDTO.getCursos().isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (CursoDTO cursoDTO : formacaoDTO.getCursos()) {
                Curso curso = new Curso();
                curso.setId(cursoDTO.getId());
                curso.setNome(cursoDTO.getNome());
                try{
                     curso.setDataCriacao(LocalDate.parse(cursoDTO.getDataCriacao(), formatter));
                } catch (DateTimeParseException ex) {
                    curso.setDataCriacao(null);
                }
                curso.setDuracao(cursoDTO.getDuracao());
                curso.setIcone(cursoDTO.getIcone());
                curso.setEscola(Escola.fromString(cursoDTO.getEscola().split(",")[0].trim()));
                if (cursoDTO.getHabilidades() != null && ! cursoDTO.getHabilidades().isEmpty()) {
                    for (HabilidadeDTO habilidadeDTO : cursoDTO.getHabilidades()) {
                        curso.getHabilidades().add(new Habilidade(null, curso, habilidadeDTO.getDescricao()));
                    }
                }
                try {
                    curso.setAvaliacao(cursoDTO.getAvaliacao());
                } catch (NumberFormatException ex) {
                    curso.setAvaliacao(0.0);
                }
                if (cursoDTO.getInstrutores() != null && ! cursoDTO.getInstrutores().isEmpty()) {
                    for (InstrutorDTO intrutorDTO : cursoDTO.getInstrutores()) {
                        curso.getInstrutores().add(new Instrutor(intrutorDTO.getNome(), intrutorDTO.getDescricao()));
                    }
                }

                formacaoExistente.getCursos().add(curso);
            }
        }

        Formacao formacaoAtualizada = formacaoRepository.save(formacaoExistente);

        return new FormacaoDTO(
                formacaoAtualizada.getId(),
                formacaoAtualizada.getNome(),
                formacaoAtualizada.toCursoDTOList()
        );
    }

    public void excluirFormacao(Long id) {
        if (!formacaoRepository.existsById(id)) {
            throw new RuntimeException("Formação não encontrada para exclusão");
        }
        formacaoRepository.deleteById(id);
    }


    private List<Curso> buscarCursosPorIds(List<Long> cursoIds) {
        return cursoIds.stream()
                .map(id -> cursoRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Curso não encontrado com ID: " + id)))
                .collect(Collectors.toList());
    }
}
