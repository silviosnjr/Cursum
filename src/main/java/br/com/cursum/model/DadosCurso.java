package br.com.cursum.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosCurso (
        @JsonAlias("Nome") String nome,
        @JsonAlias("Criacao") String dataCriacao,
        @JsonAlias("duracao") String duracao,
        @JsonAlias("Icone") String icone,
        @JsonAlias("Escola") String escola,
        @JsonAlias("Habilidades") String habilidades,
        @JsonAlias("Instrutor") String instrutores,
        @JsonAlias("Avaliacao")  String avaliacao,
        @JsonAlias("id") String idApi) {

    @Override
    public String toString() {
        return """
                ====== DADOS DO CURSO ==================
                Nome do curso: %s
                Data criação: %s
                Carga horária: %s
                Escola: %s
                Habilidades: %s
                Instrutores: %s
                Avaliação: %s
                =======================================
                """.formatted(this.nome, this.dataCriacao, this.duracao, this.escola, this.habilidades, this.instrutores, this.avaliacao);
    }
}


