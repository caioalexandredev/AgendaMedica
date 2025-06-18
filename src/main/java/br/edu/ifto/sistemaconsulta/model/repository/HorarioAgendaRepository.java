package br.edu.ifto.sistemaconsulta.model.repository;

import br.edu.ifto.sistemaconsulta.model.entity.Consulta;
import br.edu.ifto.sistemaconsulta.model.entity.HorarioAgenda;
import br.edu.ifto.sistemaconsulta.model.entity.StatusHorarioAgenda;
import br.edu.ifto.sistemaconsulta.model.enums.StatusHorarioAgendaEnum;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class HorarioAgendaRepository {
    @PersistenceContext
    private EntityManager em;

    public HorarioAgenda find(Long id){
        return em.find(HorarioAgenda.class, id);
    }

    public StatusHorarioAgenda consultaStatusHorarioAgenda(StatusHorarioAgendaEnum statusHorarioAgendaEnum){
        return em.find(StatusHorarioAgenda.class, statusHorarioAgendaEnum.getId());
    }

    public void save(HorarioAgenda horarioAgenda){
        em.persist(horarioAgenda);
    }

    public void saveAll(List<HorarioAgenda> horariosAgenda)
    {
        horariosAgenda.forEach(horarioAgenda -> em.persist(horarioAgenda));
    }

    public HorarioAgenda medico(Long id){
        return em.find(HorarioAgenda.class, id);
    }

    public List<HorarioAgenda> horariosAgenda(){
        Query query = em.createQuery("from HorarioAgenda ");
        return query.getResultList();
    }

    public void remove(Long id){
        HorarioAgenda h = em.find(HorarioAgenda.class, id);
        em.remove(h);
    }

    public void update(HorarioAgenda horarioAgenda){
        em.merge(horarioAgenda);
    }

    public List<HorarioAgenda> search(
        Long medicoId,
        LocalDate data,
        StatusHorarioAgendaEnum statusHorarioAgendaEnum
    ) {
        String hql = "from HorarioAgenda h where 1=1";

        if (medicoId != null) {
            hql += " and h.medico.id =  :medicoId";
        }

        if (data != null) {
            hql += " and h.data = :data";
        }

        if (statusHorarioAgendaEnum != null) {
            hql += " and h.statusHorarioAgenda.id = :statusHorarioAgenda";
        }

        hql += " order by h.data, h.inicio, h.fim, h.medico.nome desc";

        Query query = em.createQuery(hql);

        if (medicoId != null) {
            query.setParameter("medicoId", medicoId);
        }

        if (data != null) {
            query.setParameter("data", data);
        }

        if (statusHorarioAgendaEnum != null) {
            query.setParameter("statusHorarioAgenda", statusHorarioAgendaEnum.getId());
        };

        return query.getResultList();
    }
}
