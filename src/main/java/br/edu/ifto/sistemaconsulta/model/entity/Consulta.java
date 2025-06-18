package br.edu.ifto.sistemaconsulta.model.entity;

import br.edu.ifto.sistemaconsulta.model.validation.Insert;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "A data é obrigatória", groups = {Insert.class} )
    private LocalDateTime data;

    @NotNull(message = "O valor é obrigatório", groups = {Insert.class})
    @Min(value = 1, message = "O valor mínimo é 1")
    private Double valor;

    private String observacao;

    @Valid
    @ManyToOne
    @JoinColumn(name = "id_paciente")
    @NotNull
    private Paciente paciente;

    @Valid
    @ManyToOne
    @JoinColumn(name = "id_medico")
    @NotNull
    private Medico medico;

    @OneToMany(mappedBy = "consulta")
    private List<HorarioAgenda> horariosAgenda = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public List<HorarioAgenda> getHorariosAgenda() {
        return horariosAgenda;
    }

    public void setHorariosAgenda(List<HorarioAgenda> horariosAgenda) {
        this.horariosAgenda = horariosAgenda;
    }
}
