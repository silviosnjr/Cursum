package br.com.cursum.model;

import jakarta.persistence.*;

@Entity
@Table(name = "habilidades")
public class Habilidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    @ManyToOne(fetch = FetchType.LAZY)
    private Curso curso;

    public Habilidade(){};

    public Habilidade(Curso curso, String descricao){
        this.descricao = descricao;
        this.curso = curso;
    }

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

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}
