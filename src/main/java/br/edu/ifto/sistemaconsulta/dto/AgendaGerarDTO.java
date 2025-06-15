package br.edu.ifto.sistemaconsulta.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

public class AgendaGerarDTO {
    @NotNull(message = "O horário de início é obrigatório.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime inicio;

    @NotNull(message = "O horário de fim é obrigatório.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime fim;

    @NotNull(message = "O tempo de consulta é obrigatório.")
    @Positive(message = "O tempo de consulta deve ser um número positivo.")
    private Integer tempo;

    @NotNull(message = "É obrigatório gerar a agenda a partir de um médico.")
    private Long medicoId;

    @NotNull(message = "É obrigatório gerar a agenda a partir de uma data especifica.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate data;

    @Valid
    private List<IntervaloDTO> intervalos = new ArrayList<>();

    public LocalTime getInicio() {
        return inicio;
    }

    public AgendaGerarDTO setInicio(LocalTime inicio) {
        this.inicio = inicio;
        return this;
    }

    public LocalTime getFim() {
        return fim;
    }

    public AgendaGerarDTO setFim(LocalTime fim) {
        this.fim = fim;
        return this;
    }

    public Integer getTempo() {
        return tempo;
    }

    public AgendaGerarDTO setTempo(Integer tempo) {
        this.tempo = tempo;
        return this;
    }

    public List<IntervaloDTO> getIntervalos() {
        return intervalos;
    }

    public AgendaGerarDTO setIntervalos(List<IntervaloDTO> intervalos) {
        this.intervalos = intervalos;
        return this;
    }

    public Long getMedicoId() {
        return medicoId;
    }

    public AgendaGerarDTO setMedicoId(Long medicoId) {
        this.medicoId = medicoId;
        return this;
    }

    public LocalDate getData() {
        return data;
    }

    public AgendaGerarDTO setData(LocalDate data) {
        this.data = data;
        return this;
    }
}