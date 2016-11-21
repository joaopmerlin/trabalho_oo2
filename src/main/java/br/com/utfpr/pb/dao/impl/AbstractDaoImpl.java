package br.com.utfpr.pb.dao.impl;

import br.com.utfpr.pb.config.DatabaseConnection;
import br.com.utfpr.pb.dao.AbstractDao;
import br.com.utfpr.pb.model.Model;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by João on 10/11/2016.
 */

public class AbstractDaoImpl<T extends Model, ID extends Serializable> implements AbstractDao<T, ID> {

    protected Class<T> persistedClass = getClazz();

    protected EntityManager em = DatabaseConnection.getInstance()
            .getEntityManager();

    @Override
    public T save(T entidade) {
        preSave(entidade);
        em.getTransaction().begin();
        try {
            if (entidade.getId() == null) {
                em.persist(entidade);
                em.getTransaction().commit();
                return entidade;
            } else {
                em.merge(entidade);
                em.getTransaction().commit();
                return entidade;
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            postSave(entidade);
        }
    }

    protected T preSave(T entidade) {
        return entidade;
    }

    protected T postSave(T entidade) {
        return entidade;
    }

    @Override
    public T find(ID id) {
        return em.find(persistedClass, id);
    }

    @Override
    public List<T> findAll() {
        return em.createQuery("from " + persistedClass.getName()).getResultList();
    }

    @Override
    public void remove(T t) {
        em.getTransaction().begin();
        try {
            em.remove(em.getReference(persistedClass, t.getId()));
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    private Class<T> getClazz() {
        Class<?> clazz = this.getClass();
        while (!clazz.getSuperclass().equals(AbstractDaoImpl.class)) {
            clazz = clazz.getSuperclass();
        }
        ParameterizedType tipoGenerico = (ParameterizedType)
                clazz.getGenericSuperclass();
        return (Class<T>) tipoGenerico.getActualTypeArguments()[0];
    }
}
