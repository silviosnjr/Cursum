package br.com.cursum.dto;

public class HabilidadeDTO {
    private Long id;
    private String descricao;
    private Long cursoId;

    // Construtor
    public HabilidadeDTO(Long id, String descricao, Long cursoId) {
        this.id = id;
        this.descricao = descricao;
        this.cursoId = cursoId;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }
}
