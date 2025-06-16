package br.edu.ifto.sistemaconsulta.model.enums;

public enum TipoAgendaGerarEnum {
    GERAR("Gerar", 1),
    SALVAR("Salvar", 2),
    SALVAR_PADRAO("Salvar Padrão", 3);

    private final String nomeParaExibicao;
    private final int id;

    TipoAgendaGerarEnum(String nomeParaExibicao, int id) {
        this.nomeParaExibicao = nomeParaExibicao;
        this.id = id;
    }

    public String getNomeParaExibicao() {
        return nomeParaExibicao;
    }

    public int getId() {
        return id;
    }

    public static TipoAgendaGerarEnum valueOf(int id) {
        for (TipoAgendaGerarEnum status : values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código de StatusPedido inválido: " + id);
    }
}