package br.edu.ifto.sistemaconsulta.model.repository;

import br.edu.ifto.sistemaconsulta.model.entity.Consulta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ConsultaRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Consulta consulta){
        em.persist(consulta);
    }

    public Consulta consulta(int id){
        return em.find(Consulta.class, id);
    }

    public List<Consulta> consultas(){
        Query query = em.createQuery("from Consulta ");
        return query.getResultList();
    }

    public List<Consulta> consultasPorMedico(Long medicoId){
        Query query = em.createQuery("from Consulta c where c.medico.id = :medicoId");
        query.setParameter("medicoId", medicoId);
        return query.getResultList();
    }

    public List<Consulta> consultasPorPaciente(Long pacienteId){
        Query query = em.createQuery("from Consulta c where c.paciente.id = :pacienteId");
        query.setParameter("pacienteId", pacienteId);
        return query.getResultList();
    }

    public void remove(int id){
        Consulta v = em.find(Consulta.class, id);
        em.remove(v);
    }

    public void update(Consulta consulta){
        em.merge(consulta);
    }

    public List<Consulta> search(LocalDateTime datainicio, LocalDateTime datafim, Long idMedico, Long idPaciente) {
        String hql = "from Consulta c where 1=1 ";

        if (datainicio != null) {
            hql += " and c.data >= :datainicio";
        }

        if (datafim != null) {
            hql += " and c.data <= :datafim";
        }

        if (idMedico != null) {
            hql += " and c.medico.id = :idMedico";
        }

        if (idPaciente != null) {
            hql += " and c.paciente.id = :idPaciente";
        }

        Query query = em.createQuery(hql);

        if (datainicio != null) {
            query.setParameter("datainicio", datainicio);
        }

        if (datafim != null) {
            query.setParameter("datafim", datafim);
        }

        if (idMedico != null) {
            query.setParameter("idMedico", idMedico);
        }

        if (idPaciente != null) {
            query.setParameter("idPaciente", idPaciente);
        }

        return query.getResultList();
    }
}
