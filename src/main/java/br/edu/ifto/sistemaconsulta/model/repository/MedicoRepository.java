package br.edu.ifto.sistemaconsulta.model.repository;

import br.edu.ifto.sistemaconsulta.model.entity.Medico;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MedicoRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Medico medico){
        em.persist(medico);
    }

    public Medico medico(Long id){
        return em.find(Medico.class, id);
    }

    public List<Medico> medicos(){
        Query query = em.createQuery("from Medico ");
        return query.getResultList();
    }

    public void remove(Long id){
        Medico v = em.find(Medico.class, id);
        em.remove(v);
    }

    public void update(Medico medico){
        em.merge(medico);
    }

    public List<Medico> search(String nome, String crm) {
        String hql = "from Medico m";
        if (nome != null && !nome.trim().isEmpty()) {
            hql += " where lower(m.nome) like :nome";
        }

        if (crm != null && !crm.trim().isEmpty()) {
            hql += " where lower(m.crm) like :crm";
        }

        Query query = em.createQuery(hql);

        if (nome != null && !nome.trim().isEmpty()) {
            query.setParameter("nome", "%" + nome.toLowerCase() + "%");
        }

        if (crm != null && !crm.trim().isEmpty()) {
            query.setParameter("crm", "%" + crm.toLowerCase() + "%");
        }

        return query.getResultList();
    }
}
