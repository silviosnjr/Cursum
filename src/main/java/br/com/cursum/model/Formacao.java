package br.com.cursum.model;

import br.com.cursum.dto.AulaDTO;
import br.com.cursum.dto.CursoDTO;
import jakarta.persistence.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "formacoes")
public class Formacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @ManyToMany(cascade = { CascadeType.REMOVE }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "formacao_curso",
            joinColumns = { @JoinColumn(name = "formacao_id") },
            inverseJoinColumns = { @JoinColumn(name = "curso_id") }
    )
    private List<Curso> cursos = new ArrayList<>();

    public Formacao() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public List<CursoDTO> toCursoDTOList() {
        DateTimeFormatter dataFt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.cursos.stream()
                .map(curso -> new CursoDTO(
                        curso.getId(),
                        curso.getNome(),
                        curso.getDataCriacao() != null ? curso.getDataCriacao().format(dataFt) : "Data indisponível",
                        curso.getDuracao(),
                        curso.getIcone(),
                        curso.getAvaliacao(),
                        curso.getEscola().toString(),
                        curso.toHabilidadeDTOList(),
                        curso.toInstrutorDTOList(),
                        curso.toAulaDTOList()))
                .collect(Collectors.toList());
    }
}
