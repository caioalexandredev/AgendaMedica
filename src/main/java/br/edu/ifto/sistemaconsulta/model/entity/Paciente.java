package br.edu.ifto.sistemaconsulta.model.entity;

import br.edu.ifto.sistemaconsulta.model.validation.Insert;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("P")
public class Paciente extends Pessoa {
    @NotBlank(message = "Telefone é obrigatório!")
    private String telefone;

    @OneToMany(mappedBy = "paciente")
    private List<Consulta> consultas = new ArrayList<>();

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    @NotNull(groups = {Insert.class})
    @Override
    public Long getId() {
        return super.getId();
    }
}
