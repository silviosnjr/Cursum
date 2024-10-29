package br.com.cursum.model;

import jakarta.persistence.*;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Entity
@Table(name = "atividades")
public class Atividade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer numSequencia;
    private String titulo;
    private AtividadeTipo tipo;
    @ManyToOne(fetch = FetchType.LAZY)
    private Aula aula;
    private String idApi;

    public Atividade(DadosAtividade dadosAtividade) {
        this.numSequencia = Integer.valueOf(dadosAtividade.numSequencia());
        this.titulo = dadosAtividade.titulo();
        this.tipo = AtividadeTipo.fromString(dadosAtividade.tipo());
        this.idApi = dadosAtividade.idApi();
    }

    public Atividade(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumSequencia() {
        return numSequencia;
    }

    public void setNumSequencia(Integer numSequencia) {
        this.numSequencia = numSequencia;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public AtividadeTipo getTipo() {
        return tipo;
    }

    public void setTipo(AtividadeTipo tipo) {
        this.tipo = tipo;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
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
                ******* ATIVIDADE DA AULA **************
                ID: %d
                Sequência: %s
                Título: %s
                Tipo: %s
                ****************************************
                """.formatted(
                this.id,
                this.numSequencia,
                this.titulo,
                this.tipo);
    }
}
