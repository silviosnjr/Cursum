package br.com.cursum.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "instrutores")
public class Instrutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private Integer idApi;

    @ManyToMany(mappedBy = "instrutores")
    private List<Curso> cursos = new ArrayList<>();

    public Instrutor(DadosInstrutor dadosInstrutor){
        this.idApi = dadosInstrutor.idApi();
        this.nome = dadosInstrutor.nome();
        this.descricao = dadosInstrutor.descricao();
    }

    public Instrutor(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public Instrutor(){}

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getIdApi() {
        return idApi;
    }

    public void setIdApi(Integer idApi) {
        this.idApi = idApi;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    @Override
    public String toString() {
        return "Instrutor{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", idApi=" + idApi +
                '}';
    }
}



