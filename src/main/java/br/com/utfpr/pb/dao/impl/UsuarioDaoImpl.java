package br.com.utfpr.pb.dao.impl;

import br.com.utfpr.pb.config.DatabaseConnection;
import br.com.utfpr.pb.dao.UsuarioDao;
import br.com.utfpr.pb.model.Usuario;
import br.com.utfpr.pb.util.EncryptPasswordUtil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 * Created by João on 10/11/2016.
 */
public class UsuarioDaoImpl extends AbstractDaoImpl<Usuario, Long> implements UsuarioDao {

    private EntityManager em = DatabaseConnection.getInstance().getEntityManager();

    @Override
    public Usuario findByLoginAndSenha(String login, String senha) {
        try {
            Object usuario = em.createQuery("select u from Usuario u where u.login = :login and u.senha = :senha")
                    .setParameter("login", login)
                    .setParameter("senha", EncryptPasswordUtil.getInstance().encrypt(senha))
                    .getSingleResult();
            if (usuario != null) {
                return (Usuario) usuario;
            }
        } catch (NoResultException e) {
        }
        return null;
    }

    @Override
    public Usuario findByLogin(String login) {
        try {
            Object usuario = em.createQuery("select u from Usuario u where u.login = :login")
                    .setParameter("login", login)
                    .getSingleResult();
            if (usuario != null) {
                return (Usuario) usuario;
            }
        } catch (NoResultException e) {
        }
        return null;
    }
}
