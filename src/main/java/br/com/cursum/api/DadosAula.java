package br.com.cursum.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosAula(
        @JsonAlias("Numero") String numSequencia,
        @JsonAlias("Título") String titulo,
        @JsonAlias("Duracao") String duracao,
        @JsonAlias("id") String idApi,
        @JsonAlias("idCurso") String idCursoApi
) {
}
