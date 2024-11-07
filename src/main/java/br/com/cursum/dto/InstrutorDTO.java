package br.com.cursum.dto;

import java.util.List;

public class InstrutorDTO {
    private Long id;
    private String nome;
    private String descricao;
    private List<Long> cursoIds;

    // Construtor
    public InstrutorDTO(Long id, String nome, String descricao, List<Long> cursoIds) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.cursoIds = cursoIds;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public List<Long> getCursoIds() { return cursoIds; }
    public void setCursoIds(List<Long> cursoIds) { this.cursoIds = cursoIds; }
}