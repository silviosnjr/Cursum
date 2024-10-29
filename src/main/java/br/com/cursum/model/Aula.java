package br.com.cursum.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "aulas")
public class Aula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private Integer numSequencia;
    private String duracao;
    @ManyToOne(fetch = FetchType.LAZY)
    private Curso curso;
    @OneToMany(mappedBy = "aula", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Atividade> atividades = new ArrayList<>();
    private String idApi;

    public Aula(DadosAula dadosAula) {
        this.titulo = dadosAula.titulo();
        this.numSequencia = Integer.valueOf(dadosAula.numSequencia());
        this.duracao = dadosAula.duracao();
        this.idApi = dadosAula.idApi();
    }

    public Aula(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumSequencia() {
        return numSequencia;
    }

    public void setNumSequencia(Integer numSequencia) {
        this.numSequencia = numSequencia;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<Atividade> getAtividades() {
        return atividades;
    }

    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }

    public String getIdApi() {
        return idApi;
    }

    public void setIdApi(String idApi) {
        this.idApi = idApi;
    }

    @Override
    public String toString() {
        return """
                %s - %s (id: %d )
                Duração: %s
                %s
                """.formatted(
                this.numSequencia,
                this.titulo,
                this.id,
                this.duracao,
                this.atividades.stream()
                        .map(a -> String.format("- %s) %s: %s (%d)", a.getNumSequencia(), a.getTipo(), a.getTitulo(), a.getId()))
                        .collect(Collectors.joining("\n")));
    }
}
