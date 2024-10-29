package br.com.cursum.model;

public enum Escola {
    FRONT_END("Front-End", "Front End"),
    PROGRAMACAO("Programação", "Back-End"),
    DEVOPS("DevOps", "DevOps"),
    DATA_SCIENCE("Data Science", "Banco de Dados"),
    INOVACAO_E_GESTAO("Inovação e Gestão","Inovação e Gestão"),
    INTELIGENCIA_ARTIFICIAL("Inteligência Artifícial", "IA"),
    MOBILE("Mobile", "Mobile"),
    START("Start", "Start"),
    UX_E_DESIGN("UX & Design", "Ux & Design");

    private String nome;
    private String alternativa;

    Escola(String nome, String alternativa) {
        this.nome = nome;
        this.alternativa = alternativa;
    }

    public String getNome() {
        return nome;
    }

    public static Escola fromString(String text) {
        for (Escola escola : Escola.values()) {
            if (escola.nome.equalsIgnoreCase(text) || escola.alternativa.equalsIgnoreCase(text)){
                return escola;
            }
        }
        throw new IllegalArgumentException("Nenhuma escola encontrada para a string fornecida: " + text);
    }

    public static Escola fromAlternativa(String text) {
        for (Escola escola : Escola.values()) {
            if (escola.alternativa.equalsIgnoreCase(text)) {
                return escola;
            }
        }
        throw new IllegalArgumentException("Nenhuma escola encontrada para a string fornecida: " + text);
    }
}
