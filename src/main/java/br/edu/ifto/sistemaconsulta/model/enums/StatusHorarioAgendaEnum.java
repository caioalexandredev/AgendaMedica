package br.edu.ifto.sistemaconsulta.model.enums;

public enum StatusHorarioAgendaEnum {
    DISPONIVEL("Disponível", 1, "text-bg-primary", "O horário será disponibilizado para possibilitar um agendamento de consulta."),
    AGENDADO("Agendado", 2, "text-bg-success", "O horário já está agendado."),
    CANCELADO("Cancelado", 3, "text-bg-danger", "O agendamento foi cancelado."),
    CONFLITO("Conflito", 4, "text-bg-danger", "Conflito detectado, horário não será salvo.");

    private final String nomeParaExibicao;
    private final int id;
    private final String cssClass;
    private final String tooltipText;

    StatusHorarioAgendaEnum(String nomeParaExibicao, int id, String cssClass, String tooltipText) {
        this.nomeParaExibicao = nomeParaExibicao;
        this.id = id;
        this.cssClass = cssClass;
        this.tooltipText = tooltipText;
    }

    public String getNomeParaExibicao() {
        return nomeParaExibicao;
    }

    public int getId() {
        return id;
    }

    public String getCssClass() {
        return cssClass;
    }

    public String getTooltipText() {
        return tooltipText;
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