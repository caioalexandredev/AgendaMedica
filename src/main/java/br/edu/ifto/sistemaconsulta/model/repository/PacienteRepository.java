package br.edu.ifto.sistemaconsulta.model.repository;

import br.edu.ifto.sistemaconsulta.model.entity.Medico;
import br.edu.ifto.sistemaconsulta.model.entity.Paciente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PacienteRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Paciente paciente){
        em.persist(paciente);
    }

    public Paciente paciente(Long id){
        return em.find(Paciente.class, id);
    }

    public List<Paciente> pacientes(){
        Query query = em.createQuery("from Paciente");
        return query.getResultList();
    }

    public void remove(Long id){
        Paciente v = em.find(Paciente.class, id);
        em.remove(v);
    }

    public void update(Paciente paciente){
        em.merge(paciente);
    }

    public List<Medico> search(String nome) {
        String hql = "from Paciente p";
        if (nome != null && !nome.trim().isEmpty()) {
            hql += " where lower(p.nome) like :nome";
        }

        Query query = em.createQuery(hql);
        if (nome != null && !nome.trim().isEmpty()) {
            query.setParameter("nome", "%" + nome.toLowerCase() + "%");
        }

        return query.getResultList();
    }
}
