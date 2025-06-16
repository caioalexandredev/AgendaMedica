package br.edu.ifto.sistemaconsulta.model.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Entity
public class IntervaloAgendaGerar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O início do intervalo não pode ser vazio.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime inicio;

    @NotNull(message = "O fim do intervalo não pode ser vazio.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime fim;

    @Valid
    @ManyToOne
    @JoinColumn(name = "id_agenda_gerar")
    @NotNull
    private AgendaGerar agendaGerar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public IntervaloAgendaGerar setInicio(LocalTime inicio) {
        this.inicio = inicio;
        return this;
    }

    public LocalTime getFim() {
        return fim;
    }

    public IntervaloAgendaGerar setFim(LocalTime fim) {
        this.fim = fim;
        return this;
    }

    public AgendaGerar getAgendaGerar() {
        return agendaGerar;
    }

    public void setAgendaGerar(AgendaGerar agendaGerar) {
        this.agendaGerar = agendaGerar;
    }
}