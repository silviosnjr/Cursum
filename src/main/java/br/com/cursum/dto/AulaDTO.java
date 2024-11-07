package br.com.cursum.dto;

import br.com.cursum.api.DadosAula;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AulaDTO {
    private Long id;
    private String titulo;
    private Integer numSequencia;
    private String duracao;
    private Long cursoId;
    private List<AtividadeDTO> atividades;

    // Construtor
    public AulaDTO(Long id, String titulo, Integer numSequencia, String duracao, Long cursoId, List<AtividadeDTO>  atividades) {
            this.id = id;
        this.titulo = titulo;
        this.numSequencia = numSequencia;
        this.duracao = duracao;
        this.cursoId = cursoId;
        this.atividades = (atividades != null) ? atividades : new ArrayList<>();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Integer getNumSequencia() { return numSequencia; }
    public void setNumSequencia(Integer numSequencia) { this.numSequencia = numSequencia; }

    public String getDuracao() { return duracao; }
    public void setDuracao(String duracao) { this.duracao = duracao; }

    public Long getCursoId() { return cursoId; }
    public void setCursoId(Long cursoId) { this.cursoId = cursoId; }

    public List<AtividadeDTO> getAtividades() {
        return atividades;
    }

    public void setAtividades(List<AtividadeDTO> atividades) {
        this.atividades = atividades;
    }

    @Override
    public String toString() {
        return """
            %s - %s
            Duração: %s
            ******* ATIVIDADES DA AULA *************
            %s
            ****************************************
            """.formatted(
                this.numSequencia != null ? this.numSequencia : "N/A",
                this.titulo != null ? this.titulo : "Sem título",
                this.duracao != null ? this.duracao : "Indefinida",
                this.atividades != null ?
                        this.atividades.stream()
                                .map(a -> String.format("- %s) %s: %s",
                                        a.getNumSequencia() != null ? a.getNumSequencia() : "N/A",
                                        a.getTipo() != null ? a.getTipo() : "Sem tipo",
                                        a.getTitulo() != null ? a.getTitulo() : "Sem título"))
                                .collect(Collectors.joining("\n"))
                        : "Nenhuma atividade");
    }

}