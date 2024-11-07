package br.com.cursum.dto;

import java.util.ArrayList;
import java.util.List;

public class FormacaoDTO {
    private Long id;
    private String nome;
    private List<CursoDTO> cursos = new ArrayList<>();

    // Construtor
    public FormacaoDTO(Long id, String nome, List<CursoDTO> cursos) {
        this.id = id;
        this.nome = nome;
        this.cursos = cursos;
    }

    public FormacaoDTO() {

    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public List<CursoDTO> getCursos() {
        return cursos;
    }

    public void setCursos(List<CursoDTO> cursos) {
        this.cursos = cursos;
    }
}
