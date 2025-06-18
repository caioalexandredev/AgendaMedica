package br.edu.ifto.sistemaconsulta.model.entity;

import br.edu.ifto.sistemaconsulta.model.enums.StatusHorarioAgendaEnum;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class HorarioAgenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "O horário de início é obrigatório.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime inicio;

    @NotNull(message = "O horário de fim é obrigatório.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime fim;

    @Valid
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_medico")
    private Medico medico;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusHorarioAgendaEnum statusHorarioAgenda;

    @NotNull(message = "É obrigatório gerar a agenda a partir de uma data especifica.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "id_consulta")
    private Consulta consulta;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getFim() {
        return fim;
    }

    public void setFim(LocalTime fim) {
        this.fim = fim;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalTime inicio) {
        this.inicio = inicio;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public StatusHorarioAgendaEnum getStatusHorarioAgenda() {
        return statusHorarioAgenda;
    }

    public void setStatusHorarioAgenda(StatusHorarioAgendaEnum statusHorarioAgenda) {
        this.statusHorarioAgenda = statusHorarioAgenda;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consultar) {
        this.consulta = consultar;
    }
}
