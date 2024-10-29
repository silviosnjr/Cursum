package br.com.cursum.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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
}
