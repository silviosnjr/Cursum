package br.com.cursum.model;

public enum AtividadeTipo {
    VIDEO("Vídeo"),
    FONTE("Fonte"),
    EXTRA("Extra"),
    TUTORIAL("Tutorial"),
    RESULTADO("Resultado"),
    ALTERNATIVA("Alternativa");

    private String descricao;

    AtividadeTipo(String tipo) {
        this.descricao = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static AtividadeTipo fromString(String text) {
        for (AtividadeTipo tipo : AtividadeTipo.values()) {
            if (tipo.descricao.equalsIgnoreCase(text)){
                return tipo;
            }
        }
        throw new IllegalArgumentException("Nenhuma tipo encontrada para a string fornecida: " + text);
    }

}
