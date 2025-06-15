package br.edu.ifto.sistemaconsulta.model.entity;

import br.edu.ifto.sistemaconsulta.model.validation.Insert;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("M")
public class Medico extends Pessoa {

    @NotBlank(message = "CRM é obrigatório!")
    @Size(min=11, message = "O tamanho deve ser de no mínimo 11 caracteres")
    private String crm;

    @OneToMany(mappedBy = "medico")
    private List<Consulta> consultas = new ArrayList<>();

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
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
