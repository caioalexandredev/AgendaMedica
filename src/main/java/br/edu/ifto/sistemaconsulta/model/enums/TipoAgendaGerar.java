package br.edu.ifto.sistemaconsulta.model.enums;

public enum TipoAgendaGerar {
    GERAR("Gerar", 1),
    SALVAR("Salvar", 2),
    SALVAR_PADRAO("Salvar Padrão", 3);

    private final String nomeParaExibicao;
    private final int id;

    TipoAgendaGerar(String nomeParaExibicao, int id) {
        this.nomeParaExibicao = nomeParaExibicao;
        this.id = id;
    }

    public String getNomeParaExibicao() {
        return nomeParaExibicao;
    }

    public int getId() {
        return id;
    }

    public static TipoAgendaGerar valueOf(int id) {
        for (TipoAgendaGerar status : values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código de StatusPedido inválido: " + id);
    }
}