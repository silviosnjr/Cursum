package br.com.cursum.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

import jakarta.persistence.*;

@Entity
@Table(name = "cursos")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private LocalDate dataCriacao;
    private String duracao;
    private String icone;
    @Enumerated(EnumType.STRING)
    private Escola escola;
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Habilidade> habilidades = new ArrayList<>();
    private Double avaliacao;
    private String idApi;
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Aula> aulas = new ArrayList<>();

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "instrutor_curso",
            joinColumns = { @JoinColumn(name = "curso_id") },
            inverseJoinColumns = { @JoinColumn(name = "instrutor_id") }
    )
    private List<Instrutor> instrutores = new ArrayList<>();

    @ManyToMany(mappedBy = "cursos", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<Formacao> formacoes = new ArrayList<>();

    @Deprecated
    public Curso(){};

    public Curso(DadosCurso dadosCurso) {
        this.idApi = dadosCurso.idApi();
        this.nome = dadosCurso.nome();
        this.duracao = dadosCurso.duracao();
        this.icone = dadosCurso.icone();
        this.escola = Escola.fromString(dadosCurso.escola().split(",")[0].trim());

        this.habilidades = new ArrayList<>();
        if (dadosCurso.habilidades() != null && ! dadosCurso.habilidades().isEmpty()) {
            String[] habilidadesArray = dadosCurso.habilidades().split(",");
            for (String habilidade : habilidadesArray) {
                this.habilidades.add(new Habilidade(this, habilidade.trim()));
            }
        }
        /*this.habilidades = Arrays.asList(dadosCurso.habilidades().split(","))
                .stream()
                .map(String::trim)  // Remove espaços extras ao redor de cada habilidade
                .collect(Collectors.toList());*/
        try {
            this.dataCriacao = LocalDate.parse(dadosCurso.dataCriacao());
        } catch (DateTimeParseException ex) {
            this.dataCriacao = null;
        }

        try {
            this.avaliacao = Double.valueOf(dadosCurso.avaliacao());
        } catch (NumberFormatException ex) {
            this.avaliacao = 0.0;
        }
        //OptionalDouble.of(Double.valueOf(dadosCurso.avaliacao())).orElse(0);
    }

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

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public Escola getEscola() {
        return escola;
    }

    public void setEscola(Escola escola) {
        this.escola = escola;
    }

    public List<Habilidade> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(List<Habilidade> habilidades) {
        this.habilidades = habilidades;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getIdApi() {
        return idApi;
    }

    public void setIdApi(String idApi) {
        this.idApi = idApi;
    }

    public List<Instrutor> getInstrutores() {
        return instrutores;
    }

    public void setInstrutores(List<Instrutor> instrutores) {
        this.instrutores = instrutores;
    }

    public List<Aula> getAulas() {
        return aulas;
    }

    public void setAulas(List<Aula> aulas) {
        this.aulas = aulas;
    }

    public void resumo(){
        System.out.println("(ID: %d) %s".formatted(this.id, this.nome));
    }

    public List<Formacao> getFormacoes() {
        return formacoes;
    }

    public void setFormacoes(List<Formacao> formacoes) {
        this.formacoes = formacoes;
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
                =======================================
                """.formatted(
                this.id,
                this.nome,
                dataCriacao.format(dataFt),
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

