package br.com.cursum.model;

import br.com.cursum.api.DadosAula;
import br.com.cursum.dto.AtividadeDTO;
import br.com.cursum.dto.AulaDTO;
import jakarta.persistence.*;

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
    @OneToMany(mappedBy = "aula", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Atividade> atividades = new ArrayList<>();
    //private String idApi;

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

    public List<AtividadeDTO> toAtividadeDTOList() {
        return this.atividades.stream()
                .map(atividade -> new AtividadeDTO(
                        atividade.getId(),
                        atividade.getNumSequencia(),
                        atividade.getTitulo(),
                        atividade.getTipo().getDescricao(),
                        atividade.getAula().getId()))
                .collect(Collectors.toList());
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
