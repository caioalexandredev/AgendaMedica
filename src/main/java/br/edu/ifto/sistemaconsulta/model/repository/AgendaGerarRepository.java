package br.edu.ifto.sistemaconsulta.model.repository;

import br.edu.ifto.sistemaconsulta.model.entity.AgendaGerar;
import br.edu.ifto.sistemaconsulta.model.entity.Consulta;
import br.edu.ifto.sistemaconsulta.model.entity.StatusHorarioAgenda;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AgendaGerarRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(AgendaGerar agendaGerar){
        em.persist(agendaGerar);
    }

    public AgendaGerar consulta(Long id){
        return em.find(AgendaGerar.class, id);
    }

    public List<Consulta> consultas(){
        Query query = em.createQuery("from AgendaGerar");
        return query.getResultList();
    }

    public void remove(Long id){
        AgendaGerar a = em.find(AgendaGerar.class, id);
        em.remove(a);
    }

    public void update(AgendaGerar agendaGerar){
        em.merge(agendaGerar);
    }

    public AgendaGerar consultaPorMedico(Long medicoId) {
        String hql = "from AgendaGerar a where a.medicoId =  :medicoId";
        Query query = em.createQuery(hql);
        query.setParameter("medicoId", medicoId);

        List<AgendaGerar> resultados = query.getResultList();

        if (resultados.isEmpty()) {
            return null;
        } else {
            return resultados.get(0);
        }
    }
}
