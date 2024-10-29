package br.com.cursum.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosInstrutor (
        @JsonAlias("id") Integer idApi,
        @JsonAlias("nome") String nome,
        @JsonAlias("descricao") String descricao
){
}
