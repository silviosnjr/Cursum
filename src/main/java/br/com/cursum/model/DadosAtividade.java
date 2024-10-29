package br.com.cursum.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosAtividade(
        @JsonAlias("Numero") String numSequencia,
        @JsonAlias("Titulo") String titulo,
        @JsonAlias("Tipo") String tipo,
        @JsonAlias("id") String idApi,
        @JsonAlias("idAula") String idAula
) {
}
