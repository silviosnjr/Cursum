package br.com.cursum.dto;

public class AtividadeDTO {
    private Long id;
    private Integer numSequencia;
    private String titulo;
    private String tipo;
    private Long aulaId;

    // Construtor
    public AtividadeDTO(Long id, Integer numSequencia, String titulo, String tipo, Long aulaId) {
        this.id = id;
        this.numSequencia = numSequencia;
        this.titulo = titulo;
        this.tipo = tipo;
        this.aulaId = aulaId;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getNumSequencia() { return numSequencia; }
    public void setNumSequencia(Integer numSequencia) { this.numSequencia = numSequencia; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Long getAulaId() { return aulaId; }
    public void setAulaId(Long aulaId) { this.aulaId = aulaId; }

    @Override
    public String toString() {
        return """
                Sequência: %s
                Título: %s
                Tipo: %s
                """.formatted(
                this.numSequencia,
                this.titulo,
                this.tipo);
    }
}
