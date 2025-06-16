package br.edu.ifto.sistemaconsulta.model.enums;

public enum StatusHorarioAgendaEnum {
    DISPONIVEL("Disponível", 1),
    AGENDADO("Agendado", 2),
    CANCELADO("Cancelado", 3),
    CONFLITO("Conflito", 4);

    private final String nomeParaExibicao;
    private final int id;

    StatusHorarioAgendaEnum(String nomeParaExibicao, int id) {
        this.nomeParaExibicao = nomeParaExibicao;
        this.id = id;
    }

    public String getNomeParaExibicao() {
        return nomeParaExibicao;
    }

    public int getId() {
        return id;
    }

    public static StatusHorarioAgendaEnum valueOf(int id) {
        for (StatusHorarioAgendaEnum status : values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código de StatusHorarioAgenda inválido: " + id);
    }
}