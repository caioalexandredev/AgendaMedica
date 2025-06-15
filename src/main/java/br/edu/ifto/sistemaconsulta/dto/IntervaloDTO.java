package br.edu.ifto.sistemaconsulta.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalTime;

public class IntervaloDTO {
    @NotNull(message = "O início do intervalo não pode ser vazio.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime inicio;

    @NotNull(message = "O fim do intervalo não pode ser vazio.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime fim;

    public LocalTime getInicio() {
        return inicio;
    }

    public IntervaloDTO setInicio(LocalTime inicio) {
        this.inicio = inicio;
        return this;
    }

    public LocalTime getFim() {
        return fim;
    }

    public IntervaloDTO setFim(LocalTime fim) {
        this.fim = fim;
        return this;
    }
}