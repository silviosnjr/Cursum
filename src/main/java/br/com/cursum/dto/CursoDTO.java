package br.com.cursum.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CursoDTO {
    private Long id;
    private String nome;
    private String dataCriacao;
    private String duracao;
    private String icone;
    private Double avaliacao;
    private String escola;
    private List<HabilidadeDTO> habilidades;
    private List<InstrutorDTO> instrutores;
    private List<AulaDTO> aulas;

    // Construtores, getters e setters

    public CursoDTO(Long id, String nome, String dataCriacao, String duracao, String icone, Double avaliacao, String escola, List<HabilidadeDTO> habilidades, List<InstrutorDTO> instrutores, List<AulaDTO> aulas) {
        this.id = id;
        this.nome = nome;
        this.dataCriacao = dataCriacao;
        this.duracao = duracao;
        this.icone = icone;
        this.avaliacao = avaliacao;
        this.escola = escola;
        this.habilidades = (habilidades != null) ? habilidades : new ArrayList<>();
        this.instrutores = (instrutores != null) ? instrutores : new ArrayList<>();
        this.aulas = (aulas != null) ? aulas : new ArrayList<>();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getDuracao() { return duracao; }
    public void setDuracao(String duracao) { this.duracao = duracao; }

    public String getIcone() { return icone; }
    public void setIcone(String icone) { this.icone = icone; }

    public Double getAvaliacao() { return avaliacao; }
    public void setAvaliacao(Double avaliacao) { this.avaliacao = avaliacao; }

    public String getEscola() { return escola; }
    public void setEscola(String escola) { this.escola = escola; }

    public List<HabilidadeDTO> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(List<HabilidadeDTO> habilidades) {
        this.habilidades = habilidades;
    }

    public List<InstrutorDTO> getInstrutores() {
        return instrutores;
    }

    public void setInstrutores(List<InstrutorDTO> instrutores) {
        this.instrutores = instrutores;
    }

    public List<AulaDTO> getAulas() {
        return aulas;
    }

    public void setAulas(List<AulaDTO> aulas) {
        this.aulas = aulas;
    }

    @Override
    public String toString() {
        DateTimeFormatter dataFt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return """
                ====== CURSO SALVO ==================
                ID: %d
                Nome do curso: %s
                Data criação: %s
                Carga horária: %s
                Escola: %s
                Habilidades: %s
                Instrutores: %s
                Avaliação: %s
                ------ AULAS DO CURSO ------------------
                %s
                ----------------------------------------
                =======================================
                """.formatted(
                this.id,
                this.nome,
                this.dataCriacao,
                this.duracao,
                this.escola,
                this.habilidades.stream()
                        .map(h -> String.format("%s (id %d)", h.getDescricao(), h.getId()))
                        .collect(Collectors.joining(" | ")),
                this.instrutores.stream()
                        .map(i -> String.format("%s (id %d)", i.getNome(), i.getId()))
                        .collect(Collectors.joining(" | ")),
                this.avaliacao,
                this.aulas.stream()
                        .map(a -> String.format(a.toString()))
                        .collect(Collectors.joining("\n"))
        );
    }
}
