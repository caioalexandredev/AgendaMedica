package br.edu.ifto.sistemaconsulta.model.repository;

import br.edu.ifto.sistemaconsulta.model.entity.AgendaGerar;
import br.edu.ifto.sistemaconsulta.model.entity.Consulta;
import br.edu.ifto.sistemaconsulta.model.entity.IntervaloAgendaGerar;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IntervaloAgendaGerarRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(IntervaloAgendaGerar intervaloAgendaGerar){
        em.persist(intervaloAgendaGerar);
    }

    public IntervaloAgendaGerar consulta(Long id){
        return em.find(IntervaloAgendaGerar.class, id);
    }

    public List<Consulta> consultas(){
        Query query = em.createQuery("from IntervaloAgendaGerar");
        return query.getResultList();
    }

    public void remove(Long id){
        IntervaloAgendaGerar i = em.find(IntervaloAgendaGerar.class, id);
        em.remove(i);
    }

    public void update(IntervaloAgendaGerar intervaloAgendaGerar){
        em.merge(intervaloAgendaGerar);
    }
}
