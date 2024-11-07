package br.com.cursum.service;

import br.com.cursum.dto.InstrutorDTO;
import br.com.cursum.model.Curso;
import br.com.cursum.model.Instrutor;
import br.com.cursum.repository.CursoRepository;
import br.com.cursum.repository.InstrutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InstrutorService {

    @Autowired
    private InstrutorRepository instrutorRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Transactional
    public List<InstrutorDTO> buscarTodos() {
        return instrutorRepository.findAll().stream()
                .map(instrutor -> new InstrutorDTO(
                        instrutor.getId(),
                        instrutor.getNome(),
                        instrutor.getDescricao(),
                        instrutor.getCursos().stream().map(Curso::getId).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Transactional
    public InstrutorDTO buscarPorId(Long id) {
        Instrutor instrutor = instrutorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Instrutor não encontrado"));

        return new InstrutorDTO(
                instrutor.getId(),
                instrutor.getNome(),
                instrutor.getDescricao(),
                instrutor.getCursos().stream().map(Curso::getId).collect(Collectors.toList())
        );
    }

    public InstrutorDTO criarInstrutor(InstrutorDTO instrutorDTO) {
        Instrutor instrutor = new Instrutor();
        instrutor.setNome(instrutorDTO.getNome());
        instrutor.setDescricao(instrutorDTO.getDescricao());
        instrutor.setCursos(buscarCursosPorIds(instrutorDTO.getCursoIds()));

        Instrutor instrutorSalvo = instrutorRepository.save(instrutor);

        return new InstrutorDTO(
                instrutorSalvo.getId(),
                instrutorSalvo.getNome(),
                instrutorSalvo.getDescricao(),
                instrutorSalvo.getCursos().stream().map(Curso::getId).collect(Collectors.toList())
        );
    }

    public InstrutorDTO atualizarInstrutor(Long id, InstrutorDTO instrutorDTO) {
        Instrutor instrutorExistente = instrutorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Instrutor não encontrado para atualização"));

        instrutorExistente.setNome(instrutorDTO.getNome());
        instrutorExistente.setDescricao(instrutorDTO.getDescricao());
        instrutorExistente.setCursos(buscarCursosPorIds(instrutorDTO.getCursoIds()));

        Instrutor instrutorAtualizado = instrutorRepository.save(instrutorExistente);

        return new InstrutorDTO(
                instrutorAtualizado.getId(),
                instrutorAtualizado.getNome(),
                instrutorAtualizado.getDescricao(),
                instrutorAtualizado.getCursos().stream().map(Curso::getId).collect(Collectors.toList())
        );
    }

    public void excluirInstrutor(Long id) {
        if (!instrutorRepository.existsById(id)) {
            throw new RuntimeException("Instrutor não encontrado para exclusão");
        }
        instrutorRepository.deleteById(id);
    }


    private List<Curso> buscarCursosPorIds(List<Long> cursoIds) {
        return cursoIds.stream()
                .map(id -> cursoRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Curso não encontrado com ID: " + id)))
                .collect(Collectors.toList());
    }
}
