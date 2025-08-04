package br.edu.ifto.sistemaconsulta.model.repository;

import br.edu.ifto.sistemaconsulta.model.entity.Paciente;
import br.edu.ifto.sistemaconsulta.model.entity.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepository {
    @PersistenceContext
    private EntityManager em;

    public Usuario usuario(String login){
        return em.createQuery("SELECT u FROM Usuario u WHERE u.login = :login", Usuario.class)
                .setParameter("login", login)
                .getSingleResult();
    }
}
