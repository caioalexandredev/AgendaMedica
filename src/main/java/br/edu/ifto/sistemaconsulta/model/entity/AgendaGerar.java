package br.edu.ifto.sistemaconsulta.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AgendaGerar {
    @Id
    @GeneratedValue(generator = "inc")
    private Long id;

    @NotNull(message = "O horário de início é obrigatório.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime inicio;

    @NotNull(message = "O horário de fim é obrigatório.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime fim;

    @NotNull(message = "O tempo de consulta é obrigatório.")
    @Positive(message = "O tempo de consulta deve ser um número positivo.")
    @Min(value = 5, message = "O tempo de consulta deve ser no minimo 5 minutos.")
    private Integer tempo;

    @NotNull(message = "É obrigatório gerar a agenda a partir de um médico.")
    private Long medicoId;

    @Transient
    @NotNull(message = "É obrigatório gerar a agenda a partir de uma data especifica.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate data;

    @Transient
    @NotNull
    private Integer tipo;

    @OneToMany(mappedBy = "agendaGerar", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<IntervaloAgendaGerar> intervalos = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public AgendaGerar setInicio(LocalTime inicio) {
        this.inicio = inicio;
        return this;
    }

    public LocalTime getFim() {
        return fim;
    }

    public AgendaGerar setFim(LocalTime fim) {
        this.fim = fim;
        return this;
    }

    public Integer getTempo() {
        return tempo;
    }

    public AgendaGerar setTempo(Integer tempo) {
        this.tempo = tempo;
        return this;
    }

    public List<IntervaloAgendaGerar> getIntervalos() {
        return intervalos;
    }

    public AgendaGerar setIntervalos(List<IntervaloAgendaGerar> intervalos) {
        this.intervalos = intervalos;
        return this;
    }

    public Long getMedicoId() {
        return medicoId;
    }

    public AgendaGerar setMedicoId(Long medicoId) {
        this.medicoId = medicoId;
        return this;
    }

    public LocalDate getData() {
        return data;
    }

    public AgendaGerar setData(LocalDate data) {
        this.data = data;
        return this;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }
}